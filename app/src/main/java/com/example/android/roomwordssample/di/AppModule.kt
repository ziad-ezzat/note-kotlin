package com.example.android.roomwordssample.di

import android.app.Application
import com.example.android.roomwordssample.model.NoteDao
import com.example.android.roomwordssample.model.NoteRoomDatabase
import com.example.android.roomwordssample.Retrofit.NoteService // Import the NoteService class
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent // Import the appropriate component
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Add the @InstallIn annotation with SingletonComponent
object AppModule {

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

    @Singleton
    @Provides
    fun provideNoteService(): NoteService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://test-13589-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NoteService::class.java)
    }
}