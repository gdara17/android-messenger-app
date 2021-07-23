package ge.gdara17.messengerapp.contacts

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.Constants
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

class ContactsModel(private val presenter: ContactsContract.Presenter) : ContactsContract.Model {
    private val auth = Firebase.auth
    private val database = Firebase.database
    private val usersRef = database.getReference("users")
    private val chatsRef = database.getReference("chats")

    override fun addChat(chat: Chat) {
        if (chat.with == null) {
            return
        }

        val chatKey = chatsRef.push().key
        if (chatKey == null) {
            Log.d(Constants.DEBUG_TAG, "could not add chat")
            return
        }

        val childUpdates = hashMapOf<String, Any>(
            "${chat.with!!.uid!!}/chats/${auth.currentUser!!.uid}" to chatKey,
            "${auth.currentUser!!.uid}/chats/${chat.with!!.uid!!}" to chatKey
        )

        usersRef.updateChildren(childUpdates).addOnSuccessListener {
            chat.uid = chatKey
            presenter.onChatAdded(chat)
        }
    }

    override fun fetchContacts(searchString: String?) {
        usersRef.get().addOnSuccessListener { dataSnapshot ->
            Log.d(Constants.DEBUG_TAG, "Users fetched successfully")
            var users = dataSnapshot.getValue<MutableMap<String, User>>()
            users = users?.filter { (_, user) ->
                if (searchString.isNullOrBlank()) {
                    return@filter true
                } else {
                    val username = user.username
                    if (username.isNullOrBlank()) {
                        return@filter false
                    } else {
                        return@filter username.contains(searchString)
                    }
                }
            } as MutableMap<String, User>?

            users?.apply {
                remove(auth.currentUser?.uid)
                forEach { (key, user) ->
                    user.uid = key
                }
                values.toMutableList().let {
                    presenter.onContactsFetched(it)
                }
            }
        }
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
