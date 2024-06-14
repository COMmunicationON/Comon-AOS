package com.example.comon.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.comon.ui.theme.MainPurple
import com.example.comon.ui.theme.Purple200
import com.example.comon.ui.theme.Purple40
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun NoteScreen(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(bottom = 30.dp)
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
                text = "오답노트",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center
                )
            )
        }

        SwipeToDeleteList()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SwipeToDeleteList() {
    var items by remember { mutableStateOf(listOf(
        ListItemData(textKind = "음절", headlineText = "가",null),
        ListItemData(textKind = "단어", headlineText = "수박",null),
        ListItemData(textKind = "문장", headlineText = "나 오늘 밥 먹었어",null)
    )) }
    val scope = rememberCoroutineScope()

    LazyColumn {
        items(items) { item ->
            var offsetX by remember { mutableStateOf(0f) }
            var isSwiped by remember { mutableStateOf(false) }
            val animatedOffsetX by animateFloatAsState(targetValue = offsetX)
            Box(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                offsetX += dragAmount
                            },
                            onDragEnd = {
                                if (offsetX < 150) {
                                    scope.launch {
                                        items = items.filterNot { it == item }
                                    }
                                } else {
                                    offsetX = 0f
                                }
                            }
                        )
                    }
            ) {
                ListItem(
                    headlineText = { Text(item.headlineText, modifier = Modifier.align(Alignment.Center)) },
                    textKind = item.textKind,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .padding(start = 15.dp, end = 15.dp)
                )
            }
        }
    }
}

data class ListItemData(val textKind: String, val headlineText: String, val  timeAgo : String?)


@Composable
fun ListItem(
    headlineText: @Composable () -> Unit,
    textKind: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        )
        {
            Column {
                Text(
                    text = textKind,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MainPurple,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ){
                    headlineText()
                    Icon(
                        painter = painterResource(id = R.drawable.icon__sound_high),
                        contentDescription = "sound",
                        tint = MainPurple,
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeToDeleteListPreview() {
    NoteScreen(rememberNavController())
}
