package com.example.shoppinglistcomposelesson.note_list_screen

import android.annotation.SuppressLint
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistcomposelesson.dialog.MainDialog
import com.example.shoppinglistcomposelesson.shopping_list_screen.UiShoppingListItem
import com.example.shoppinglistcomposelesson.ui.theme.BlueLight
import com.example.shoppinglistcomposelesson.ui.theme.EmptyText
import com.example.shoppinglistcomposelesson.ui.theme.GrayLight
import com.example.shoppinglistcomposelesson.ui.theme.Red
import com.example.shoppinglistcomposelesson.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ uiEven ->
            when(uiEven){
                is UiEvent.Navigate -> {
                    onNavigate(uiEven.route)
                }
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEven.message,
                        actionLabel = "Undone"
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(NoteListEvent.UnDoneDeleteItem)
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState){ data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ,
                shape = RoundedCornerShape(15.dp)
            ) {
                TextField(
                    value = viewModel.searchText,
                    onValueChange = { text ->
                       viewModel.onEvent(NoteListEvent.OnTextSearchChange(text))
                    },
                    label = {
                        Text(text = "Search...")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ){
                items(viewModel.noteList){ item ->
                    UiNoteItem(viewModel.titleColor.value , item){ event ->
                        viewModel.onEvent(event)
                    }
                }
            }
            MainDialog(viewModel)
            if(viewModel.noteList.isEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    text = "Empty",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = EmptyText
                )
            }
        }
    }
}