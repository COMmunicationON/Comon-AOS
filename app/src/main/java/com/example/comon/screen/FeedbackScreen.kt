package com.example.comon.screen

import DisplayTextAccuracy
import android.app.ActionBar
import android.media.browse.MediaBrowser
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.CircularProgressbar
import com.example.comon.component.gradientProgressBar
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.util.TrainingResult

@Composable
fun FeedbackScreen(
    openDialog: Boolean,
    onDialogChange: (Boolean) -> Unit,
    entireResult: MutableList<TrainingResult>,
    tempVideoUrl: String
) {

    Log.i("entire", tempVideoUrl.toString())
    // 동영상 재생 여부 상태 변수 추가
    if (openDialog) {
        val scrollState = rememberScrollState()
        val lastResult = entireResult.last()
        val lowestEach = lastResult.let { result ->
            val minAccuracy = result.eachAccuracy?.minOrNull()
            val lowestIndex = minAccuracy?.let { result.eachAccuracy.indexOf(it) }
            lowestIndex?.let { result.each?.getOrNull(it) }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.fillMaxWidth()) {
//                        Icon(
//                            modifier = Modifier
//                                .padding(start = 30.dp)
//                                .clickable {
//                                    //navController.popBackStack() // 이전 화면으로 이동
//                                    onDialogChange(false)
//                                },
//                            painter = painterResource(id = R.drawable.icon__arrow_left),
//                            contentDescription = null
//                        )
                        Box(
                            modifier = Modifier
                                .width(87.dp)
                                .height(32.dp)
                                .background(
                                    color = Color(0xFF6F3BDD),
                                    shape = RoundedCornerShape(size = 20.dp)
                                )
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = "피드백",
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
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp, bottom=10.dp),
                    text = entireResult.last().text.toString(),
                    letterSpacing = 5.sp,
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight(700),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                )


                val word = lastResult.each as ArrayList<String>
                val accuracies = lastResult.eachAccuracy as ArrayList<Int>


                DisplayTextAccuracy(word, accuracies)
                Text(
                    modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
                    text = "' $lowestEach ' 발음이 취약해요!",
                    fontWeight = FontWeight(600),
                    fontSize = 13.sp,
                    color = Color(0xFF6F3BDD),
                )
                Box(
                    modifier = Modifier
                        .border(
                            color = Color.Gray,
                            shape = RoundedCornerShape(20.dp),
                            width = 1.dp
                        )
                        .fillMaxWidth(0.9f)
                        .padding(20.dp)
                )
                {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "발음 점수",
                            fontSize = 15.sp,
                            modifier = Modifier
                                .padding(bottom = 20.dp),
                            //.align(Alignment.CenterHorizontally)
                            fontWeight = FontWeight.Bold
                        )

                        Box(
                            modifier = Modifier
                            // .align(Alignment.CenterHorizontally)
                        )
                        {
                            CircularProgressbar(
                                name = "발음 점수",
                                percent = lastResult.pronunciationScore!!,
                                dataUsage = lastResult.pronunciationScore.toFloat(),//전체 점수
                                size = 120.dp
                            )
                        }
                        Text(
                            text = "점수 분석 결과",
                            modifier = Modifier
                                .padding(top = 40.dp, bottom = 10.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                            //.align(Alignment.CenterHorizontally)
                        )
                        Column() {

                            Row(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    text = "정확도 점수",
                                    modifier = Modifier,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${lastResult.accuracyScore}/100",
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                )
                            }
                            gradientProgressBar(score = lastResult.accuracyScore!!)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    text = "완성도 점수",
                                    modifier = Modifier,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${lastResult.completenessScore}/100",
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                )
                            }
                            gradientProgressBar(score = lastResult.completenessScore!!)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(top = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    text = "유창성 점수",
                                    modifier = Modifier,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${lastResult.fluencyScore}/100",
                                    modifier = Modifier,
                                    fontSize = 12.sp,
                                )
                            }
                            gradientProgressBar(score = lastResult.fluencyScore!!)
                        }
                    }

                }

                Box(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .width(87.dp)
                        .height(32.dp)
                        .background(
                            color = Color(0xFF6F3BDD),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                ) {
                    Text(
                        text = "복습하기",
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
                    text = "나의 입모양과 발음을 다시 한 번 살펴보세요",
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300)
                    )
                )
                Box( //카메라 프리뷰
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.8f)
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally)

                )
                {
                        ExoPlayerVideoPlayer(videoUri = tempVideoUrl)
                }
                Button(
                    onClick = { onDialogChange(false) },
                    border = BorderStroke(2.dp, Color(0xFF6F3BDD)),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp, bottom = 20.dp),
                    colors = ButtonColors(Color.White, Color.White, Color.White, Color.White)
                ) {
                    Text(
                        text = "계속 학습하기",
                        modifier = Modifier,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF6F3BDD),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }


}

@Composable
fun ExoPlayerVideoPlayer(videoUri: String) {
    val context = LocalContext.current
    val exoplayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                play()
            }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoplayer
                layoutParams =
                    ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
    DisposableEffect(Unit) {
        onDispose {
            exoplayer.release()
        }
    }
}


@DevicePreview
@Composable
fun FeedbackScreenPreview() {
    val testNav = rememberNavController()
    ComonTheme {
        //FeedbackScreen(testNav, )
    }
}