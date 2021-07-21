package ge.gdara17.messengerapp.chat

import ge.gdara17.messengerapp.dataclasses.Chat

class ChatPresenter (private val view: ChatContract.View) :
    ChatContract.Presenter {
    private val model = ChatModel(this)

    override fun getChat() {
        model.fetchChat()
    }

    override fun onChatFetched(chat: Chat) {
        view.showChat(chat)
    }
}