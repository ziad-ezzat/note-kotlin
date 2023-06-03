package com.example.android.roomwordssample.Retrofit

import com.example.android.roomwordssample.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteService {
    @POST("notes.json")
    fun postNote(@Body note: Note): Call<Note>
}
