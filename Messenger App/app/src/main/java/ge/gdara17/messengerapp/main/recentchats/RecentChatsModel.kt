package ge.gdara17.messengerapp.main.recentchats

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.Constants
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

class RecentChatsModel(private val presenter: RecentChatsContract.Presenter) :
    RecentChatsContract.Model {
    private var auth: FirebaseAuth = Firebase.auth
    private val database = Firebase.database
    private val usersRef = database.getReference("users")
    private val chatsRef = database.getReference("chats")

    init {
        addChatsValueListener()
    }

    private fun addChatsValueListener() {
        chatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                fetchRecentChats()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun fetchRecentChats(searchString: String?) {
        usersRef.child(auth.currentUser?.uid!!).get().addOnSuccessListener { userSnapshot ->
            val currentUser = userSnapshot.getValue<User>()
            Log.d(Constants.DEBUG_TAG, "current user fetch:success, $currentUser")
            currentUser?.chats?.let {
                fetchUsersWithChats(it, searchString)
            }
        }.addOnFailureListener {
            Log.d(Constants.DEBUG_TAG, "current user fetch:failure", it)
        }
    }

    private fun fetchUsersWithChats(userToChat: Map<String, String>, searchString: String?) {
        val chatToUser = userToChat.entries.associate { (k, v) -> v to k }
        val userUids = userToChat.keys.toList()

        usersRef.get().addOnSuccessListener { usersSnapshot ->
            var users = usersSnapshot.getValue<Map<String, User>>()?.filter { (key, user) ->
                key in userUids
            }?.onEach { (uid, user) ->
                user.uid = uid
            }

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
            }

            Log.d(Constants.DEBUG_TAG, "chat users fetch:success, $users")

            users?.let {
                val chatUids = it.map { (key, _) -> userToChat[key]!! }
                fetchChatsForUsers(chatUids, it, chatToUser)
            }
        }.addOnFailureListener {
            Log.d(Constants.DEBUG_TAG, "current user fetch:failure, $it")
        }
    }

    private fun fetchChatsForUsers(
        chatUids: List<String>,
        users: Map<String, User>,
        chatToUser: Map<String, String>
    ) {
        chatsRef.get().addOnSuccessListener { chatsSnapshot ->
            val chatsMap = chatsSnapshot.getValue<Map<String, Chat>>()?.filter { (uid, _) ->
                uid in chatUids
            }?.onEach { (chatUid, chat) ->
                chat.uid = chatUid
                chat.with = users[chatToUser[chatUid]]
            }
            Log.d(Constants.DEBUG_TAG, "user chats fetch:success, $chatsMap")

            chatsMap?.let {
                val chats = chatsMap.values.sortedByDescending {
                    it.lastMessage?.timestamp
                }.toMutableList()
                presenter.onRecentChatsFetched(chats)
            }
        }.addOnFailureListener {
            Log.d(Constants.DEBUG_TAG, "user chats fetch:failure, $it")
        }
    }
}