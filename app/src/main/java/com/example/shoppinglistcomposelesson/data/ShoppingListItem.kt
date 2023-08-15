package com.example.shoppinglistcomposelesson.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_name")
data class ShoppingListItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val my_time: String,
    val allItemsCount: Int,
    val allSelectedItemsCount: Int
)
