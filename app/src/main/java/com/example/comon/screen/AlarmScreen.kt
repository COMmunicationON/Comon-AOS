package com.example.comon.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.comon.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AlarmScreen(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface)) {
        Box(
            modifier = Modifier
                .padding(bottom = 25.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(top = 40.dp, start = 30.dp)
                    .clickable {
                        navController.popBackStack() // 이전 화면으로 이동
                    },
                painter = painterResource(id = R.drawable.icon__arrow_left),
                contentDescription = null

            )
            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                text = "알림",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
        }

        AlarmList()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AlarmList() {
    val items by remember { mutableStateOf(listOf(
        ListItemData(textKind = "음절", headlineText = "음절 훈련을 하지 않은지 2일이 경과 했어요", timeAgo = "1주전"),
        ListItemData(textKind = "단어", headlineText = "9시, 단어 학습 훈련시간입니다!", timeAgo = "1주전"),
        ListItemData(textKind = "문장", headlineText = "문장 훈련을 하지 않은지 일주일이 경과 했어요", timeAgo = "1주전")
    )) }
    val scope = rememberCoroutineScope()

    LazyColumn() {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AlarmListItem(
                    headlineText = {
                        Text(item.headlineText, modifier = Modifier.align(Alignment.Center))
                    },
                    textKind = item.textKind,
                    timeAgo = item.timeAgo!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(15.dp)
                        .padding(start = 10.dp, end = 10.dp)
                )
            }
        }
    }
}


@Composable
fun AlarmListItem(
    headlineText: @Composable () -> Unit,
    textKind: String,
    timeAgo: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        //shape = MaterialTheme.shapes.medium,
        //tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surface

    ) {
        Column(
            modifier=Modifier.fillMaxSize()
        ) {
            Row (
                modifier=Modifier.fillMaxWidth()
                    .padding(bottom = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = textKind,
                    //modifier=Modifier.size(),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF6956E5),
                    )
                )
                Text(
                    text = timeAgo,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF6B7280),

                        )
                )
            }
            headlineText()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmListPreview() {
    AlarmScreen(rememberNavController())
}