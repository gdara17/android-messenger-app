package ge.gdara17.messengerapp.chat

import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.Message

class ChatPresenter(private val view: ChatContract.View, chatUid: String) :
    ChatContract.Presenter {
    private val model = ChatModel(this, chatUid)

    override fun getChat() {
        model.fetchChat()
    }

    override fun onChatFetched(chat: Chat) {
        view.showChat(chat)
    }

    override fun sendMessage(message: Message) {
        model.sendMessage(message)
    }

    override fun onChatUpdated(messages: MutableList<Message>) {
        view.showMessages(messages)
    }
}