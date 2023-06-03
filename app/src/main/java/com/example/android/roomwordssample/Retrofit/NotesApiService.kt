package com.example.android.roomwordssample.Retrofit

import com.example.android.roomwordssample.model.Note
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteService {
    @POST("notes")
    suspend fun postNote(@Body note: Note): Response<Note>
}
