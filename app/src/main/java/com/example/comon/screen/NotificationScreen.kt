package com.example.comon.screen

import android.app.Notification
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.comon.R

@Composable
fun NotificationScreen(
    navController: NavController
)
{
    Box(modifier = Modifier)
    {
        Column {
            Icon(
                modifier = Modifier
                    .padding(top = 40.dp, start = 30.dp)
                    .clickable {
                        navController.popBackStack() // 이전 화면으로 이동
                    },
                painter = painterResource(id = R.drawable.icon__arrow_left),
                contentDescription = null

            )
        }
    }
}