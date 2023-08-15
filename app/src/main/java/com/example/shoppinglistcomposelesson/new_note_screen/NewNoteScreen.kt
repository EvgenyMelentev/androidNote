package com.example.shoppinglistcomposelesson.new_note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistcomposelesson.R
import com.example.shoppinglistcomposelesson.ui.theme.BlueLight
import com.example.shoppinglistcomposelesson.ui.theme.DarkText
import com.example.shoppinglistcomposelesson.ui.theme.GrayLight
import com.example.shoppinglistcomposelesson.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{ uiEvent ->
            when(uiEvent){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState, snackbarHost = {
        SnackbarHost(hostState = scaffoldState.snackbarHostState){ data ->
            Snackbar(
                snackbarData = data,
                backgroundColor = BlueLight
            )
        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.title,
                            onValueChange = { text ->
                                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                            },
                            label = {
                                Text(
                                    text = "Title...",
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = BlueLight
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(
                                    android.graphics.Color.parseColor(viewModel.titleColor.value)
                                )
                            )
                        )
                        IconButton(
                            onClick = {
                                viewModel.onEvent(NewNoteEvent.OnSave)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.save),
                                contentDescription = "Save",
                                tint = BlueLight
                            )
                        }
                    }
                    TextField(
                        value = viewModel.description,
                        onValueChange = { text ->
                            viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                        },
                        label = {
                            Text(
                                text = "Description...",
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = DarkText
                        )
                    )
                }
            }
        }
    }
}