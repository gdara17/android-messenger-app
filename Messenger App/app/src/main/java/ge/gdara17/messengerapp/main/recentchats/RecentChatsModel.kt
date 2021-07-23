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
import ge.gdara17.messengerapp.dataclasses.Message
import ge.gdara17.messengerapp.dataclasses.User
import java.util.*

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

    fun fetchRecentChats() {
        usersRef.child(auth.currentUser?.uid!!).get().addOnSuccessListener { userSnapshot ->
            val currentUser = userSnapshot.getValue<User>()
            Log.d(Constants.DEBUG_TAG, "current user fetch:success, $currentUser")
            currentUser?.chats?.let {
                fetchUsersWithChats(it)
            }
        }.addOnFailureListener {
            Log.d(Constants.DEBUG_TAG, "current user fetch:failure", it)
        }
    }

    private fun fetchUsersWithChats(userToChat: Map<String, String>) {
        val chatToUser = userToChat.entries.associate { (k, v) -> v to k }
        val chatUids = userToChat.values.toList()
        val userUids = userToChat.keys.toList()

        usersRef.get().addOnSuccessListener { usersSnapshot ->
            val users = usersSnapshot.getValue<Map<String, User>>()?.filter { (key, _) ->
                key in userUids
            }?.onEach { (uid, user) ->
                user.uid = uid
            }
            Log.d(Constants.DEBUG_TAG, "chat users fetch:success, $users")

            users?.let {
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