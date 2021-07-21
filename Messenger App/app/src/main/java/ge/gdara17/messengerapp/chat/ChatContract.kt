package ge.gdara17.messengerapp.chat

import ge.gdara17.messengerapp.dataclasses.Chat

interface ChatContract {
    interface View {
        fun showChat(chat: Chat)
    }

    interface Model {
        fun fetchChat()
    }

    interface Presenter {
        fun getChat()
        fun onChatFetched(chat: Chat)
    }
}