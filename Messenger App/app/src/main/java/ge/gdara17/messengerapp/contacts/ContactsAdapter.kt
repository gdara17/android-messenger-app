package ge.gdara17.messengerapp.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ge.gdara17.messengerapp.databinding.ContactCellBinding
import ge.gdara17.messengerapp.dataclasses.Chat
import ge.gdara17.messengerapp.dataclasses.User

class ContactsAdapter(private val listener: ContactClickListener) :
    RecyclerView.Adapter<ContactsAdapter.ContactCellViewHolder>() {
    private lateinit var contacts: MutableList<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactCellViewHolder {
        val binding =
            ContactCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactCellViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ContactCellViewHolder, position: Int) {
        holder.configure(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun submitList(contacts: MutableList<User>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    fun getAt(position: Int): User {
        return contacts[position]
    }


    class ContactCellViewHolder(
        private val binding: ContactCellBinding,
        private val listener: ContactClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun configure(contact: User) {
            // TODO: avatar
            binding.tvContactName.text = contact.name
            binding.tvContactOccupation.text = contact.occupation

            addClickListeners()
        }

        private fun addClickListeners() {
            binding.root.setOnClickListener {
                listener.onContactClick(adapterPosition)
            }
        }
    }
}