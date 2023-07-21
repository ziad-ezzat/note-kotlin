package com.example.android.roomwordssample.di

import android.app.Application
import androidx.room.Room
import com.example.android.roomwordssample.model.NoteDao
import com.example.android.roomwordssample.model.NoteRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent // Import the appropriate component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Add the @InstallIn annotation with SingletonComponent
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application, scope: CoroutineScope): NoteRoomDatabase {
        return NoteRoomDatabase.getDatabase(application, scope)
    }

    @Singleton
    @Provides
    fun provideNoteDao(database: NoteRoomDatabase): NoteDao {
        return database.noteDao()
    }
}