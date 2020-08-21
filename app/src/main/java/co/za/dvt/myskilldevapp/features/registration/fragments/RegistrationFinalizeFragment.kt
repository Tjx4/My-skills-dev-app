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
        binding.fullNames = registrationActivity?.registrationViewModel?.fullNames?.value
        binding.registerGender = registrationActivity?.registrationViewModel?.gender?.value?.sex
        binding.registerEmail = registrationActivity?.registrationViewModel?.email?.value
        var mobile = registrationActivity?.registrationViewModel?.mobileNumber?.value?.toString()
        binding.registerMobileNumber = mobile?: ""
        val parentView = binding.root
        initViews(parentView)
        return parentView
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            registrationActivity?.registrationViewModel?.setNames()

registrationActivity?.registrationViewModel?.name?.value
registrationActivity?.registrationViewModel?.surname?.value
registrationActivity?.registrationViewModel?.email?.value
        }
    }

     fun initViews(parentView: View) {

    }

    companion object {
        fun newInstance(title: String, description: String): BaseRegistrationFragment {
            val registrationStep3Fragment = RegistrationFinalizeFragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep3Fragment.arguments = payload
            registrationStep3Fragment.title = title
            registrationStep3Fragment.description = description
            return registrationStep3Fragment
        }
    }

}