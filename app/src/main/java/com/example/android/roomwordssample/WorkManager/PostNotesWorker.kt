package com.example.android.roomwordssample.WorkManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.android.roomwordssample.R
import com.example.android.roomwordssample.Repository.NoteRepository
import com.example.android.roomwordssample.Retrofit.NoteService
import com.example.android.roomwordssample.model.Note
import com.example.android.roomwordssample.model.NoteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostNotesWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {


    val retrofit = Retrofit.Builder()
        .baseUrl("https://test-13589-default-rtdb.firebaseio.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    var apiInterface: NoteService = retrofit.create(NoteService::class.java)

    override fun doWork(): Result {

        val noteRepository = NoteRepository(
            NoteRoomDatabase.getDatabase(
                applicationContext,
                CoroutineScope(Dispatchers.IO)
            ).noteDao()
        )

        val notes = runBlocking { noteRepository.allWords.firstOrNull() }

        try {
            if (notes != null) {
                for (note in notes) {
                    val call = apiInterface.postNote(note)
                    call.enqueue(object : Callback<Note> {
                        override fun onResponse(call: Call<Note>, response: Response<Note>) {
                            if (response.isSuccessful) {
                                showNotification("Post Completed 1", "The note was posted successfully")
                            } else {
                                showNotification("Post Failed 2", "Failed to post the note")
                            }
                        }
                        override fun onFailure(call: Call<Note>, t: Throwable) {
                            showNotification("Error 3", "Failed to make the network request")
                        }
                    })
                }
            }
        } catch (e: Exception) {
            showNotification("Error 4", "Failed to fetch notes")
            val call = apiInterface.postNote(Note("test"))
            call.enqueue(object : Callback<Note> {
                override fun onResponse(call: Call<Note>, response: Response<Note>) {
                    if (response.isSuccessful) {
                        showNotification("Post Completed 5", "The note was posted successfully")
                    } else {
                        showNotification("Post Failed 6", "Failed to post the note")
                    }
                }
                override fun onFailure(call: Call<Note>, t: Throwable) {
                    showNotification("Error 7", "Failed to make the network request")
                }
            })
        }

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "work_notifications"
        val notificationId = 1

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel for devices running Android 8.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Work Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
