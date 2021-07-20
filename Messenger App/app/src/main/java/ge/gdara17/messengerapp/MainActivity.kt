package ge.gdara17.messengerapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.gdara17.messengerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener(tabItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, RecentChatsFragment())
            .commit()

        binding.fab.setOnClickListener {
            val i = Intent(this, ChatActivity::class.java)
            startActivity(i)
        }
//        val database = Firebase.database
//        val myRef = database.getReference("message")
//        myRef.setValue("Hello World!")
    }

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
}