package ge.gdara17.messengerapp.chat

import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.Message

interface ChatContract {
    interface View {
        fun showChat(chat: Chat)
        fun showMessages(messages: MutableList<Message>)
    }

    interface Model {
        fun fetchChat()
        fun sendMessage(message: Message)
    }

    interface Presenter {
        fun getChat()
        fun onChatFetched(chat: Chat)

        fun sendMessage(message: Message)
        fun onChatUpdated(messages: MutableList<Message>)
    }
}