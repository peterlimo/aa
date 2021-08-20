package com.example.kcmav1.dashboard.search

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.R
import com.example.kcmav1.SearchResultsActivity
import com.example.kcmav1.SongListActivity
import com.example.kcmav1.model.DList
import com.example.kcmav1.model.Type
import com.example.kcmav1.model.UploadSongData
import com.example.kcmav1.recyclers.TypeListAdapter
import com.example.kcmav1.recyclers.ViewSongsOfTypeAdapter
import com.example.kcmav1.utils.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


class HomeFragment : AppCompatActivity(),TypeListAdapter.OnItemClickListener{
    lateinit var db:FirebaseFirestore
    lateinit var toolbar:Toolbar
    val type=ArrayList<Type>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:TypeListAdapter
    lateinit var pd:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

       setContentView(R.layout.fragment_song_types)
        pd= ProgressDialog(this)
        toolbar=findViewById(R.id.toolbar)
        recyclerView=findViewById(R.id.song_type_recycler)
        adapter= TypeListAdapter(type,this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db= FirebaseFirestore.getInstance()
getSongTypes()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(position:Int){
        val item= type.get(position)
        val _title:String=item.title
        val intent= Intent(this, SongListActivity::class.java)
        intent.putExtra("type",_title)
        startActivity(intent)
    }


    fun getSongTypes()
    {
        db.collection("files").get().addOnSuccessListener {
            result->

            for (document in result){
                if (document.exists()) {
                    val title=document.id
                    type.add(Type(title))
                    recyclerView.adapter=adapter
                }
            }

        }.addOnFailureListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                onSearchRequested()

                true
            }
            else -> false
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView=MenuItemCompat.getActionView(menu.findItem(R.id.search)) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this, SearchResultsActivity::class.java)))
        searchView.isIconifiedByDefault=true

        return true
    }


}