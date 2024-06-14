package com.example.comon.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberAsyncImagePainter
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.Numbering
import com.example.comon.model.TrainingModel
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.util.RecordUtil
import com.example.comon.util.TextToSpeechHelper
import com.example.comon.util.TrainingResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

@Composable
fun TrainingScreen(
    navController: NavHostController,
    path: String?,
    difficulty: String?,
    viewModel: TrainingViewModel = viewModel(),
    viewModel2: HomeViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var courseLevel by rememberSaveable { mutableIntStateOf(1) } //1번 문제 (1/10)
    var trainingString by rememberSaveable { mutableStateOf("") } //수박
    var trainingStringSyllable by rememberSaveable { mutableStateOf(listOf("")) } //[수,박]
    var trainingStringPhonemes by rememberSaveable { mutableStateOf(listOf("")) } //[ㅅ,ㅜ,ㅂ,ㅏ,ㄱ]
    //val referenceText by remember { mutableStateOf("꿈만 같아") } //테스트
//    val navigate =  remember(courseLevel) {
//        if (courseLevel==10) navController.navigate("HOME")
//    }

    val trainingData by viewModel.trainingData.observeAsState()

    var openDialog by remember { mutableStateOf(false) }
    var openBackDialog by remember { mutableStateOf(false) }
    var tempResultsState = remember { mutableListOf<TrainingResult>() }

    val context = LocalContext.current
    val textToSpeechHelper = remember { TextToSpeechHelper(context) }

    var videoUri by remember { mutableStateOf("") }

    var imageIndex by remember { mutableIntStateOf(0) }
    var imageList by rememberSaveable { mutableStateOf(listOf("")) }


    LaunchedEffect(key1 = courseLevel) {
        while (true) {
            delay(Duration.ofSeconds(1))
            imageIndex = (imageIndex + 1) % imageList.size
        }
    }

    DisposableEffect(textToSpeechHelper) {
        onDispose {
            textToSpeechHelper.shutdown()
        }
    }

    // courseLevel을 증가시키는 함수 정의
    val onCourseLevelChange: (Int) -> Unit = {
        if (courseLevel == 10) {
            //navController.popBackStack()
        } else {
            //navController.navigate("HOME")
            courseLevel++
        }
    }

    val onDialogChange: (Boolean) -> Unit = { new ->
        openDialog = new
    }

    val onClose: () -> Unit = {
        openBackDialog = false
        navController.navigate("HOME") {
            popUpTo("first") { inclusive = true }
        }
    }

    val onDismiss: () -> Unit = {
        openBackDialog = false
    }

    val addResults: (TrainingResult) -> Unit = {
        tempResultsState.add(it)
        Log.d("tempResult",tempResultsState.toString())

        if (tempResultsState.size >= 10) {
            scope.launch {
                viewModel.sendResultsToServer(tempResultsState.toList())
                tempResultsState.clear()
            }
        }
    }

    val onResult: (String) -> Unit ={
        videoUri = it
    }

    LaunchedEffect(tempResultsState) {
        //Log.d("tempResult",tempResultsState.toString())
    }

    LaunchedEffect(Unit) {
        trainingString = null.toString()
        val trainingModel = TrainingModel(type = path, level = difficulty!!.toInt())
        viewModel.fetchTrainingData(trainingModel)
    }

    LaunchedEffect(trainingData, courseLevel) {
        if (courseLevel == 10) navController.navigate("HOME")
        trainingData?.let { response ->
            //Log.d("!!", "전체: $dataList")
            // courseLevel에 해당하는 인덱스의 데이터 가져오기
            val data = response.datas?.get(courseLevel - 1)
            data?.let {
                trainingString = it.data!!
                trainingStringSyllable = it.syllables!!
                trainingStringPhonemes = it.phonemes!!
                imageList = it.phonemeImages!!
//                Log.d("WordScreen", "Word: ${it.data}")
//                Log.d("WordScreen", "Syllables: ${it.syllables}")
//                Log.d("WordScreen", "SyllablesIndes: ${it.syllables[0]}")
//                Log.d("WordScreen", "Phonemes: ${it.phonemes}")
//                Log.d("WordScreen", "PhonemesIndex: ${it.phonemes[0]}")
                Log.d("WordScreen", "phonemeImages: ${it.phonemeImages}")
            }
        }
    }

    BackHandler(
        onBack = {
            openBackDialog = true
        }
    )

    BackDialog(openBackDialog, onClose = onClose, onDismiss = onDismiss)
    FeedbackScreen(openDialog, onDialogChange, tempResultsState, videoUri)
    if (!openDialog) {
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
                    Icon(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .clickable {
                                openBackDialog = true
                            },
                        painter = painterResource(id = R.drawable.icon__cancel),
                        contentDescription = null
                    )
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
                    modifier = Modifier
                        .padding(start = 15.dp, bottom = 5.dp)
                        .align(Alignment.CenterHorizontally),
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
                            text = "따라해보세요: $trainingString",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight(600),
                                textAlign = TextAlign.Center,
                            )
                        )
//                        Image(
//                            painter = painterResource(id = R.drawable.sample_word_activity),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .align(Alignment.CenterHorizontally)
//                                .width(250.dp)
//                                .height(230.dp)
//                                .padding(20.dp)
//                                .clickable {
//                                    textToSpeechHelper.speak(trainingString)
//                                }
//                        )
                        Image(
                            painter = rememberAsyncImagePainter(imageList[imageIndex]),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(250.dp)
                                .height(230.dp)
                                .padding(20.dp)
                                .clickable {
                                    textToSpeechHelper.speak(trainingString)
                                }
                        )
                    }

                }
                RecordUtil(
                    navController = navController,
                    assignedText = trainingString,
                    syllable = trainingStringSyllable,
                    originPhoneme = trainingStringPhonemes,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    path = path!!,
                    difficulty = difficulty!!,
                    courseLevel = courseLevel, // courseLevel 전달
                    onCourseLevelChange = onCourseLevelChange, // courseLevel 변경 이벤트 핸들러 전달
                    openDialog = openDialog,
                    onDialogChange = onDialogChange,
                    addResults = addResults,
                    onResult = onResult
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(10.dp, 20.dp, 10.dp, 15.dp),
                    text = trainingString,
                    letterSpacing = 10.sp,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }

    }
}

@Composable
fun BackDialog(openBackDialog: Boolean, onClose: () -> Unit, onDismiss: ()->Unit) {
    if (openBackDialog) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = onDismiss,
            title = { Text("잠깐!") },
            text = {
                Text(
                    text = "지금 종료하면 저장이 되지 않습니다. \n 그래도 나가시겠습니까?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White, // 버튼 색 변경
                        containerColor = Color(0xFF6F3BDD)
                    )
                ) {
                    Text("네")
                }
            }
        )
    }
}

@DevicePreview
@Composable
fun WordScreenPreview() {
    val navTest = rememberNavController()
    ComonTheme {
        TrainingScreen(navTest, "word", "1")
    }
}