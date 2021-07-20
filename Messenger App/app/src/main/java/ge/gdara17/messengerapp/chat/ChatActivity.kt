package ge.gdara17.messengerapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gdara17.messengerapp.databinding.ActivityChatBinding
import ge.gdara17.messengerapp.dataclasses.Chat

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessagesAdapter
    private lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chat = intent.getParcelableExtra(CHAT_KEY)!!
        initViews()
    }

    private fun initViews() {
        binding.tvChatPerson.text = chat.with?.name
        binding.tvChatOccupation.text = chat.with?.occupation
        // TODO: avatar

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvChatMessages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MessagesAdapter(chat.messages!!)
        binding.rvChatMessages.adapter = adapter
    }

    companion object {
        const val CHAT_KEY = "ge.gdara17.messengerapp.CHAT_KEY"
    }
}