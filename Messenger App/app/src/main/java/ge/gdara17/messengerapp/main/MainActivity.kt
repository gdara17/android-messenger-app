package ge.gdara17.messengerapp.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import ge.gdara17.messengerapp.R
import ge.gdara17.messengerapp.main.settings.SettingsFragment
import ge.gdara17.messengerapp.chat.ChatActivity
import ge.gdara17.messengerapp.contacts.ContactsActivity
import ge.gdara17.messengerapp.databinding.ActivityMainBinding
import ge.gdara17.messengerapp.dataclasses.User
import ge.gdara17.messengerapp.main.recentchats.RecentChatsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tabItemSelectedListener =
        NavigationBarView.OnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            if (item.itemId == R.id.bottom_navigation_item_home) {
                fragment = RecentChatsFragment()
            } else {
                fragment = SettingsFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()

            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationView()
        setClickListeners()
    }

    private fun initNavigationView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, RecentChatsFragment())
            .commit()
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener(tabItemSelectedListener)
    }

    private fun setClickListeners() {
        binding.fab.setOnClickListener {
            val i = Intent(this, ContactsActivity::class.java)
            startActivity(i)
        }
    }

}