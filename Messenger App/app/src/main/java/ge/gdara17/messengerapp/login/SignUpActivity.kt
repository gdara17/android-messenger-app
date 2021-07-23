package ge.gdara17.messengerapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ge.gdara17.messengerapp.R
import ge.gdara17.messengerapp.databinding.ActivityLoginBinding
import ge.gdara17.messengerapp.databinding.ActivitySignUpBinding
import ge.gdara17.messengerapp.dataclasses.User
import ge.gdara17.messengerapp.main.MainActivity

class SignUpActivity : AppCompatActivity(), LogInContract.View {
    private lateinit var binding: ActivitySignUpBinding
    private val presenter: LogInContract.Presenter = LogInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addListeners()
    }

    private fun addListeners() {
        binding.btnSignUpSignUp.setOnClickListener {
            val username = binding.etSignUpNickname.text.toString()
            val occupation = binding.etSignUpOccupation.text.toString()
            val password = binding.etSignUpPassword.text.toString()

            val user = User(
                username = username,
                occupation = occupation
            )

            presenter.createUser(user, password)
        }
    }

    override fun onLoginSuccess() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onLoginFail() {
        Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
    }
}