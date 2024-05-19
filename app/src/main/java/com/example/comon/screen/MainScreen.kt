package com.example.comon.screen

import RequestRecordAudioPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.comon.R
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.comon.ui.theme.Purple40

@Composable
fun MainScreen(navController: NavHostController) {

    RequestRecordAudioPermission()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarVisible = when (currentRoute) {
        "HOME","PROFILE","STATICS","SETTINGS" -> true
        else -> false
    }

    Scaffold(
            bottomBar = {
                if(bottomBarVisible) {
                    BottomNavigationBar(navController = navController) }
            }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomBarCheck(bottomBarVisible: Boolean, navController: NavHostController) {

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Settings,
        BottomNavItem.Statics,
        BottomNavItem.Mypage
    )

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(17.dp, 17.dp, 0.dp, 0.dp)),
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon!!),
                            contentDescription = item.title,
                            modifier = Modifier
                                .width(25.dp)
                                .height(25.dp)
                                .padding(top = 6.dp, bottom = 3.dp) //padding 조정
                        )
                    },
                    label = {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = item.title,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Purple40
                        )
                    },
                    selectedContentColor = Purple40,
                    unselectedContentColor = Gray,
                    selected = currentRoute == item.screenRoute,
                    alwaysShowLabel = false,
                    onClick = {
                        navController.navigate(item.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }


sealed class BottomNavItem(
    val title: String,
    val screenRoute: String,
    val icon: Int?,
    val isEnabled: Boolean = true // 새로운 필드 추가: 항목이 활성화되어 있는지 여부
) {
    object Home : BottomNavItem("홈", "HOME", R.drawable.icon__home, true)

    object Mypage : BottomNavItem("프로필", "PROFILE", R.drawable.icon__user, true)
    object Statics : BottomNavItem("통계", "STATICS", R.drawable.icon__graph, true)
    object Settings : BottomNavItem("설정", "SETTINGS", R.drawable.icon__arrow_left, true)

    object Difficulty : BottomNavItem("난이도","DIFFICULTY",null, false)
    object Training : BottomNavItem("훈련","TRAINING",null, false)
    object Feedback : BottomNavItem("피드백","FEEDBACK",null, false)

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(navController)
        }
        composable(BottomNavItem.Mypage.screenRoute) {
            MyPageScreen()
        }
        composable(BottomNavItem.Statics.screenRoute) {
            //AnalysisScreen()
        }
        composable(BottomNavItem.Settings.screenRoute) {
            //SettingsScreen()
        }
        composable(BottomNavItem.Difficulty.screenRoute+"/{type}") {
            //val path = navController.previousBackStackEntry?.savedStateHandle?.get<String>("path")
            val type = it.arguments?.getString("type") ?: ""
            DifficultyScreen(navController, path = type)
        }
        composable(BottomNavItem.Training.screenRoute+"/{path}/{level}") {
            //val difficulty = navController.previousBackStackEntry?.savedStateHandle?.get<String>("difficulty")
            val path = it.arguments?.getString("path") ?: ""
            val level = it.arguments?.getString("level") ?: ""
            TrainingScreen(navController,path = path, difficulty = level)
        }
        composable(BottomNavItem.Feedback.screenRoute+"/{tempResultsState}") {
            //val difficulty = navController.previousBackStackEntry?.savedStateHandle?.get<String>("difficulty")
//            val arg1 = it.arguments?.getString("tempResultsState") ?: ""
//            FeedbackScreen(navController,arg1)
        }
    }
}

