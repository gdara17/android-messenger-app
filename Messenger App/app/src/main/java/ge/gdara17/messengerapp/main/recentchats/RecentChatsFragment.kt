package ge.gdara17.messengerapp.main.recentchats

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


class RecentChatsFragment : Fragment(), RecentChatClickListener, RecentChatsContract.View {

    private var _binding: FragmentRecentChatsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val presenter: RecentChatsContract.Presenter = RecentChatsPresenter(this)
    private lateinit var adapter: RecentChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentChatsBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerView()
        presenter.getRecentChats()

        return view
    }

    private fun initRecyclerView() {
        binding.rvRecentChats.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RecentChatsAdapter(this)
        adapter.submitList(mutableListOf())
        binding.rvRecentChats.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecentChatClick(position: Int) {
        val i = Intent(context, ChatActivity::class.java)
        i.putExtra(ChatActivity.CHAT_EXTRA, adapter.getAt(position))
        startActivity(i)
    }

    override fun showRecentChats(recentChats: MutableList<Chat>) {
        adapter.submitList(recentChats)
    }
}