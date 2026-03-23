package com.example.test1

import LoginViewModel
import android.R.attr.text
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.test1.ui.theme.Test1Theme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val username = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Test1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
                    when (uiState) {
                        is LoginState.Error -> {
                            Error((uiState as LoginState.Error).error, Modifier.padding(16.dp))
                        }

                        LoginState.Loading -> {
                        }

                        LoginState.Success -> {
                            Welcome("Welcome User", Modifier.padding(16.dp))
                        }

                        LoginState.Initial -> {
                            InitialScreen(username, password, loginViewModel)

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InitialScreen(
    username: MutableState<String>,
    password: MutableState<String>,
    loginViewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        TextField(
            value = username.value,
            onValueChange = { newText ->
                username.value = newText
            },
            label = { Text("Enter username") },
            placeholder = { Text("Enter username") }
        )

        TextField(
            value = password.value,
            onValueChange = { newText ->
                password.value = newText
            },
            label = { Text("Enter password") },
            placeholder = { Text("Enter password") }
        )

        ElevatedButton(
            onClick =
                {
                    loginViewModel.login(username.value, password.value)

                }) {
            Text("Login")
        }
    }

}

@Composable
fun Welcome(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Error(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Test1Theme {
        Greeting("Android")
    }
}