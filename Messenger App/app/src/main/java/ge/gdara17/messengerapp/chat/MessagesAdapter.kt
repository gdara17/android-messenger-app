package ge.gdara17.messengerapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.gdara17.messengerapp.R
import ge.gdara17.messengerapp.dataclasses.Message

class MessagesAdapter(private val messages: MutableList<Message>) : RecyclerView.Adapter<MessagesAdapter.MessageCellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageCellViewHolder {
        return if (viewType == SENT_MESSAGE) {
            val cell = LayoutInflater.from(parent.context)
                .inflate(R.layout.sent_message_cell, parent, false)
            SentMessageCellViewHolder(cell)
        } else {
            val cell = LayoutInflater.from(parent.context)
                .inflate(R.layout.received_message_cell, parent, false)
            ReceivedMessageCellViewHolder(cell)
        }
    }

    override fun onBindViewHolder(holder: MessageCellViewHolder, position: Int) {
        holder.configure(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].received!!) RECEIVED_MESSAGE else SENT_MESSAGE
    }

    abstract class MessageCellViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun configure(message: Message)
    }

    class SentMessageCellViewHolder(private val itemView: View) : MessageCellViewHolder(itemView) {
        override fun configure(message: Message) {
            val tvMessageText = itemView.findViewById<TextView>(R.id.tvSentMessageCellMessage)
            val tvMessageTime = itemView.findViewById<TextView>(R.id.tvSentMessageCellTime)

            tvMessageText.text = message.text
            tvMessageTime.text = message.getFormattedTime()
        }
    }

    class ReceivedMessageCellViewHolder(private val itemView: View) :
        MessageCellViewHolder(itemView) {
        override fun configure(message: Message) {
            val tvMessageText = itemView.findViewById<TextView>(R.id.tvReceivedMessageCellMessage)
            val tvMessageTime = itemView.findViewById<TextView>(R.id.tvReceivedMessageCellTime)

            tvMessageText.text = message.text
            tvMessageTime.text = message.getFormattedTime()
        }
    }

    companion object {
        const val SENT_MESSAGE = 0
        const val RECEIVED_MESSAGE = 1
    }

}
