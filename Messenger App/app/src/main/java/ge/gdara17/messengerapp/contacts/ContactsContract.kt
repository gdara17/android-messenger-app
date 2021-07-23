package ge.gdara17.messengerapp.contacts

import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

interface ContactsContract {
    interface View {
        fun showContacts(contacts: MutableList<User>)
        fun onChatAdded(chat: Chat)
    }

    interface Model {
        fun addChat(chat: Chat)
        fun fetchContacts(searchString: String? = null)
    }

    interface Presenter {
        fun addChat(chat: Chat)
        fun getContacts(searchString: String? = null)
        fun onChatAdded(chat: Chat)
        fun onContactsFetched(contacts: MutableList<User>)
    }
}