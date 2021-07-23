package ge.gdara17.messengerapp.contacts

import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

class ContactsPresenter(private val view: ContactsContract.View) :
    ContactsContract.Presenter {
    private val model: ContactsContract.Model = ContactsModel(this)

    override fun addChat(chat: Chat) {
        model.addChat(chat)
    }

    override fun getContacts(searchString: String?) {
        model.fetchContacts(searchString)
    }

    override fun onChatAdded(chat: Chat) {
        view.onChatAdded(chat)
    }

    override fun onContactsFetched(contacts: MutableList<User>) {
        view.showContacts(contacts)
    }
}