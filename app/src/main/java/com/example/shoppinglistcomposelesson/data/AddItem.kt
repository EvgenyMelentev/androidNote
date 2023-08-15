package com.example.shoppinglistcomposelesson.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_item_test")
data class AddItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val isCheck: Boolean,
    val listId: Int
)
