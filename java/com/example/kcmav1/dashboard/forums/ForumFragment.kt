package com.example.kcmav1.dashboard.forums

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.ChatPageActivity
import com.example.kcmav1.R
import com.example.kcmav1.model.Room
import com.example.kcmav1.model.Text
import com.example.kcmav1.model.Type
import com.example.kcmav1.model.UploadSongData
import com.example.kcmav1.recyclers.ForumListAdapter
import com.example.kcmav1.room.Forum
import com.example.kcmav1.room.ForumViewModel
import com.example.kcmav1.utils.AppPreferences
import com.example.kcmav1.utils.toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ForumFragment : AppCompatActivity(),ForumListAdapter.OnItemClickListener {
    lateinit var forum_name:String
    lateinit var forumViewModel: ForumViewModel
    lateinit var db:FirebaseFirestore
    lateinit var toolbar: Toolbar
    val room=ArrayList<Room>()
    lateinit var currentMess:String
    lateinit var current_user:String
    lateinit var current_sender:String
    lateinit var adapter:ForumListAdapter
    lateinit var recyclerView:RecyclerView
    lateinit var se:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forums)
         adapter = ForumListAdapter(room, this)
        db = FirebaseFirestore.getInstance()
        val progress = ProgressDialog(this)
        recyclerView = findViewById<RecyclerView>(R.id.forum_list_recycler)
        toolbar = findViewById(R.id.forum_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        AppPreferences.init(this)
        if (AppPreferences.isLogin) {
            current_user = AppPreferences.email
            se=AppPreferences.username
        }
        forumViewModel = ViewModelProvider(this).get(ForumViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progress.show()
        progress.setTitle("Please wait!")
        progress.setMessage("fetching documents")

        db.collection("users").document(current_user).collection("myforums") .orderBy("name")
                .addSnapshotListener {value, e ->
                    if (e!=null){
                        return@addSnapshotListener
                    }
                    progress.dismiss()
                    for (document in value!!) {
                        if (document.exists()) {
                            forum_name = document.get("name").toString()
                            room.add(Room(forum_name,"currentMess", "adfdf"))
                            recyclerView.adapter = adapter

                        }
                        else
                        {

                        }

                    }
                }

                }



    private fun fetch(forum: String) {
        db.collection("rooms").document(forum).collection("messages").orderBy("sentAt", Query.Direction.DESCENDING)
                .limit(1).get().addOnSuccessListener { task->
                    if (task.isEmpty){
                        room.add(Room(forum_name,"currentMess", "adfdf"))
                    recyclerView.adapter = adapter
                    }
                    else
                    {
                        for (doc in task)
                        {
                            room.clear()
                            currentMess=doc.get("text").toString()
                            current_sender=doc.get("sender").toString()
                            displayData(forum,current_sender,currentMess)

                        }

                    }
                }



    }

    private fun displayData(fo:String,currentSen: String, currentM: String) {

            room.add(Room(fo,currentMess, current_sender))
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position:Int)
    {
        val user= room[position]
        val _title:String=user.name
        val intent= Intent(this, ChatPageActivity::class.java)
        intent.putExtra("room",_title)
        startActivity(intent)
    }

    ///handling back button in android
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
