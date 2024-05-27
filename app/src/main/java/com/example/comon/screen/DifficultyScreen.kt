package com.example.comon.screen

import androidx.camera.core.processing.SurfaceProcessorNode.In
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun DifficultyScreen(
    navController : NavController,
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
                    .padding(top=30.dp)
                    .align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Center,
            ) {
                DifficultyComponent(navController,path,1)
                DifficultyComponent(navController,path,2)
                DifficultyComponent(navController,path,3)
            }

        }
    }
}

@Composable
fun DifficultyComponent(
    navController: NavController,
    path: String,
    level: Int
)
{
    Button(
        onClick = {
            navController.navigate("SETTING_FACE/$path/$level")
        },
        modifier = Modifier
            .padding(10.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = Color.LightGray,
                ambientColor = Color(0x0A000000),
                shape = RoundedCornerShape(size = 10.dp)
            ),
        colors = ButtonDefaults.buttonColors(Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(size = 20.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(110.dp)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 18.dp)
                )
        ) {
            Text(
                text = when(level) {
                    1 -> "쉬움"
                    2 -> "중간"
                    3 -> "어려움"
                    else -> "Unknown Level"
                },
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

@DevicePreview
@Composable
fun DifficultyScreenPreview() {
    val testNav = rememberNavController()
    ComonTheme {
        DifficultyScreen(testNav,"word")
    }
}