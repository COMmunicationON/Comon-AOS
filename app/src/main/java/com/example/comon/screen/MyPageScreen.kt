package com.example.comon.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.DevicePreview
import com.example.comon.R
import com.example.comon.component.Banner
import com.example.comon.component.MenuItem
import com.example.comon.component.MyPageToolbar
import com.example.comon.component.Profile
import com.example.comon.model.MenuUiModel
import com.example.comon.ui.theme.ComonTheme

@Composable
fun MyPageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.chevron_left),
            contentDescription = "뒤로가기",
            modifier = Modifier.padding(top = 36.dp, start = 24.dp)
        )
        LazyColumn {
            item {
                MyPageToolbar(
                    title = "마이페이지",
                    onItemClick = {},
                    modifier = Modifier.padding(start = 28.dp, end = 28.dp, top = 25.dp)
                )
                Profile(
                    url = "https://avatars.githubusercontent.com/u/54518925?v=4",
                    name = "최상",
                    email = "sang8408@gmail.com"
                )
            }
            item {
                Text(
                    text = "소속단체",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 28.dp, top = 25.dp, bottom = 25.dp)
                )
                Banner(department = "컴퓨터공학부", part = "Android")
                Spacer(modifier = Modifier.height(24.dp))
            }
            items(MenuUiModel.DEFAULT.size) {
                val menu = MenuUiModel.DEFAULT[it]
                MenuItem(title = menu.title, onPress = menu.onPress)
            }
        }
    }
}

@DevicePreview
@Composable
private fun MyPageScreenDevicePreview() {
    ComonTheme {
        MyPageScreen()
    }
}