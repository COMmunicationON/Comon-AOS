package com.example.comon.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.comon.R
import com.example.comon.ui.theme.ComonTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.sample_logo),
            contentDescription = "로고"
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "COMON",
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.pretendardvariable)
                ),
                fontSize = 25.sp, // Adjust the font size as needed
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
                color = Color(0xff6956E5)
            )

        )
        Spacer(modifier = Modifier.height(70.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("ID") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Log.d("로그인",username)
                viewModel.login(username, password)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff6956E5),
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
        ) {
            Text("로그인")
        }

        val loginResult by viewModel.loginResult.observeAsState()
        if (loginResult is LoginResult.Failure) {
            val errorMessage = when ((loginResult as LoginResult.Failure).errorCode) {
                200 -> "로그인 성공"
                400 or 401 -> "로그인 실패 : 아이디나 비밀번호가 올바르지 않습니다"
                500 -> "로그인 실패 : 서버 오류"
                -1 -> "응답 X"
                -2 -> "타임아웃"
                else -> "로그인 실패 : 알 수 없는 오류"
            }
            Log.d("로그인 응답",errorMessage)
            Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Log.d("로그인",username)
                viewModel.login(username, password)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x00000000),
                contentColor = Color.Black,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ),
        ) {
            Text("회원가입 하기")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ComonTheme {
        LoginScreen()
    }
}
