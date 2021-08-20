package com.example.kcmav1.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


data class Text(val text:String,
                var sender:String,
                var viewType:Int,
                var time:String,
                var date_m_year:String,
                val diocese:String,
                val loves:Int,
                val forum_name:String,
                @ServerTimestamp val sentAt: Timestamp? = null
)