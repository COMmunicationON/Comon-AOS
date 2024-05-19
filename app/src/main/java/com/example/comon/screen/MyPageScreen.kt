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
import androidx.compose.ui.draw.rotate
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
        LazyColumn {
            item {
                MyPageToolbar(
                    title = "마이페이지",
                    onItemClick = {},
                    modifier = Modifier.padding(start = 28.dp, end = 28.dp, top = 50.dp, bottom = 40.dp)
                )
                Profile(
                    url = "https://avatars.githubusercontent.com/u/80382025?s=400&u=315cf0772677a2981fa97ab9401863f754e2148b&v=4",
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