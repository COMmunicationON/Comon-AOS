package com.example.comon.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun DifficultyScreen() {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F2F3))
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.icon__arrow_left),
                contentDescription = null,
                modifier = Modifier.padding(top = 40.dp, start = 30.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 40.dp, start = 30.dp)
                    .width(87.dp)
                    .height(32.dp)
                    .background(color = Color(0xFF6F3BDD), shape = RoundedCornerShape(size = 20.dp))
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
            Text(
                modifier = Modifier.padding(top = 15.dp, start = 40.dp),
                text = "난이도",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "나와 맞는 난이도를 선택해\n훈련을 진행하세요",
                modifier = Modifier.padding(start = 40.dp, top = 10.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(300)
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Box(
                    modifier = Modifier
                        .width(161.dp)
                        .height(117.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                )
                {
                    Text(
                        text = "쉬움",
                        modifier = Modifier.padding(20.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .width(161.dp)
                        .height(117.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                )
                {
                    Text(
                        text = "중간",
                        modifier = Modifier.padding(20.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .width(161.dp)
                        .height(117.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                )
                {
                    Text(
                        text = "어려움",
                        modifier = Modifier.padding(20.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

        }
    }
}

@DevicePreview
@Composable
fun DifficultyScreenPreview() {
    ComonTheme {
        DifficultyScreen()
    }
}