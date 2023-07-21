package com.example.android.roomwordssample.di

import com.example.android.roomwordssample.Retrofit.NoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Add the @InstallIn annotation with SingletonComponent
object NetworkModule {

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