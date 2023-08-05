package com.example.android.roomwordssample.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.roomwordssample.Repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class WordViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var rebository: NoteRepository

    private lateinit var viewModel: WordViewModel

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAllWords() {
    }

    @Test
    fun insert() {
    }
}

