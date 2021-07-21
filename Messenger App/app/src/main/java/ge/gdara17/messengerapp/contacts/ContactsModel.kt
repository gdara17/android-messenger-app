package ge.gdara17.messengerapp.contacts

import ge.gdara17.messengerapp.dataclasses.User

class ContactsModel(private val presenter: ContactsContract.Presenter) : ContactsContract.Model {
    override fun fetchContacts() {
        presenter.onContactsFetched(getDummyContacts())
    }

    private fun getDummyContacts(): MutableList<User> {
        val user1 = User("User1", "Occupation1")
        val user2 = User("User2", "Occupation2")
        val user3 = User("User3", "Occupation3")
        val user4 = User("User4", "Occupation4")
        val user5 = User("User5", "Occupation5")

        return mutableListOf(
            user1,
            user2,
            user3,
            user4,
            user5
        )
    }
}
