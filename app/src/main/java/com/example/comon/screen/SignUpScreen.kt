package com.example.comon.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.comon.ui.theme.ComonTheme

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var userid by remember {mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("이름") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userid,
            onValueChange = { userid = it },
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
                viewModel.signUp(username, userid, password)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("회원가입")
        }

        val signUpResult by viewModel.signUpResult.observeAsState()
        if (signUpResult is SignUpResult.Failure) {
            val errorMessage = when ((signUpResult as SignUpResult.Failure).errorCode) {
                // Handle different error codes here
                else -> "Sign up failed: Unknown error"
            }
            // Display error message
            Text(errorMessage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ComonTheme {
        SignUpScreen()
    }
}
