package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.FragmentRegistrationStep2Binding
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationPersonalDetailsFragment : BaseRegistrationFragment() {
    lateinit var binding: FragmentRegistrationStep2Binding
    private var signInBtn: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationStep2Binding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.registerName = registrationActivity?.registrationViewModel?.name?.value
        val parentView = binding.root

        initViews(parentView)
        return parentView
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {

        }
    }

    fun initViews(parentView: View){
        signInBtn = parentView.findViewById(R.id.btnSignIn)
        signInBtn?.setOnClickListener {
            registrationActivity?.moveToFinalStep()
        }
    }

    companion object {
        fun newInstance(title: String, description: String): BaseRegistrationFragment {
            val registrationStep2Fragment = RegistrationPersonalDetailsFragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep2Fragment.arguments = payload
            registrationStep2Fragment.title = title
            registrationStep2Fragment.description = description
            return registrationStep2Fragment
        }
    }

}
