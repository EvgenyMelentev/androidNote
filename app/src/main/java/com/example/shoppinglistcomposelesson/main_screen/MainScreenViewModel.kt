package com.example.shoppinglistcomposelesson.main_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistcomposelesson.data.ShoppingListItem
import com.example.shoppinglistcomposelesson.data.ShoppingListRepository
import com.example.shoppinglistcomposelesson.dialog.DialogController
import com.example.shoppinglistcomposelesson.dialog.DialogEvent
import com.example.shoppinglistcomposelesson.shopping_list_screen.ShoppingListEvent
import com.example.shoppinglistcomposelesson.utils.Routes
import com.example.shoppinglistcomposelesson.utils.UiEvent
import com.example.shoppinglistcomposelesson.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
): ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set

    var showFloatingButton = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editableText.value,
                            getCurrentTime(),
                            0,
                            0
                        )
                    )
                }
            }
            is MainScreenEvent.OnNewItemClick -> {
                if(event.route == Routes.SHOPPING_LIST){
                    openDialog.value = true
                } else {
                    sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
                }

            }
            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                showFloatingButton.value = !(event.route == Routes.ABOUT
                        || event.route == Routes.SETTINGS)
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}