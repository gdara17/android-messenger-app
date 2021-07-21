package ge.gdara17.messengerapp.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun initRecyclerView() {
        binding.rvContacts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ContactsAdapter(this)
        adapter.submitList(mutableListOf())
        binding.rvContacts.adapter = adapter
    }

    override fun onContactClick(position: Int) {
        val i = Intent(this, ChatActivity::class.java)
        val user = adapter.getAt(position)
        val chat = Chat(user, messages = mutableListOf())
        i.putExtra(ChatActivity.CHAT_EXTRA, chat)
        startActivity(i)
    }

    override fun showContacts(contacts: MutableList<User>) {
        adapter.submitList(contacts)
    }
}