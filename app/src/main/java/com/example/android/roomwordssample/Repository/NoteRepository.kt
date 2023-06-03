package com.example.android.roomwordssample.Repository

import androidx.annotation.WorkerThread
import com.example.android.roomwordssample.model.Note
import com.example.android.roomwordssample.model.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allWords: Flow<List<Note>> = noteDao.getAllNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

}
