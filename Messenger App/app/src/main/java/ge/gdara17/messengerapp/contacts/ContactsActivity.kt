package ge.gdara17.messengerapp.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gdara17.messengerapp.chat.ChatActivity
import ge.gdara17.messengerapp.databinding.ActivityContactsBinding
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

class ContactsActivity : AppCompatActivity(), ContactClickListener, ContactsContract.View {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactsAdapter
    private val presenter: ContactsContract.Presenter = ContactsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        addListeners()
        presenter.getContacts()
    }

    private fun addListeners() {
        binding.btnContactsBack.setOnClickListener {
            finish()
        }
        binding.etContactsSearch.addTextChangedListener {
            presenter.getContacts(it.toString())
        }
    }

    private fun initRecyclerView() {
        binding.rvContacts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ContactsAdapter(this)
        adapter.submitList(mutableListOf())
        binding.rvContacts.adapter = adapter
    }

    override fun onContactClick(position: Int) {
        val user = adapter.getAt(position)
        val chat = Chat(
            with = user,
            messages = mutableListOf()
        )
        presenter.addChat(chat)
    }

    override fun showContacts(contacts: MutableList<User>) {
        adapter.submitList(contacts)
    }

    override fun onChatAdded(chat: Chat) {
        val i = Intent(this, ChatActivity::class.java)
        i.putExtra(ChatActivity.CHAT_EXTRA, chat)
        startActivity(i)
    }
}