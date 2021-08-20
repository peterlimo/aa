package com.example.kcmav1

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.dashboard.test.Adapter
import com.example.kcmav1.model.Message
import com.example.kcmav1.model.Text
import com.example.kcmav1.room.MessageViewModel
import com.example.kcmav1.utils.AppPreferences
import com.example.kcmav1.utils.toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class ChatPageActivity : AppCompatActivity() {
    var type by Delegates.notNull<Int>()
    lateinit var send_btn:LinearLayout
    lateinit var message_text:EditText
    lateinit var message_recyler:RecyclerView
    lateinit var messageViewModel: MessageViewModel
    lateinit var db:FirebaseFirestore
    lateinit var groupId:String
    lateinit var userId:String
    lateinit var toolbar: Toolbar
    var imageId =0
    lateinit var userEmail:String
    var isLiked:Boolean=true
    var viewType =0
    val text=ArrayList<Message>()
    lateinit var currentDiocese:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)
        messageViewModel=ViewModelProvider(this).get(MessageViewModel::class.java)
        send_btn=findViewById(R.id.button_send_message)
        message_text=findViewById(R.id.edit_text_message)
        message_recyler=findViewById(R.id.recycler_gchat)
        toolbar=findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        ///getting the group id
        groupId=intent.getStringExtra("room").toString()

        supportActionBar?.title=groupId
        //initializing progress dialog
        val progress=ProgressDialog(this)
        //initializing the firebase
        db=FirebaseFirestore.getInstance()
        //code to fetch messages and display on the recyclerview
        val adapter= Adapter(this, text)

        message_recyler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        db.collection("rooms").document(groupId).collection("messages").orderBy("time", Query.Direction.DESCENDING).get().addOnSuccessListener {
//            result->
            db.collection("rooms").document(groupId).collection("messages").orderBy("sentAt", Query.Direction.ASCENDING)
                     .addSnapshotListener { value, e ->
                        if (e!=null){
                            return@addSnapshotListener

                        }
                         text.clear()
                        for (doc in value!!)
                        {
                            if (doc.exists())
                            {
                                val did=doc.id

                                val mess=doc.get("text").toString()
                                val user=doc.get("sender").toString()

                                val time=doc.get("time").toString()
                                val fname=doc.get("forum_name").toString()
                                val timeat=doc.get("sentAt").toString()
                                val date_m_year=doc.get("date_m_year").toString()
                                val loves=doc.get("loves").toString()
                                if (user.equals(userId)){
                                    viewType=1
                                }
                                else if (
                                        user.equals("admin")
                                )
                                {
                                    viewType=2
                                }
                                else
                                {
                                    viewType=3
                                }

                                text.add(Message(did,mess, user, viewType,time,currentDiocese,date_m_year,fname,loves,isLiked))

                                message_recyler.adapter=adapter
                                message_recyler.scrollToPosition(text.size-1)

                            }
                            else
                            {
                                toast("No messages found")
                            }
                        }
                         adapter.notifyDataSetChanged()

                    }



        AppPreferences.init(this)
        if (AppPreferences.isLogin){
          userId= AppPreferences.username
            currentDiocese=AppPreferences.diocese
            userEmail=AppPreferences.email
        }

//get the phone timestamp

//code to send message to room

        send_btn.setOnClickListener {
            val tsLong = System.currentTimeMillis() / 1000
            val ts = tsLong.toString()
            val dateFormat = SimpleDateFormat("HH:mm")
            val mdy=SimpleDateFormat("yyyy-MM-dd")
            val monthayyear:String=mdy.format(Date()).toString()
            val currentDateTime: String = dateFormat.format(Date()).toString() // Find todays date

            val message=message_text.text.toString()
            val send=Text(message, userId, 1,currentDateTime,monthayyear,currentDiocese,0,groupId)

            db.collection("rooms").document(groupId)
                    .collection("messages")
                    .add(send)
                    .addOnSuccessListener {
                     toast("Message sent successfully")
                        message_text.setText("")
                    }
                    .addOnFailureListener {

        }

    }
}
    private fun getDate(time_stamp_server: Long): String {
        val formatter = SimpleDateFormat("mm-dd-yyyy")
        return formatter.format(time_stamp_server)
    }
}