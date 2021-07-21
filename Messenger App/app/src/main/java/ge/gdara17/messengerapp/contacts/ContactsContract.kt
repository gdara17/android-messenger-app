package ge.gdara17.messengerapp.contacts

import ge.gdara17.messengerapp.dataclasses.User

interface ContactsContract {
    interface View {
        fun showContacts(contacts: MutableList<User>)
    }

    interface Model {
        fun fetchContacts()
    }

    interface Presenter {
        fun getContacts()
        fun onContactsFetched(contacts: MutableList<User>)
    }
}