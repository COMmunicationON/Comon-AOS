package com.example.comon.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.comon.ui.theme.Purple40

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
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
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                            .padding(top = 6.dp) //padding 조정
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = item.title,
                        fontSize = 11.sp,
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
    val title: String, val screenRoute: String, val icon: Int,
) {
    object Home : BottomNavItem("홈", "HOME", R.drawable.icon__home)

    object Mypage : BottomNavItem("프로필", "ANALYSIS", R.drawable.icon__user)
    object Statics : BottomNavItem("통계", "TIMELINE", R.drawable.icon__graph)
    object Settings : BottomNavItem("설정", "SETTINGS", R.drawable.icon__arrow_left)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen()
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
    }
}