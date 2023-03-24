package com.example.android.roomwordssample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.roomwordssample.model.Note
import com.example.android.roomwordssample.Repository.NoteRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: NoteRepository) : ViewModel() {
    val allWords: LiveData<List<Note>> = repository.allWords.asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }
}

class WordViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
