package com.example.kcmav1.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MessageViewModel(application: Application): AndroidViewModel(application) {

    //    we use livedat so we can put an obserer on the data
//    and the UI when the data actually changes
//    Repository is completely separated from the UI through the viewmodel


//    Launching a new coroutine to insert the data in a non-blocking way



}
