package com.example.comon.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.ui.theme.Purple200
import com.example.comon.ui.theme.Purple40
import com.example.comon.ui.theme.Purple500
import com.example.comon.ui.theme.Purple700
import com.example.comon.ui.theme.Teal200

@Composable
fun BarChart(
    data: Map<String, Int>,
    barWidth: Dp = 30.dp,
    animDuration: Int = 1000,
) {
    val totalSum = data.values.sum()
    val maxValue = data.values.maxOrNull() ?: 1

    var animationPlayed by remember { mutableStateOf(false) }

//    val animateHeight by animateFloatAsState(
//        targetValue = if (animationPlayed) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = animDuration,
//            delayMillis = 0,
//            easing = LinearOutSlowInEasing
//        )
//    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    val colors = listOf(
        Purple200,
        Purple500,
        Teal200,
        Purple700,
        Blue
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
            //.verticalScroll(rememberScrollState())
            , verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            data.values.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .background(color = colors[index], shape = RoundedCornerShape(10.dp))
                        .size(
                            barWidth,
                            (200.dp * (value.toFloat() / maxValue.toFloat()))
                        )
                )
            }
        }

        // To see the data in a more structured way
        DetailsBarChart(
            data = data,
            colors = colors
        )
    }
}

@Composable
fun DetailsBarChart(
    data: Map<String, Int>,
    colors: List<Color>
) {

    LazyVerticalGrid(
        modifier = Modifier.padding(10.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(data.keys.toList().zip(data.values.toList())) { item ->
            val index = data.keys.indexOf(item.first)
            DetailsBarChartItem(data = item, color = colors[index])
        }
    }
}

@Composable
fun DetailsBarChartItem(
    data: Pair<String, Int>,
    height: Dp = 30.dp,
    color: Color
) {
    Surface(
        modifier = Modifier.wrapContentHeight(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(
                    text = data.first,
                    fontSize = 15.sp,
                    color = Purple40
                )
                Text(
                    text = "${data.second}%",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun BarChartPreview() {
    ComonTheme {
        BarChart(
            data = mapOf(
                Pair("ㅁ", 40),
                Pair("ㄴ", 20),
                Pair("ㄱ", 10),
                Pair("ㄹ", 50),
            )
        )
    }
}