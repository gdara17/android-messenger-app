package ge.gdara17.messengerapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gdara17.messengerapp.databinding.ActivityChatBinding
import ge.gdara17.messengerapp.dataclasses.Chat

class ChatActivity : AppCompatActivity(), ChatContract.View {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessagesAdapter
    private lateinit var chat: Chat
    private val presenter: ChatContract.Presenter = ChatPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chat = intent.getParcelableExtra(CHAT_EXTRA)!!
        initViews()
        initRecyclerView()
        addListeners()
    }

    private fun addListeners() {
        binding.chatToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        binding.tvChatPerson.text = chat.with?.name
        binding.tvChatOccupation.text = chat.with?.occupation
        // TODO: avatar
    }

    private fun initRecyclerView() {
        binding.rvChatMessages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MessagesAdapter()
        adapter.submitList(chat.messages ?: mutableListOf())
        binding.rvChatMessages.adapter = adapter
    }

    companion object {
        const val CHAT_EXTRA = "ge.gdara17.messengerapp.CHAT_EXTRA"
    }

    override fun showChat(chat: Chat) {
        this.chat = chat
        adapter.submitList(chat.messages ?: mutableListOf())
    }
}