package com.example.android.roomwordssample

import android.app.Application
import androidx.room.Room
import com.example.android.roomwordssample.Repository.NoteRepository
import com.example.android.roomwordssample.model.NoteRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteRoomDatabase
    {
        return Room.databaseBuilder(
            app,
            NoteRoomDatabase::class.java,
            NoteRoomDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteRoomDatabase): NoteRepository
    {
        return NoteRepository(db.noteDao())
    }

}