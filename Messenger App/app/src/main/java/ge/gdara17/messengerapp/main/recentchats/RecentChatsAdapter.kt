package ge.gdara17.messengerapp.main.recentchats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gdara17.messengerapp.databinding.RecentChatCellBinding
import ge.gdara17.messengerapp.dataclasses.Chat

class RecentChatsAdapter(private val listener: RecentChatClickListener) :
    RecyclerView.Adapter<RecentChatsAdapter.RecentChatCellViewHolder>() {
    private var recentChats: MutableList<Chat> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentChatCellViewHolder {
        val binding =
            RecentChatCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentChatCellViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: RecentChatCellViewHolder, position: Int) {
        holder.configureWith(recentChats[position])
    }

    override fun getItemCount(): Int {
        return recentChats.size
    }

    fun submitList(recentChats: MutableList<Chat>) {
        this.recentChats = recentChats
        notifyDataSetChanged()
    }

    fun getAt(position: Int): Chat {
        return recentChats[position]
    }

    class RecentChatCellViewHolder(
        private val binding: RecentChatCellBinding,
        private val listener: RecentChatClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun configureWith(chat: Chat) {

            // TODO: avatar
            binding.tvRecentChatPerson.text = chat.with?.username
            binding.tvRecentChatMessage.text = chat.lastMessage?.text
            binding.tvRecentChatTime.text = chat.lastMessage?.getFormattedTime()

            addClickListeners()
        }

        private fun addClickListeners() {
            binding.root.setOnClickListener {
                listener.onRecentChatClick(adapterPosition)
            }
        }
    }

}

