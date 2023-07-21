package com.example.android.roomwordssample.view

import android.app.Application
import com.example.android.roomwordssample.Repository.NoteRepository
import com.example.android.roomwordssample.model.NoteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}