package com.example.kcmav1

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.model.DList
import com.example.kcmav1.model.Type
import com.example.kcmav1.model.UploadSongData
import com.example.kcmav1.recyclers.ViewSongsOfTypeAdapter
import com.example.kcmav1.utils.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore

class SearchResultsActivity : AppCompatActivity(), ViewSongsOfTypeAdapter.OnItemClickListener {
    lateinit var search_results:RecyclerView
    val songitem=ArrayList<DList>()
    lateinit var tv_query:TextView
    lateinit var adapter:ViewSongsOfTypeAdapter
    lateinit var pd:ProgressDialog
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        search_results=findViewById(R.id.search_results)
        pd=ProgressDialog(this)
        tv_query=findViewById(R.id.tv_query)
        toolbar=findViewById(R.id.result_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)



            search(query)

        }
      search_results.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter= ViewSongsOfTypeAdapter(songitem,this)
    }


    private fun search(text: String?) {
        pd.show()
        pd.setMessage("Searching")
        val db=FirebaseFirestore.getInstance()
        db.collectionGroup("songs")
                .get()
                .addOnCompleteListener(
                        OnCompleteListener {task->
                            pd.dismiss()
                            for (document in task.result!!) {
                                if(document.exists())
                                {
                                    var song_title = arrayListOf<String>(document.get("title").toString())
                                    val match = song_title.filter { it in text!!}
                                    match.forEach { d ->
                                        var song_composer = document.get("composer").toString()
                                        var song_views = document.get("views").toString()
                                        var dnumber = document.get("dnumber").toString()
                                         var stype=document.get("type").toString()
                                        songitem.add(DList(d, "Viewed" + " "+song_views +" "+"times", "Downloaded"+"  " + dnumber +"  "+"times",song_composer ,stype))
                                        search_results.adapter = adapter
                                    }

                                }
                                else
                                {
                                    tv_query.setText("Result not available")
                                }
                            }
                        }
                )
}

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClickme(position: Int) {
        val user= songitem.get(position)
        val _title:String=user.title
        var _type:String=user.type
        val intent= Intent(this, SongDetailActivity::class.java)
        intent.putExtra("title",_title)
        intent.putExtra("type",_type)
        startActivity(intent)
    }
}