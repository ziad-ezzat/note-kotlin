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
import com.example.android.roomwordssample.Retrofit.RetrofitClient
import com.example.android.roomwordssample.model.NoteRoomDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.converter.gson.GsonConverterFactory

class PostNotesWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val noteRepository = NoteRepository(NoteRoomDatabase.getDatabase(applicationContext, CoroutineScope(
            Dispatchers.IO)).noteDao())

        CoroutineScope(Dispatchers.IO).launch {
            val notes = noteRepository.allWords.firstOrNull()

            try {
                val noteService = RetrofitClient.noteService
                for (note in notes!!) {
                    val response = noteService.postNote(note)
                    if (response.isSuccessful) {
                        showNotification("Post Completed","The note was posted successfully")
                    } else {
                        showNotification("Post Failed","Failed to post the note")
                    }
                }
            } catch (e: Exception) {
                showNotification("Post Failed","Failed to post the note")
            }
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
