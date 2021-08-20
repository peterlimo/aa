package com.example.kcmav1.dashboard.test

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.R
import com.example.kcmav1.ResponsesActivity
import com.example.kcmav1.model.Liker
import com.example.kcmav1.model.Message
import com.example.kcmav1.utils.AppPreferences
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class Adapter(context: Context, list: ArrayList<Message>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

companion object {
    const val VIEW_TYPE_ONE = 1
    const val VIEW_TYPE_TWO = 2
     var isliked:Boolean=true
    lateinit var db:FirebaseFirestore

}

private val context: Context = context
var list: ArrayList<Message> = list

 inner class View1ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
     lateinit var love_btn:ImageView
    var message: TextView = itemView.findViewById(R.id.sent_message_textview)
    var time: TextView = itemView.findViewById(R.id.time)
    val senderr:TextView=itemView.findViewById(R.id.current_diocese)
    val email:TextView=itemView.findViewById(R.id.my_email)
     val response_view:LinearLayout=itemView.findViewById(R.id.response_view)
    var isss:Boolean=true

     val chat_btn:ImageView=itemView.findViewById(R.id.open_chats)
    val no_of_loves:TextView=itemView.findViewById(R.id.no_of_loves)

    fun bind(position: Int) {
        love_btn=itemView.findViewById(R.id.love_btn)
        val recyclerViewModel = list[position]
        message.text = recyclerViewModel.text
        time.text=recyclerViewModel.time
        isss=recyclerViewModel.isLike
        no_of_loves.text=recyclerViewModel.loves
        val mid=recyclerViewModel.mesid
        val ggid=recyclerViewModel.gid
        val currentM=recyclerViewModel.text
        email.setText("@/"+recyclerViewModel.diocese)
        senderr.setText("ME")
        chat_btn.setOnClickListener {
            OpenResponses(ggid,mid,it,currentM)
        }
        love_btn.setOnClickListener {
            Like(it.context,adapterPosition)
        }
    }

     private fun OpenResponses(ggid: String, mid: String, it: View, currentM: String) {
         val i=Intent(it.context,ResponsesActivity::class.java)
         i.putExtra("cms",currentM)
         i.putExtra("gid",ggid)
         i.putExtra("mid",mid)
         context.startActivity(i)
     }


     override fun onClick(v: View?) {

    }

     private fun Like(contex: Context?, position: Int) {

         if (contex != null) {
             AppPreferences.init(contex.applicationContext)
         }
         val email=AppPreferences.email
         val c=list[position]
         val id=c.mesid
         val fid=c.gid
         db= FirebaseFirestore.getInstance()
         db.collection("rooms")
                 .document(fid)
                 .collection("messages")
                 .document(id)
                 .collection("likes")
                 .document(email)
                 .get()
                 .addOnSuccessListener {
                     if (it.exists()){
                         db.collection("rooms")
                                 .document(fid)
                                 .collection("messages")
                                 .document(id)
                                 .update("loves",FieldValue.increment(-1))
                         db.collection("rooms")
                                 .document(fid)
                                 .collection("messages")
                                 .document(id)
                                 .collection("likes")
                                 .document(email).delete()

                     }
                     else{
                         updateLike(itemView,context,id,fid)
                     }
                 }
     }


 }



    private fun updateLike(context1: View, context: Context?, id: String, gid: String) {
      var  love_btn:ImageView=context1.findViewById(R.id.love_btn)
        db= FirebaseFirestore.getInstance()
        if (context != null) {
            AppPreferences.init(context1.context)
        }
        val email=AppPreferences.email
val liker= Liker(email)
        db.collection("rooms")
                .document(gid)
                .collection("messages")
                .document(id)
                .update("loves",FieldValue.increment(1))
                .addOnSuccessListener {
                    db.collection("rooms")
                            .document(gid)
                            .collection("messages")
                            .document(id)
                            .collection("likes")
                            .document(email)
                            .set(liker)
                }
    }

    private inner class View2ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        lateinit var love_btn:ImageView
    var message: TextView = itemView.findViewById(R.id.received_text)
    var time: TextView = itemView.findViewById(R.id.time_received)
    var sender: TextView = itemView.findViewById(R.id.received_from)
        var pdiocese:TextView=itemView.findViewById(R.id.my_email)
    var mdy:TextView=itemView.findViewById(R.id.date)
        val no_of_loves:TextView=itemView.findViewById(R.id.received_loves)
    fun bind(position: Int) {
        love_btn=itemView.findViewById(R.id.received_love)
        val recyclerViewModel = list[position]
        message.text = recyclerViewModel.text
        time.text=recyclerViewModel.time
        sender.text=recyclerViewModel.sender
       pdiocese.setText("@/"+recyclerViewModel.diocese)
        mdy.text=recyclerViewModel.mdy
        no_of_loves.text=recyclerViewModel.loves

        var iss:Boolean=recyclerViewModel.isLike
        love_btn.setOnClickListener {


            Like(it.context,adapterPosition)
        }
    }
        private fun Like(contex: Context?, position: Int) {

            if (contex != null) {
                AppPreferences.init(contex.applicationContext)
            }
            val email=AppPreferences.email
            val c=list[position]
            val id=c.mesid
            val fid=c.gid
            db= FirebaseFirestore.getInstance()
            db.collection("rooms")
                    .document(fid)
                    .collection("messages")
                    .document(id)
                    .collection("likes")
                    .document(email)
                    .get()
                    .addOnSuccessListener {
                        if (it.exists()){
                            db.collection("rooms")
                                    .document(fid)
                                    .collection("messages")
                                    .document(id)
                                    .update("loves",FieldValue.increment(-1))
                            db.collection("rooms")
                                    .document(fid)
                                    .collection("messages")
                                    .document(id)
                                    .collection("likes")
                                    .document(email).delete()
                        }
                        else{

                            updateLike(itemView,context,id,fid)
                        }
                    }
        }
        private fun updateLike(context1: View, context: Context?, id: String, gid: String) {
            db= FirebaseFirestore.getInstance()
            if (context != null) {
                AppPreferences.init(context1.context)
            }
            val email=AppPreferences.email
            val liker= Liker(email)
            db.collection("rooms")
                    .document(gid)
                    .collection("messages")
                    .document(id)
                    .update("loves",FieldValue.increment(1))
                    .addOnSuccessListener {
                        db.collection("rooms")
                                .document(gid)
                                .collection("messages")
                                .document(id)
                                .collection("likes")
                                .document(email)
                                .set(liker)
                    }
        }

    }




override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    if (viewType == VIEW_TYPE_ONE) {
        return View1ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.send_message_item, parent, false)
        )
    }
    else{
    return View2ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.received_message_item, parent, false)
    )}
}

override fun getItemCount(): Int {
    return list.size
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (list[position].viewType === VIEW_TYPE_ONE) {
        (holder as View1ViewHolder).bind(position)
    } else {
        (holder as View2ViewHolder).bind(position)
    }

}

override fun getItemViewType(position: Int): Int {
    return list[position].viewType
}


}
