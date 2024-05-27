package com.example.comon.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.comon.R
import com.example.comon.screen.FeedbackScreen
import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult
import com.microsoft.cognitiveservices.speech.PropertyId
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.util.Locale


@SuppressLint("MutableCollectionMutableState")
@Composable
fun RecordUtil(
    navController: NavHostController,
    assignedText: String,
    syllable: List<String>,
    originPhoneme: List<String>,
    modifier: Modifier,
    path: String,
    courseLevel : Int, // courseLevel 전달
    onCourseLevelChange : (Int) -> Unit, // courseLevel 변경 이벤트 핸들러 전달
    openDialog: Boolean,
    onDialogChange : (Boolean) -> Unit,
    addResults : (TrainingResult) -> Unit
) {
    val tempResultsState = rememberSaveable { mutableListOf<TrainingResult>() }

    val (isEnabled, setIsEnabled) = remember { mutableStateOf(true) }
    val micColor =  remember(isEnabled) {
        if (isEnabled) Color.Gray else Color(0xFF14FF00)
    }
    val micText =  remember(isEnabled) {
        if (isEnabled) "녹음하기" else "듣고 있어요..."
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

    IconButton(
        onClick = {
            if (microphoneStream != null) {
                releaseMicrophoneStream()
                return@IconButton
            }

            setIsEnabled(false)

            try {
                val speechConfig: SpeechConfig = SpeechConfig.fromSubscription(speechSubscriptionKey, speechRegion)

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
                val totalPronounciationScore = 0

                val tempPhonemeAccuracy = ArrayList<Int>()
                val tempWord = ArrayList<String>()
                val tempWordAccuracy =  ArrayList<Int>()

                var totalPronScore : Int =0
                var totalAccurScore: Int =0
                var totalCompletenessScore: Int =0
                var totalFluencyScore: Int =0

                reco.recognized.addEventListener { _, speechRecognitionResultEventArgs ->
                    val s = speechRecognitionResultEventArgs.result.text
                    Log.i(
                        logTag,
                        "Final result received: $s"
                    )
                    val pronResult =
                        PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.result)
                    val scoreResult = listOf(pronResult.accuracyScore,pronResult.pronunciationScore,pronResult.completenessScore,pronResult.fluencyScore)
                    Log.i(
                        logTag, "Accuracy score: ${pronResult.accuracyScore};" +
                                "  pronunciation score: ${pronResult.pronunciationScore}," +
                                " completeness score: ${pronResult.completenessScore}," +
                                " fluency score: ${pronResult.fluencyScore}"
                    )

                    totalPronScore=pronResult.pronunciationScore.toInt()
                    totalFluencyScore=pronResult.fluencyScore.toInt()
                    totalAccurScore=pronResult.accuracyScore.toInt()
                    totalCompletenessScore=pronResult.completenessScore.toInt()


                    val jString =
                        speechRecognitionResultEventArgs.result.properties.getProperty(PropertyId.SpeechServiceResponse_JsonResult)
                    Log.i(logTag, jString)


                    for (w in pronResult.words) {
                        val wordResult = Word(
                            w.word,
                            w.errorType,
                            w.accuracyScore,
                            w.duration / 10000,
                            w.offset / 10000,
                        )//문장
                        tempWord.add(w.word)
                        tempWordAccuracy.add(w.accuracyScore.toInt())
                        for(p in w.phonemes)
                        {
                            Log.i("phonemes","/"+p.phoneme +"/"+ p.accuracyScore)//단어, 음절
                            tempPhonemeAccuracy.add(p.accuracyScore.toInt())
                        }

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

                    //열개 한번에 post 해 줄 데이터 리스트 쌓기
                    if(path == "sentence"){
                        val tempResult = TrainingResult(text=assignedText, each=tempWord, eachAccuracy = tempWordAccuracy, accuracyScore = totalAccurScore, pronunciationScore = totalPronScore, completenessScore = totalCompletenessScore, fluencyScore = totalFluencyScore)
                        addResults(tempResult)
                        //tempResultsState.add(tempResult)
                        Log.i("로그",tempResultsState.toString())
                    }
                    else{
                        val tempResult = TrainingResult(text=assignedText, each = originPhoneme, eachAccuracy = tempPhonemeAccuracy,accuracyScore = totalAccurScore, pronunciationScore = totalPronScore, completenessScore = totalCompletenessScore, fluencyScore = totalFluencyScore)
                        addResults(tempResult)
                        //tempResultsState.add(tempResult)
                        Log.i("로그",tempResultsState.toString())

                    }
                    //결괏값 String, 발음 영상 임시 저장한 url!!! String으로 넘기기
                    //navController.navigate("FEEDBACK/$tempResults/$url")
                    releaseMicrophoneStream()
                    onDialogChange(true)
                    setIsEnabled(true)
                    onCourseLevelChange(courseLevel)
                }
                reco.startContinuousRecognitionAsync()
            } catch (ex: Exception) {
                Log.e(logTag, ex.message ?: "speech error")
            }
            //navController.navigate("FEEDBACK/$tempResultsState")
        },
        modifier = modifier
            .padding(0.5.dp, top = 25.dp)
            .size(65.dp)
            .background(color = Color(0xFF6F3BDD), shape = CircleShape)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon__mic_),
            contentDescription = "mic",
            tint=micColor
        )
    }
    Box(
        modifier = modifier
            .padding(top = 20.dp)
            .background(
                color = Color(0xFF6F3BDD),
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(20.dp, 7.dp)
    ) {
        Text(
            text = micText,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
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

data class TrainingResult(
    val text: String? = "",
    val each: List<String>? = listOf(""),
    val eachAccuracy: List<Int>?= each?.let { List(it.size) { 0 } },
    val accuracyScore: Int? = 0,
    val pronunciationScore: Int? =0,
    val completenessScore: Int?=0,
    val fluencyScore: Int?=0
)



//@DevicePreview
//@Composable
//fun RecordUtilPreview()
//{
//    ComonTheme {
//        val nav = rememberNavController()
//        RecordUtil(navController = nav, assignedText = "수박", modifier = Modifier)
//    }
//}