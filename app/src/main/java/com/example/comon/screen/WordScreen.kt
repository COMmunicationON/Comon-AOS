package com.example.comon.screen

import RequestRecordAudioPermission
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.Numbering
import com.example.comon.model.TrainingModel
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.util.MicrophoneStream
import com.example.comon.util.RecordUtil
import com.example.comon.util.Word
import com.github.difflib.DiffUtils
import com.github.difflib.patch.DeltaType
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.util.ArrayList
import java.util.Locale

@Composable
fun WordScreen(
    navController: NavHostController,
    path: String,
    difficulty: String,
    viewModel: TrainingViewModel = viewModel()
    )
{
    val courseLevel by remember { mutableIntStateOf(1) } //1번 문제 (1/10)
    var word by remember { mutableStateOf("") } //수박
    val referenceText by remember { mutableStateOf("꿈만 같아") } //테스트


//    val mic by remember { mutableStateOf(true) }
//    val micColor =  remember(mic) {
//        if (mic) Color(0xFF14FF00) else Color.Gray
//    }

    val trainingData by viewModel.trainingData.observeAsState()
    val dataList = trainingData

    val (showRecordUtil, setShowRecordUtil) = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        word= null.toString()
        val trainingModel = TrainingModel(type = path, level = difficulty.toInt())
        Log.i("word",difficulty)
        viewModel.fetchTrainingData(trainingModel)
    }

    LaunchedEffect(trainingData) {
        trainingData?.let { response ->
            //Log.d("!!", "전체: $dataList")
            // courseLevel에 해당하는 인덱스의 데이터 가져오기
            val data = response.datas?.get(courseLevel-1)
            data?.let {
                // 단어, 음절, 음소 가져와서 로그에 출력
                word=it.data!!
                Log.d("WordScreen", "Word: ${it.data}")
                Log.d("WordScreen", "Syllables: ${it.syllables}")
                Log.d("WordScreen", "SyllablesIndes: ${it.syllables?.get(0)}")
                Log.d("WordScreen", "Phonemes: ${it.phonemes}")
                Log.d("WordScreen", "PhonemesIndex: ${it.phonemes?.get(0)}")

                // 단어, 음절, 음소를 리스트 또는 변수에 저장하는 코드 추가...
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F2F3))
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth()
        )
        {
            //Text(text = path+difficulty, color = Color.Black) //console
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
            )
            {
//                Icon(
//                    modifier = Modifier.padding(start = 30.dp, top = 5.dp)
//                        .clickable {
//                            navController.popBackStack() // 이전 화면으로 이동
//                        },
//                    painter = painterResource(id = R.drawable.icon__arrow_left),
//                    contentDescription = null
//                )
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(87.dp)
                        .height(32.dp)
                        .background(
                            color = Color(0xFF6F3BDD),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                ) {
                    Text(
                        text = "훈련코스",
                        modifier = Modifier.align(Alignment.Center),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Text(
                text = "단어",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "쉬움",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 3.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF92959E),
                    textAlign = TextAlign.Center,
                )

            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(
                modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            )
            {
                for (i in 1..10) {
                    // courseLevel과 현재 숫자가 같으면 active를 true로 설정
                    val active = courseLevel == i
                    Numbering(num = i, active = active)
                }
            }
            Box(
                modifier = Modifier
                    .border(
                        width = 0.95.dp,
                        color = Color(0xFFE6E8EC),
                        shape = RoundedCornerShape(size = 9.5.dp)
                    )
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 9.5.dp)
                    )
            )
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top = 17.dp),
                        text = "따라해보세요: $word",
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.sample_word_activity),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(250.dp)
                            .height(230.dp)
                            .padding(20.dp)
                    )
                }

            }

