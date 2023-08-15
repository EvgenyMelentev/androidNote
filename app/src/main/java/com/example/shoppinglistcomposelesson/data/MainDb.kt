package com.example.shoppinglistcomposelesson.data

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        TestItem::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    abstract val shoppingListDao: ShoppingListDao
    abstract val noteDao: NoteDao
    abstract val addItemDao: AddItemDao
}