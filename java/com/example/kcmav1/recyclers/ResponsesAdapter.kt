package com.example.kcmav1.recyclers

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kcmav1.R
import com.example.kcmav1.model.DList
import com.example.kcmav1.model.Response
import com.example.kcmav1.utils.inflate

class ResponsesAdapter(private val type:List<Response>): RecyclerView.Adapter<ResponsesAdapter.SongsViewHolder>() {

    inner class SongsViewHolder ( itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{


        fun bind(songData: Response){
            val message=itemView.findViewById<TextView>(R.id.responder_name)
            val sender=itemView.findViewById<TextView>(R.id.responder_message)
            val time=itemView.findViewById<TextView>(R.id.responder_time)
            message.text=songData.message
            sender.text=songData.sender
            time.text=songData.date
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
//           if (position!=RecyclerView.NO_POSITION){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val inflatedView = parent.inflate(R.layout.response_item, false)
        return SongsViewHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return type.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val song= type[position]

        holder.bind(song)
    }


    }


