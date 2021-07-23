package ge.gdara17.messengerapp.main.recentchats

import ge.gdara17.messengerapp.dataclasses.Chat

interface RecentChatsContract {
    interface View {
        fun showRecentChats(recentChats: MutableList<Chat>)
    }

    interface Model {
        fun fetchRecentChats(searchString: String? = null)
    }

    interface Presenter {
        fun getRecentChats(searchString: String? = null)
        fun onRecentChatsFetched(recentChats: MutableList<Chat>)
    }
}