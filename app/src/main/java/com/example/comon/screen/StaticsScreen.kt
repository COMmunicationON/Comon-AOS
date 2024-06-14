package com.example.comon.screen

import android.media.ImageReader
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comon.DevicePreview
import com.example.comon.component.BarChart
import com.example.comon.component.MyProgressBanner
import com.example.comon.component.RowProgressBar
import com.example.comon.component.StaticsName
import com.example.comon.ui.theme.ComonTheme

@Composable
fun StaticsScreen(viewModel: HomeViewModel = viewModel()) {
    val totalCountData by viewModel.totalCountData.observeAsState()
    val weakPronData by viewModel.weakPronData.observeAsState()
    val partAverage by viewModel.partAverage.observeAsState()

    var percentage by remember { mutableIntStateOf(4) }
    var percentageNum by remember { mutableIntStateOf(593) }

    var barChartData by remember { mutableStateOf(mapOf<String, Int>()) }
    var barChartData2 by remember { mutableStateOf(mapOf<String, Int>()) }

    var pronSyllable by remember { mutableIntStateOf(7) }
    var pronWord by remember { mutableIntStateOf(7) }
    var pronSentence by remember { mutableIntStateOf(7) }

    LaunchedEffect(Unit) {
        viewModel.fetchTotalCount()
        viewModel.fetchPartAverage()
        viewModel.fetchWeakPron()
    }

    LaunchedEffect(key1 = totalCountData) {
        totalCountData?.let { response ->
            response.percentage?.let {
                percentage = (it / 10).toInt()
            }
            response.totalCount?.let {
                percentageNum = it
            }
        }
    }

    LaunchedEffect(key1 = weakPronData) {
        weakPronData?.let { response ->
            // Create a map from the response
            val chartData = response.mapNotNull { item ->
                // Ensure both phoneme and weakPercentage are not null
                item.phoneme?.let { phoneme ->
                    item.weakPercentage?.let { weakPercentage ->
                        phoneme to weakPercentage
                    }
                }
            }.toMap()
            barChartData = chartData
        }
    }

    LaunchedEffect(key1 = partAverage) {
        partAverage?.let { response ->
            val syllableAverage = response.syllable_average ?: 0f
            val wordAverage = response.word_average ?: 0f
            val sentenceAverage = response.sentence_average ?: 0f

            pronSyllable = syllableAverage.toInt()
            pronWord = wordAverage.toInt()
            pronSentence = sentenceAverage.toInt()

            barChartData2 = mapOf(
                "음절" to pronSyllable,
                "단어" to pronWord,
                "문장" to pronSentence
            )
        }
    }

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(color = Color(0xFFFDFDFD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "훈련 통계",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )
            }
            StaticsName("My Progress", "나의 진척도를 나타냅니다")
            MyProgressBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
            StaticsName("Answer Percentage", "전체 갯수 중 총 맞춘 갯수")
            RowProgressBar(progressCount = percentage, correctCount = percentageNum)
            StaticsName("My Weakness", "내가 가장 취약한 부분은?")
            BarChart(data = barChartData)
            StaticsName("Train Accuracy", "훈련 분야별 학습 진척도")
            BarChart(data = barChartData2)
        }
    }
}

@DevicePreview
@Composable
fun StaticsPreview() {
    ComonTheme {
        StaticsScreen()
    }
}
