package com.sindrgl.exercise5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.sindrgl.exercise5.ui.theme.Exercise5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val birthdateState = remember { mutableStateOf(TextFieldValue()) }
    fun add() {
        //addFriend(nameState.value.text, birthdateState.value.text)
        Log.w("Friend", nameState.value.text)
        Log.w("Friend", birthdateState.value.text)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Free_Bitcoins.exe", fontSize = 30.sp)
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Navn") })
        TextField(
            value = birthdateState.value,
            onValueChange = { birthdateState.value = it },
            label = { Text("Kortnummer") }
        )
        Button(onClick = { add() }) {
            Text("Start!")
        }
    }
}