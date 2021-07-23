package ge.gdara17.messengerapp.chat

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.Constants
import ge.gdara17.messengerapp.dataclasses.Message

class ChatModel(private val presenter: ChatContract.Presenter, private val chatUid: String) :
    ChatContract.Model {
    private val auth = Firebase.auth
    private val database = Firebase.database
    private val messagesRef = database.getReference("messages")
    private val chatsRef = database.getReference("chats")


    init {
        addMessagesValueListener()
    }

    private fun addMessagesValueListener() {
        messagesRef.child(chatUid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = snapshot.getValue<MutableMap<String, Message>>()
                    messages?.apply {
                        forEach { (_, message) ->
                            message.chatUid = chatUid
                            message.received = message.sender != auth.currentUser?.uid
                        }
                        presenter.onChatUpdated(messages.values.sortedBy {
                            it.timestamp
                        }.toMutableList())
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    override fun fetchChat() {

    }

    override fun sendMessage(message: Message) {
        message.sender = auth.currentUser?.uid
        val chatUid = message.chatUid!!

        val chatRef = messagesRef.child(chatUid)
        val messageKey = chatRef.push().key
        if (messageKey == null) {
            Log.d(Constants.DEBUG_TAG, "could not add message")
            return
        }

        chatRef.child(messageKey).setValue(message)
        chatsRef.child(chatUid).child("lastMessage").setValue(message)
    }
}
