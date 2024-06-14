package com.example.comon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.CourseBox
import com.example.comon.component.MyPageToolbar
import com.example.comon.component.PersonalInformation
import com.example.comon.component.Profile
import com.example.comon.ui.theme.ComonTheme
import com.example.comon.ui.theme.Purple40

@Composable
fun MypageScreen(navController : NavController, viewModel: HomeViewModel = viewModel()) {



    val username: String by remember { mutableStateOf("상이") } //user name
    var weakness by remember { mutableStateOf("ㄴ") } //user weakness

    val trainingData by viewModel.trainingData.observeAsState()



    LaunchedEffect(Unit) {
        viewModel.fetchRecommendData()
    }

    LaunchedEffect(key1 = trainingData) {
        trainingData?.let { response ->
            val data = response.weak_phoneme
            data?.let {
                // 단어, 음절, 음소 가져와서 로그에 출력
                weakness = it
            }
        }
    }


    Box(
        modifier = Modifier
            .statusBarsPadding()
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
                    text = "마이페이지",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                )

            }
            Spacer(modifier = Modifier.height(70.dp))
            Profile(url = "https://avatars.githubusercontent.com/u/80382025?s=400&u=315cf0772677a2981fa97ab9401863f754e2148b&v=4", name = "최상", email = "sang8408@naver.com")
            Spacer(modifier = Modifier.height(86.dp))
            Text(
                modifier = Modifier.padding(start = 30.dp, bottom = 10.dp),
                text = "설정",
                style = TextStyle(
                    fontSize = 20.sp,
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
                Box(modifier = Modifier)
                {
                    CourseBox(department = "훈련\n알림시간\n설정", navController)
                }
                Box(modifier = Modifier)
                {
                    CourseBox(department = "개인정보\n설정", navController)
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(modifier = Modifier)
                {
                    CourseBox(department = "접근권한\n설정", navController)
                }
                Box(modifier = Modifier)
                {
                    CourseBox(department = "공지사항", navController)
                }
            }


        }


    }
}

@DevicePreview
@Composable
fun MypagePreview() {
    val nav = rememberNavController()
    ComonTheme {
        MypageScreen(nav)
    }
}
