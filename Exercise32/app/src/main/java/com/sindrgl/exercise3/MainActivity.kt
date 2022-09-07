package com.sindrgl.exercise3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sindrgl.exercise3.ui.theme.Exercise3Theme

class MainActivity : ComponentActivity() {
    private val friendsViewModel by viewModels<FriendsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercise3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    mainBox(friends = friendsViewModel.friends, addFriend = friendsViewModel::addFriend , editFriend = friendsViewModel::updateFriend)
                }
            }
        }
    }
}

class FriendsViewModel : ViewModel() {

    var friends = mutableStateListOf<Friend>()
        private set

    fun addFriend(name: String, birthDate: String) {
        val id = (friends.map { it.id }.maxOrNull() ?: 0) + 1
        Log.w("Id", id.toString())
        friends.add(Friend(id, name, birthDate))
    }

    fun updateFriend(id: Int, name: String, birthdate: String) {
        friends.filter { it.id == id }.forEach {
            it.name = name
            it.birthDate = birthdate
        }
    }
}

@Composable
private fun mainBox(
    friends: List<Friend>,
    addFriend: (String, String) -> Unit,
    editFriend: (Int, String, String) -> Unit
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "friendsList") {
        composable(route = "friendsList") {
            FriendsListScreen(navController, friends, addFriend)
        }
        composable(
            route = "friendDetails/{friendId}",
            arguments = listOf(navArgument("friendId") { type = NavType.IntType })
        ) {
            FriendDetailsScreen(
                navController,
                it.arguments!!.getInt("friendId"),
                friends,
                editFriend
            )
        }
    }
}

@Composable
fun FriendsListScreen(
    navController: NavController,
    friends: List<Friend>,
    addFriend: (String, String) -> Unit
) {
    Column {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            item {
                AddFriendBox(addFriend)
                Divider()
                FriendsListBox(navController, friends)
            }
        }
    }
}

@Composable
fun AddFriendBox(
    addFriend: (String, String) -> Unit
) {
    val nameState = remember { mutableStateOf(TextFieldValue()) }
    val birthdateState = remember { mutableStateOf(TextFieldValue()) }
    fun add() {
        addFriend(nameState.value.text, birthdateState.value.text)
        Log.w("Friend", nameState.value.text)
        Log.w("Friend", birthdateState.value.text)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Friends App!", fontSize = 30.sp)
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") })
        TextField(
            value = birthdateState.value,
            onValueChange = { birthdateState.value = it },
            label = { Text("Birthdate") }
        )
        Button(onClick = { add() }) {
            Text("Add friend")
        }
    }
}

@Composable
fun FriendsListBox(
    navController: NavController,
    friends: List<Friend>,
) {
    fun add(id: Int) {
        navController.navigate("friendDetails/${id}")
        Log.w("Friend", id.toString())
    }
    for (item in friends) {
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly,verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(item.name)
                Text(item.birthDate)
                Button(onClick = { add(item.id) }) {
                    Text("Edit")
                }
            }
        }
    }
}

@Composable
fun FriendDetailsScreen(
    navController: NavController,
    friendId: Int,
    friends: List<Friend>,
    editFriend: (Int, String, String) -> Unit
) {
    val friend = friends.find { it.id == friendId }
        ?: return Scaffold {
            Button(onClick = {
                navController.navigate("friendsList")
            }) {
                Text("Something went wrong...")
            }
        }
    val nameState = remember { mutableStateOf(TextFieldValue(text = friend.name)) }
    val birthdateState = remember { mutableStateOf(TextFieldValue(text = friend.birthDate)) }
    fun saveFriend() {
        editFriend(friendId, nameState.value.text, birthdateState.value.text)
        navController.navigate("friendsList")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "Edit your friend", fontSize = 30.sp)
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") })
        TextField(
            value = birthdateState.value,
            onValueChange = { birthdateState.value = it },
            label = { Text("Birthdate") }
        )
        Button(onClick = { saveFriend() }) {
            Text("Save changes")
        }
    }
}

