package com.example.shoppinglistcomposelesson.shopping_list_screen

import com.example.shoppinglistcomposelesson.data.ShoppingListItem

sealed class ShoppingListEvent{
    data class OnShowDeleteDialog(val item: ShoppingListItem): ShoppingListEvent()
    data class OnShowEditDialog(val item: ShoppingListItem): ShoppingListEvent()
    data class OnItemClick(val route: String): ShoppingListEvent()
    object OnItemSave: ShoppingListEvent()
}
