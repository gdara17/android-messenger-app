package ge.gdara17.messengerapp.main.recentchats

import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.Message
import ge.gdara17.messengerapp.dataclasses.User
import java.util.*

class RecentChatsModel(private val presenter: RecentChatsContract.Presenter) :
    RecentChatsContract.Model {
    override fun fetchRecentChats() {
        presenter.onRecentChatsFetched(getDummyRecentChats())
    }

    private fun getDummyRecentChats(): MutableList<Chat> {
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
}
