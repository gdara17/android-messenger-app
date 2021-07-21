package ge.gdara17.messengerapp.main.recentchats

import ge.gdara17.messengerapp.dataclasses.Chat

class RecentChatsPresenter(private val view: RecentChatsContract.View) :
    RecentChatsContract.Presenter {
    private val model = RecentChatsModel(this)

    override fun getRecentChats() {
        model.fetchRecentChats()
    }

    override fun onRecentChatsFetched(recentChats: MutableList<Chat>) {
        view.showRecentChats(recentChats)
    }
}