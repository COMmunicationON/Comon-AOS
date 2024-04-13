package com.example.comon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.comon.component.Numbering
import com.example.comon.ui.theme.ComonTheme

@Composable
fun WordScreen() {
    var word: String by remember { mutableStateOf("수박") }
    var mic: Boolean by remember { mutableStateOf(true) }
    val micColor = if (mic) Color(0xFF14FF00) else Color.Gray

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
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
            )
            {
                Icon(
                    modifier = Modifier.padding(start = 30.dp, top = 5.dp),
                    painter = painterResource(id = R.drawable.icon__arrow_left),
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
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
                verticalAlignment = Alignment.Bottom
            )
            {
                Numbering(num = 1, active = true) // mutableState로 상태관리하여 넘겨줄 것
                Numbering(num = 2, active = false)
                Numbering(num = 3, active = false)
                Numbering(num = 4, active = false)
                Numbering(num = 5, active = false)
                Numbering(num = 6, active = false)
                Numbering(num = 7, active = false)
                Numbering(num = 8, active = false)
                Numbering(num = 9, active = false)
                Numbering(num = 10, active = false)
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
                    .fillMaxHeight(0.45f)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 9.5.dp)
                    )
            )
            {
                Text(
                    modifier = Modifier.padding(top = 17.dp, start = 21.dp),
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
                        .align(Alignment.BottomCenter)
                        .width(250.dp)
                        .height(230.dp)
                        .padding(20.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(0.5.dp, top = 30.dp)
                    .size(65.dp)
                    .background(color = Color(0xFF6F3BDD), shape = CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.icon__mic_),
                    contentDescription = "mic",
                    tint = micColor
                )
            }
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
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
    ComonTheme {
        WordScreen()
    }
}