//            Box(
//                modifier = Modifier
//                    .padding(0.5.dp, top = 30.dp)
//                    .size(65.dp)
//                    .background(color = Color(0xFF6F3BDD), shape = CircleShape)
//                    .align(Alignment.CenterHorizontally)
//                    .clickable {
//                        RecordUtil(word)
//                    },
//                contentAlignment = Alignment.Center
//            )
//            {
//                Icon(
//                    painter = painterResource(id = R.drawable.icon__mic_),
//                    contentDescription = "mic",
//                    tint = micColor
//                )
//            }
//            IconButton(
//                onClick = {
//                    val logTag = "recordUtil"
//
//                    try {
//                        createMicrophoneStream()
//                        val audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream())
//
//                        val reco = SpeechRecognizer(speechConfig, "ko-KR", audioConfig)
//
//                        val pronConfig = PronunciationAssessmentConfig(
//                            word,
//                            PronunciationAssessmentGradingSystem.HundredMark,
//                            PronunciationAssessmentGranularity.Phoneme
//                        )
//
//                        pronConfig.applyTo(reco)
//
//                        val recognizedWords = ArrayList<String>()
//                        val pronWords = ArrayList<Word>()
//                        val totalDurations = longArrayOf(0, 0)
//                        val offsets = longArrayOf(0, 0)
//                        val totalAccuracyScore = doubleArrayOf(0.0)
//
//                        reco.recognized.addEventListener { _, speechRecognitionResultEventArgs ->
//                            val s = speechRecognitionResultEventArgs.result.text
//                            Log.i(
//                                logTag,
//                                "Final result received: $s"
//                            )
//                            val pronResult =
//                                PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.result)
//                            Log.i(
//                                logTag, "Accuracy score: ${pronResult.accuracyScore};" +
//                                        "  pronunciation score: ${pronResult.pronunciationScore}," +
//                                        " completeness score: ${pronResult.completenessScore}," +
//                                        " fluency score: ${pronResult.fluencyScore}"
//                            )
//
//                            for (w in pronResult.words) {
//                                val wordResult = Word(
//                                    w.word,
//                                    w.errorType,
//                                    w.accuracyScore,
//                                    w.duration / 10000,
//                                    w.offset / 10000
//                                )
//                                pronWords.add(wordResult)
//                                recognizedWords.add(wordResult.word)
//                                totalAccuracyScore[0] += wordResult.duration * wordResult.accuracyScore
//                                totalDurations[0] += wordResult.duration
//                                if (wordResult.errorType == "None") {
//                                    totalDurations[1] += wordResult.duration + 10
//                                }
//                                offsets[1] = wordResult.offset + wordResult.duration + 10
//                            }
//                        }
//
//                        reco.sessionStopped.addEventListener { o, s ->
//                            Log.i(logTag, "Session stopped.")
//                            reco.stopContinuousRecognitionAsync()
//
//                            val accuracyScore = totalAccuracyScore[0] / totalDurations[0]
//                            var fluencyScore = 0.0
//                            if (recognizedWords.isNotEmpty()) {
//                                offsets[0] = pronWords[0].offset
//                                fluencyScore =
//                                    totalDurations[1].toDouble() / (offsets[1] - offsets[0]) * 100
//                            }
//
//                            val referenceWords =
//                                word.lowercase(Locale.ROOT).split(" ").toTypedArray()
//                            for (j in referenceWords.indices) {
//                                referenceWords[j] =
//                                    referenceWords[j].replace("^\\p{Punct}+|\\p{Punct}+$".toRegex(), "")
//                            }
//                            val diff = DiffUtils.diff(listOf(*referenceWords), recognizedWords, true)
//
//                            var currentIdx = 0
//                            val finalWords = ArrayList<Word>()
//                            val validWord = intArrayOf(0)
//                            for (d in diff.deltas) {
//                                when (d.type!!) {
//                                    DeltaType.EQUAL -> {
//                                        for (i in currentIdx until currentIdx + d.source.size()) {
//                                            finalWords.add(pronWords[i])
//                                        }
//                                        currentIdx += d.target.size()
//                                        validWord[0] += d.target.size()
//                                    }
//
//                                    DeltaType.DELETE, DeltaType.CHANGE -> {
//                                        for (w in d.source.lines) {
//                                            finalWords.add(Word(w, "Omission"))
//                                        }
//                                    }
//
//                                    DeltaType.INSERT -> {
//                                        for (i in currentIdx until currentIdx + d.target.size()) {
//                                            val w = pronWords[i]
//                                            w.errorType = "Insertion"
//                                            finalWords.add(w)
//                                        }
//                                        currentIdx += d.target.size()
//                                    }
//                                }
//                            }
//
//                            val completenessScore = validWord[0].toDouble() / referenceWords.size * 100
//
//                            appendTextLine(
//                                "Paragraph accuracy score: $accuracyScore," +
//                                        " completeness score: $completenessScore, fluency score: $fluencyScore\n",
//                                true
//                            )
//                            for (w in finalWords) {
//                                appendTextLine(
//                                    " word: ${w.word}\taccuracy score: " +
//                                            "${w.accuracyScore}\terror type: ${w.errorType}", false
//                                )
//                            }
//
//                            releaseMicrophoneStream()
//                        }
//
//                        reco.startContinuousRecognitionAsync()
//                    } catch (ex: Exception) {
//                        Log.e(logTag, ex.message ?: "speech error")
//                    }
//                    setShowRecordUtil(true)
//                },
//                modifier = Modifier
//                    .padding(0.5.dp, top = 30.dp)
//                    .size(65.dp)
//                    .background(color = Color(0xFF6F3BDD), shape = CircleShape)
//                    .align(Alignment.CenterHorizontally)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.icon__mic_),
//                    contentDescription = "mic",
//                    tint=micColor
//                )
//            }
//            if (showRecordUtil) {
//                RecordUtil(word)
//            }
            RecordUtil(
                assignedText = word,
                modifierCenter = Modifier
                    .padding(0.5.dp, top = 30.dp)
                    .size(65.dp)
                    .background(color = Color(0xFF6F3BDD), shape = CircleShape)
                 .align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = Color(0xFF6F3BDD),
                        shape = RoundedCornerShape(size = 20.dp)
                    )
                    .padding(20.dp, 7.dp)
            ) {
                Text(
                    text = "듣고 있어요...",
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp),
                text = word,
                letterSpacing= 10.sp,
                style = TextStyle(
                    fontSize = 37.sp,
                    fontWeight = FontWeight(700),
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@DevicePreview
@Composable
fun WordScreenPreview() {
    val navTest= rememberNavController()
    ComonTheme {
        WordScreen(navTest,"word","1")
    }
}