package com.example.kcmav1.room

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ForumViewModel(application: Application):AndroidViewModel(application) {

//    we use livedata so we can put an observe on the data
//    And the UI when the data actually changes
//    Repository is completely separated from the UI through the viewmodel
    val readAllForums:LiveData<List<Forum>>
    val repository:ForumRepository

    init {
        val forumDao=ForumRoomDatabase.getDatabase(application).roomDao()
        repository= ForumRepository(forumDao)
        readAllForums=repository.allForums
    }
//    Launching a new coroutine to insert the data in a non-blocking way


    fun addForum(forum: Forum){
        viewModelScope.launch {
            repository.addForum(forum)
        }
    }
}


