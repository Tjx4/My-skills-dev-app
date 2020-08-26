package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.za.dvt.myskilldevapp.databinding.FragmentRegistrationFinalizeBinding
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationFinalizeFragment : BaseRegistrationFragment() {
    private lateinit var binding: FragmentRegistrationFinalizeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationFinalizeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val parentView = binding.root
        initViews(parentView)
        return parentView
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            registrationActivity?.registrationViewModel?.setNames()
            //Todo: Fix 2 way binding
            binding.fullNames =  "Full name: ${registrationActivity?.registrationViewModel?.fullNames?.value ?: "Unspecified"}"
            binding.registerGender =  "Gender: ${registrationActivity?.registrationViewModel?.gender?.value?.sex ?: "Unspecified"}"
            binding.registerEmail = "Email: ${registrationActivity?.registrationViewModel?.email?.value ?: "Unspecified"}"
            binding.registerMobileNumber = "Mobile: ${registrationActivity?.registrationViewModel?.mobileNumber?.value?.toString() ?: "Unspecified"}"

        }
    }

     fun initViews(parentView: View) {

    }

    companion object {
        fun newInstance(title: String, description: String, isEnabled: Boolean): BaseRegistrationFragment {
            val registrationStep3Fragment = RegistrationFinalizeFragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep3Fragment.arguments = payload
            registrationStep3Fragment.title = title
            registrationStep3Fragment.isEnabled = isEnabled
            registrationStep3Fragment.description = description
            return registrationStep3Fragment
        }
    }

}