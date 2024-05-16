package com.example.comon.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.example.comon.ui.theme.ComonTheme

@Composable
fun SettingFaceScreen(
    navController: NavController,
    path: String
) {
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF3F2F3))
    )
    {
        //Text(text = path, color = Color.Black) //console
        Column(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Icon(
                modifier = Modifier
                    .padding(top = 40.dp, start = 30.dp)
                    .clickable {
                        navController.popBackStack() // 이전 화면으로 이동
                    },
                painter = painterResource(id = R.drawable.icon__arrow_left),
                contentDescription = null

            )
            Box(
                modifier = Modifier
                    .padding(top = 30.dp, start = 30.dp)
                    .width(87.dp)
                    .height(32.dp)
                    .background(color = Color(0xFF6F3BDD), shape = RoundedCornerShape(size = 20.dp))
            ) {
                Text(
                    text = "잠깐!",
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
                text = "화면설정",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "내 입모양 확인을 위해 \n밑의 입 모양에 입 위치를 맞춰주세요.",
                modifier = Modifier.padding(start = 40.dp, top = 10.dp, bottom = 50.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(300)
                )
            )
            Box( //카메라 프리뷰
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f)
                    .align(Alignment.CenterHorizontally)
            )
            {

            }
            Button(
                onClick = {},
                border = BorderStroke(2.dp, Color(0xFF6F3BDD)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top=50.dp),
                colors = ButtonColors(Color.White,Color.White,Color.White,Color.White)
            ) {
                Text(
                    text = "시작하기",
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF6F3BDD),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }
    }
}

@DevicePreview
@Composable
fun SettingFaceScreenPreview() {
    val testNav = rememberNavController()
    ComonTheme {
        SettingFaceScreen(testNav, "word")
    }
}