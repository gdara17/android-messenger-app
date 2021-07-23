package ge.gdara17.messengerapp.main.recentchats

import ge.gdara17.messengerapp.dataclasses.Chat

interface RecentChatsContract {
    interface View {
        fun showRecentChats(recentChats: MutableList<Chat>)
    }

    interface Model {

    }

    interface Presenter {
        fun onRecentChatsFetched(recentChats: MutableList<Chat>)
    }
}