package com.example.kcmav1

import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kcmav1.utils.toast
import com.google.firebase.firestore.FirebaseFirestore


class SongDetailActivity : AppCompatActivity() {
    lateinit var db:FirebaseFirestore
    lateinit var song_name:String
    lateinit var song_type:String
    lateinit var detail_title:TextView
    lateinit var detail_song_composer:TextView
    lateinit var detail_sung_by_which_choir:TextView
    lateinit var detail_song_type:TextView
    lateinit var detail_uploaded_by:TextView
    lateinit var detail_views:TextView
    lateinit var detail_downloads:TextView
    lateinit var dBtn:Button
    lateinit var sUrl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        detail_title=findViewById(R.id.detail_title)
        detail_song_composer=findViewById(R.id.detail_song_composer)
        detail_sung_by_which_choir=findViewById(R.id.detail_sung_by_which_choir)
        detail_uploaded_by=findViewById(R.id.detail_uploaded_by)
        detail_downloads=findViewById(R.id.detail_number_of_downloads)
        detail_views=findViewById(R.id.detail_views)
        detail_song_type=findViewById(R.id.detail_song_type)
        dBtn=findViewById(R.id.download_btn)
        val progress= ProgressDialog(this)
        song_name=intent.getStringExtra("title").toString()
        song_type=intent.getStringExtra("type").toString()
        db= FirebaseFirestore.getInstance()
        progress.show()

            progress.setTitle("Please wait!")
            progress.setMessage("Getting document")
            db.collectionGroup("songs")
                    .whereEqualTo("title", song_name).get().addOnSuccessListener { results ->
                progress.dismiss()
                        for (result in results){
                if (result.exists()) {

                    var name = result.get("title").toString()
                    var choir = result.get("choir").toString()
                    val uploader = result.get("uploader").toString()
                    val composer = result.get("composer").toString()
                    sUrl=result.get("url").toString()
                    detail_title.text = name
                    detail_song_composer.text = composer
                    detail_uploaded_by.text = uploader
                    detail_sung_by_which_choir.text = choir
                    detail_downloads.text = result.get("ndownload").toString()
                    detail_views.setText(result.get("ndownload").toString())
                    dBtn.visibility=View.VISIBLE
                }}

            }.addOnFailureListener {
toast("Failed to get document")
            }

dBtn.setOnClickListener {
    downloadFile(this,song_name,"pdf","",sUrl)
}
    }
    fun downloadFile(context: Context, fileName: String, fileExtension: String, destinationDirectory: String?, url: String?): Long {
        val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri: Uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)
        return downloadmanager.enqueue(request)
    }
    override fun onBackPressed() {

        super.onBackPressed()
        this.finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}