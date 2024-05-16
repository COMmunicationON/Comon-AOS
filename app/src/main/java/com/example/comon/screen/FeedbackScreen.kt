package com.example.comon.screen

import DisplayTextAccuracy
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.CircularProgressbar
import com.example.comon.component.gradientProgressBar
import com.example.comon.ui.theme.ComonTheme

@Composable
fun FeedbackScreen(
    navController: NavController,
    assignedText: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .clickable {
                            navController.popBackStack() // 이전 화면으로 이동
                        },
                    painter = painterResource(id = R.drawable.icon__arrow_left),
                    contentDescription = null
                )
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
                .padding(top = 20.dp),
            text = "수박",
            letterSpacing= 5.sp,
            style = TextStyle(
                fontSize = 25.sp,
                fontWeight = FontWeight(700),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
        )
        val word = listOf("ㅅ", "ㅜ", "ㅂ", "ㅏ", "ㄱ")
        val accuracies = listOf(0.9, 0.7, 0.8, 0.6, 0.4)
        DisplayTextAccuracy(word, accuracies)
        Text(
            modifier = Modifier.padding(top=10.dp, bottom = 20.dp),
            text = "' ${word[4]} '"+" 발음이 취약해요!",
            fontWeight = FontWeight(600),
            fontSize = 11.sp,
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
                        percent = 80, //전체 점수
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
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Text(
                            text = "정확도 점수",
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "90/100",
                            modifier = Modifier,
                            fontSize = 12.sp,
                        )
                    }
                    gradientProgressBar(score = 90)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Text(
                            text = "완성도 점수",
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "80/100",
                            modifier = Modifier,
                            fontSize = 12.sp,
                        )
                    }
                    gradientProgressBar(score = 80)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween)
                    {
                        Text(
                            text = "유창성 점수",
                            modifier = Modifier,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "70/100",
                            modifier = Modifier,
                            fontSize = 12.sp,
                        )
                    }
                    gradientProgressBar(score = 70)
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
            text = "나와 맞는 난이도를 선택해 훈련을 진행하세요",
            modifier = Modifier.padding(top = 20.dp),
            style = TextStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight(300)
            )
        )
        Box( //카메라 프리뷰
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f)
                .align(Alignment.CenterHorizontally)
        )
        {

        }
    }

}

@DevicePreview
@Composable
fun FeedbackScreenPreview() {
    val testNav = rememberNavController()
    ComonTheme {
        FeedbackScreen(testNav, "hello")
    }
}