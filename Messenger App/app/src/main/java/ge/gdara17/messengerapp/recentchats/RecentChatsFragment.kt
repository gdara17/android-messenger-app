package ge.gdara17.messengerapp.recentchats

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ge.gdara17.messengerapp.chat.ChatActivity
import ge.gdara17.messengerapp.databinding.FragmentRecentChatsBinding
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.Message
import ge.gdara17.messengerapp.dataclasses.User
import java.util.*


class RecentChatsFragment : Fragment(), RecentChatClickListener {

    private var _binding: FragmentRecentChatsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: RecentChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecentChatsBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecyclerView()

        return view
    }

    private fun getInitRecentChats(): MutableList<Chat> {
        val user0 = User("User0", "Occupation0")
        val user1 = User("User1", "Occupation1")
        val user2 = User("User2", "Occupation2")
        val user3 = User("User3", "Occupation3")
        val user4 = User("User4", "Occupation4")
        val user5 = User("User5", "Occupation5")

        val messages1 = mutableListOf(
            Message(Calendar.getInstance().timeInMillis, "Hi there!"),
            Message(Calendar.getInstance().timeInMillis, "How are you?", received = false),
        )

        val messages2 = mutableListOf(
            Message(Calendar.getInstance().timeInMillis, "Whats up?"),
            Message(Calendar.getInstance().timeInMillis, "Hey?!", received = false),
            Message(Calendar.getInstance().timeInMillis, "...", received = false),
            Message(Calendar.getInstance().timeInMillis, "No?"),
        )
        return mutableListOf(
            Chat(user1, messages1),
            Chat(user2, messages2),
            Chat(user3, messages1),
            Chat(user4, messages2),
            Chat(user5, messages1),
        )
    }

    private fun initRecyclerView() {
        binding.rvRecentChats.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RecentChatsAdapter(this)
        adapter.submitList(getInitRecentChats())
        binding.rvRecentChats.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecentChatClick(position: Int) {
        val i = Intent(context, ChatActivity::class.java)
        i.putExtra(ChatActivity.CHAT_KEY, adapter.getAt(position))
        startActivity(i)
    }
}