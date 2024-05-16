package com.example.comon.util

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.comon.R
import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import com.microsoft.cognitiveservices.speech.*
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.io.*
import java.util.*


@Composable
fun RecordUtil(
    assignedText: String,
    modifierCenter: Modifier
) {

    val (isEnabled, setIsEnabled) = remember { mutableStateOf(true) }
    val micColor =  remember(isEnabled) {
        if (isEnabled) Color(0xFF14FF00) else Color.Gray
    }

    val context = LocalContext.current

    var recognizedText by remember { mutableStateOf("") }
    val speechSubscriptionKey = "8cca489f7031489985e9d0f8a704821b"
    val speechRegion = "koreacentral"

    var microphoneStream: MicrophoneStream? by remember { mutableStateOf(null) }

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

    val logTag = "recordUtil"

    // create config
    val speechConfig: SpeechConfig

    try {
        speechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, speechRegion)
    } catch (ex: Exception) {
        return
    }

    IconButton(
        onClick = {
            if (microphoneStream != null) {
                releaseMicrophoneStream()
                return@IconButton
            }

            setIsEnabled(false)

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
                        val wordResult = Word(
                            w.word,
                            w.errorType,
                            w.accuracyScore,
                            w.duration / 10000,
                            w.offset / 10000
                        )
                        pronWords.add(wordResult)
                        recognizedWords.add(wordResult.word)
                        totalAccuracyScore[0] += wordResult.duration * wordResult.accuracyScore
                        totalDurations[0] += wordResult.duration
                        if (wordResult.errorType == "None") {
                            totalDurations[1] += wordResult.duration + 10
                        }
                        offsets[1] = wordResult.offset + wordResult.duration + 10
                    }
                }

                reco.sessionStopped.addEventListener { _, _ ->
                    Log.i(logTag, "Session stopped.")
                    reco.stopContinuousRecognitionAsync()

                    val accuracyScore = totalAccuracyScore[0] / totalDurations[0]
                    var fluencyScore = 0.0
                    if (recognizedWords.isNotEmpty()) {
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
                    setIsEnabled(true)
                }

                reco.startContinuousRecognitionAsync()
            } catch (ex: Exception) {
                Log.e(logTag, ex.message ?: "speech error")
            }
        },
        modifier = modifierCenter
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon__mic_),
            contentDescription = "mic",
            tint=micColor
        )
    }
}

data class Word(
    val word: String,
    var errorType: String,
    val accuracyScore: Double = 0.0,
    val duration: Long = 0,
    val offset: Long = 0
)

