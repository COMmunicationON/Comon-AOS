package com.example.comon.util

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.cognitiveservices.speech.*
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import com.microsoft.cognitiveservices.speech.samples.sdkdemo.MicrophoneStream
import java.io.*
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import com.microsoft.cognitiveservices.speech.audio.AudioInputStream


@Composable
fun RecordUtil(
    assignedText: String
) {
    val context = LocalContext.current

    var isButtonEnabled by remember { mutableStateOf(true) }
    var recognizedText by remember { mutableStateOf("true") }
    val speechSubscriptionKey = "8cca489f7031489985e9d0f8a704821b"
    val speechRegion = "koreacentral"

    var microphoneStream: MicrophoneStream? = null

    fun releaseMicrophoneStream() {
        microphoneStream?.close()
        microphoneStream = null
    }

    fun createMicrophoneStream(): MicrophoneStream? {
        releaseMicrophoneStream()
        microphoneStream = MicrophoneStream(context)
        return microphoneStream
    }

    fun appendTextLine(s: String, erase: Boolean) {
        recognizedText = if (erase) s else "${recognizedText}\n$s"
    }

    // create config
    val speechConfig: SpeechConfig

    try {
        speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, speechRegion)
    } catch (ex: Exception) {
        return
    }

    IconButton(
        onClick = {
            //assessmentClick(speechSubscriptionKey, speechRegion, speechConfig)
            val logTag = "recordUtil"

            releaseMicrophoneStream()

            try {
                createMicrophoneStream()
                val audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream())

                val reco = SpeechRecognizer(speechConfig, "ko-KR", audioConfig)

                val pronConfig = PronunciationAssessmentConfig(
                    assignedText,
                    PronunciationAssessmentGradingSystem.HundredMark,
                    PronunciationAssessmentGranularity.Phoneme
                )

                pronConfig.applyTo(reco)

                val recognizedWords = ArrayList<String>()
                val pronWords = ArrayList<Word>()
                val totalDurations = longArrayOf(0, 0)
                val offsets = longArrayOf(0, 0)
                val totalAccuracyScore = doubleArrayOf(0.0)

                reco.recognized.addEventListener { _, speechRecognitionResultEventArgs ->
                    val s = speechRecognitionResultEventArgs.result.text
                    Log.i(
                        logTag,
                        "Final result received: $s"
                    )
                    val pronResult =
                        PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.result)
                    Log.i(
                        logTag, "Accuracy score: ${pronResult.accuracyScore};" +
                                "  pronunciation score: ${pronResult.pronunciationScore}," +
                                " completeness score: ${pronResult.completenessScore}," +
                                " fluency score: ${pronResult.fluencyScore}"
                    )

                    for (w in pronResult.words) {
                        val word = Word(
                            w.word,
                            w.errorType,
                            w.accuracyScore,
                            w.duration / 10000,
                            w.offset / 10000
                        )
                        pronWords.add(word)
                        recognizedWords.add(word.word)
                        totalAccuracyScore[0] += word.duration * word.accuracyScore
                        totalDurations[0] += word.duration
                        if (word.errorType == "None") {
                            totalDurations[1] += word.duration + 10
                        }
                        offsets[1] = word.offset + word.duration + 10
                    }
                }

                reco.sessionStopped.addEventListener { _, _ ->
                    Log.i(logTag, "Session stopped.")
                    reco.stopContinuousRecognitionAsync()

                    val accuracyScore = totalAccuracyScore[0] / totalDurations[0]
                    var fluencyScore = 0.0
                    if (!recognizedWords.isEmpty()) {
                        offsets[0] = pronWords[0].offset
                        fluencyScore =
                            totalDurations[1].toDouble() / (offsets[1] - offsets[0]) * 100
                    }

                    val referenceWords =
                        assignedText.lowercase(Locale.ROOT).split(" ").toTypedArray()
                    for (j in referenceWords.indices) {
                        referenceWords[j] =
                            referenceWords[j].replace("^\\p{Punct}+|\\p{Punct}+$".toRegex(), "")
                    }
                    val diff = DiffUtils.diff(listOf(*referenceWords), recognizedWords, true)

                    var currentIdx = 0
                    val finalWords = ArrayList<Word>()
                    val validWord = intArrayOf(0)
                    for (d in diff.deltas) {
                        when (d.type!!) {
                            DeltaType.EQUAL -> {
                                for (i in currentIdx until currentIdx + d.source.size()) {
                                    finalWords.add(pronWords[i])
                                }
                                currentIdx += d.target.size()
                                validWord[0] += d.target.size()
                            }

                            DeltaType.DELETE, DeltaType.CHANGE -> {
                                for (w in d.source.lines) {
                                    finalWords.add(Word(w, "Omission"))
                                }
                            }

                            DeltaType.INSERT -> {
                                for (i in currentIdx until currentIdx + d.target.size()) {
                                    val w = pronWords[i]
                                    w.errorType = "Insertion"
                                    finalWords.add(w)
                                }
                                currentIdx += d.target.size()
                            }
                        }
                    }

                    val completenessScore = validWord[0].toDouble() / referenceWords.size * 100

                    appendTextLine(
                        "Paragraph accuracy score: $accuracyScore," +
                                " completeness score: $completenessScore, fluency score: $fluencyScore\n",
                        true
                    )
                    for (w in finalWords) {
                        appendTextLine(
                            " word: ${w.word}\taccuracy score: " +
                                    "${w.accuracyScore}\terror type: ${w.errorType}", false
                        )
                    }

                    releaseMicrophoneStream()
                }

                reco.startContinuousRecognitionAsync()
            } catch (ex: Exception) {
                Log.e(logTag, ex.message ?: "")
            }
        },
        modifier = Modifier
            .padding(0.5.dp, top = 30.dp)
            .size(65.dp)
            .background(color = Color(0xFF6F3BDD), shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = com.example.comon.R.drawable.icon__mic_),
            contentDescription = "mic"
        )
    }
}

fun assessmentClick(
    speechSubscriptionKey: String,
    speechRegion: String,
    speechConfig: SpeechConfig
) {

}


//@Composable
//fun copyAssetToCacheAndGetFilePath(filename: String): String {
//    val context = LocalContext.current
//    val cacheDir = context.cacheDir
//    val cacheFile = File(cacheDir, filename)
//    if (!cacheFile.exists()) {
//        try {
//            context.assets.open(filename).use { inputStream ->
//                val size = inputStream.available()
//                val buffer = ByteArray(size)
//                inputStream.read(buffer)
//                FileOutputStream(cacheFile).use { fos ->
//                    fos.write(buffer)
//                }
//            }
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
//    return cacheFile.path
//}


data class Word(
    val word: String,
    var errorType: String,
    val accuracyScore: Double = 0.0,
    val duration: Long = 0,
    val offset: Long = 0
)

