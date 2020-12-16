package com.oakland.ekit

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oakland.ekit.Constants.Companion.MessageModel
import java.text.DateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CommentsListAdapter // you can pass other parameters in constructor
(private val context: Context, var list: ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class MessageInViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageTV: TextView
        var dateTV: TextView
        fun bind(position: Int) {
            val messageModel = list[position]
            messageTV.text = messageModel.message

            //format the date time and put it on the message view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateTV.text = DateTimeFormatter.ofPattern("hh:mm a").format(messageModel.messageTime) //DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.messageTime)
            }
        }

        init {
            messageTV = itemView.findViewById(R.id.message_text)
            dateTV = itemView.findViewById(R.id.date_text)
        }
    }

    private inner class MessageOutViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageTV: TextView
        var dateTV: TextView
        fun bind(position: Int) {
            val messageModel = list[position]
            messageTV.text = messageModel.message
            //format the date time and put it on the message view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateTV.text = DateTimeFormatter.ofPattern("hh:mm a").format(messageModel.messageTime) //DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.messageTime)
            }
        }

        init {
            messageTV = itemView.findViewById(R.id.message_text)
            dateTV = itemView.findViewById(R.id.date_text)
        }
    }


    private inner class MessageDateViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateTV: TextView
        fun bind(position: Int) {
            val messageModel = list[position]

            //format the date time and put it on the message view
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateTV.text = DateTimeFormatter.ofPattern("E, MMM dd, hh:mm a").format(messageModel.messageTime) //DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.messageTime)
            }
        }

        init {
            dateTV = itemView.findViewById(R.id.date_text)
        }
    }

    //create the holder for the view based on the message type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_TYPE_IN) {
            MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_in, parent, false))
        }else if (viewType == MESSAGE_DATE){
            MessageDateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_date, parent, false))
        } else MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_out, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].messageType == MESSAGE_TYPE_IN) {
            (holder as MessageInViewHolder).bind(position)
        }else if(list[position].messageType == MESSAGE_DATE){
            (holder as MessageDateViewHolder).bind(position)
        }else {
            (holder as MessageOutViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].messageType
    }

    companion object {
        const val MESSAGE_TYPE_IN = 1
        const val MESSAGE_TYPE_OUT = 2
        const val MESSAGE_DATE = 3
    }

}