package ge.gdara17.messengerapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ge.gdara17.messengerapp.R
import ge.gdara17.messengerapp.databinding.ActivityContactsBinding
import ge.gdara17.messengerapp.databinding.ActivityLoginBinding
import ge.gdara17.messengerapp.main.MainActivity
import javax.security.auth.login.LoginException

class LogInActivity : AppCompatActivity(), LogInContract.View {
    private lateinit var binding: ActivityLoginBinding
    private val presenter: LogInContract.Presenter = LogInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (presenter.isUserLoggedIn()) {
            onLoginSuccess()
        }
        addListeners()
    }

    private fun addListeners() {
        binding.btnLoginSignIn.setOnClickListener {
            val username = binding.etLoginNickname.text.toString()
            val password = binding.etLoginPassword.text.toString()

            presenter.validateUser(username, password)
        }

        binding.btnLoginSignUp.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
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