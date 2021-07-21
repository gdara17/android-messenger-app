package ge.gdara17.messengerapp.main.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ge.gdara17.messengerapp.databinding.FragmentSettingsBinding
import ge.gdara17.messengerapp.dataclasses.User

class SettingsFragment : Fragment(), SettingsContract.View {
    private val presenter: SettingsContract.Presenter = SettingsPresenter(this)
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        initViews()
        return view
    }

    private fun initViews() {
        binding.btnSettingsUpdate.setOnClickListener {
            val username = binding.etSettingsNickname.text.toString()
            val occupation = binding.etSettingsOccupation.text.toString()
            presenter.updateUser(username, occupation)
        }

        binding.btnSettingsSignOut.setOnClickListener {
            presenter.signOutUser()
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSettingsUpdateSuccess() {
        Toast.makeText(context, "Profile update successfully", Toast.LENGTH_LONG).show()
    }

    override fun onSettingsUpdateFail() {
        Toast.makeText(context, "Profile update failed", Toast.LENGTH_LONG).show()
    }

}