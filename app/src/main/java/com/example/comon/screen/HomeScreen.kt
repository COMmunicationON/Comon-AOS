package com.example.comon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.CourseBox
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val username: String by remember { mutableStateOf("상이") } //user name
    val weakness by remember { mutableStateOf("ㄴ") } //user weakness

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFDFDFD))
    ) {
        Column()
        {
            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "개인 연습",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 25.dp)
                        .width(21.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.bell),
                        tint = Purple40,
                        contentDescription = "bell"
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = "추천",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 40.dp,
                        spotColor = Color.Gray,
                        ambientColor = Color(0x0A000000)
                    )
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.25f)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 20.dp))
                    .align(Alignment.CenterHorizontally)
            )
            {
                Image(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 24.dp)

                        .width(70.dp)
                        .height(70.dp)
                        .border(
                            width = 0.91603.dp,
                            color = Color(0xFF6F3BDD),
                            shape = RoundedCornerShape(size = 45.80153.dp)
                        ),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    modifier = Modifier.padding(top = 17.dp, start = 21.dp),
                    text = "개인 맞춤 추천 코스",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center,
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 46.dp, start = 21.dp),
                    text = "사용자의 취약한 부분을 모아놓은\n자동 추천 말하기 훈련 코스",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF000000),
                    )
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 15.dp, start = 15.dp)
                        .border(
                            width = 0.95.dp,
                            color = Color(0xFF6F3BDD),
                            shape = RoundedCornerShape(size = 20.dp)
                        )
                        .fillMaxWidth(0.63f)
                        .height(30.dp)
                )
                {
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = username,
                            fontWeight = FontWeight(600),
                            fontSize = 11.sp,
                            color = Color(0xFF6F3BDD),
                        )
                        Text(
                            text = "님은",
                            fontWeight = FontWeight(600),
                            fontSize = 11.sp,
                        )
                        Text(
                            text = " '$weakness' 발음",
                            fontWeight = FontWeight(600),
                            fontSize = 11.sp,
                            color = Color(0xFF6F3BDD),
                        )
                        Text(
                            text = "이 취약하시군요!",
                            fontWeight = FontWeight(600),
                            fontSize = 11.sp
                        )
                    }
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(bottom = 15.dp, end = 15.dp)
                        .width(80.dp)
                        .height(30.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff6956E5),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White,
                    ),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    Text(
                        text = "학습하기",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = "일반 코스",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(modifier = Modifier
                    .clickable {

                    })
                {
                    CourseBox(department = "음절")
                    Image(
                        painter = painterResource(id = R.drawable.um_img),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 30.dp)
                    )
                }
                Box(modifier = Modifier)
                {
                    CourseBox(department = "단어")
                    Image(
                        painter = painterResource(id = R.drawable.word_img),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 30.dp, bottom = 10.dp)
                            .width(40.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(modifier = Modifier)
                {
                    CourseBox(department = "문장")
                    Image(
                        painter = painterResource(id = R.drawable.senten_img),
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .align(Alignment.Center)
                            .padding(top = 50.dp)
                    )
                }
                Box(modifier = Modifier)
                {
                    CourseBox(department = "오답노트")
                    Image(
                        painter = painterResource(id = R.drawable.note_img),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 30.dp, bottom = 26.dp)
                            .width(45.dp)
                    )
                }
            }


        }


    }
}

@DevicePreview
@Composable
fun HomePreview() {
    ComonTheme {
        HomeScreen()
    }
}