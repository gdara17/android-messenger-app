package ge.gdara17.messengerapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gdara17.messengerapp.databinding.ActivityChatBinding
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.Message

class ChatActivity : AppCompatActivity(), ChatContract.View {
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessagesAdapter
    private lateinit var chat: Chat
    private lateinit var presenter: ChatContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chat = intent.getParcelableExtra(CHAT_EXTRA)!!
        presenter = ChatPresenter(this, chat.uid!!)
        initViews()
        initRecyclerView()
        addListeners()
    }

    private fun addListeners() {
        binding.chatToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnChatSend.setOnClickListener {
            val messageText = binding.etChatMessage.text.toString()
            binding.etChatMessage.setText("")
            if (messageText.isEmpty()) {
                return@setOnClickListener
            }
            val message = Message(
                chatUid = chat.uid!!,
                text = messageText
            )
            presenter.sendMessage(message)
        }
    }

    private fun initViews() {
        binding.tvChatPerson.text = chat.with?.username
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

    override fun showMessages(message: MutableList<Message>) {
        adapter.submitList(message)
    }
}