package co.za.dvt.myskilldevapp.features.registration.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.FragmentRegistrationFinalizeBinding
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationFinalizeFragment : BaseRegistrationFragment() {
    private lateinit var binding: FragmentRegistrationFinalizeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // val parentView = inflater.inflate(R.layout.fragment_registration_finalize, container, false)
        binding = DataBindingUtil.setContentView(registrationActivity as Activity, R.layout.fragment_registration_finalize)
        binding.lifecycleOwner = this
        binding.fullNames = registrationActivity?.registrationViewModel?.fullNames?.value
        val parentView = binding.root

        initViews(parentView)
        return parentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //binding = DataBindingUtil.setContentView(registrationActivity as Activity, R.layout.fragment_registration_finalize)
        //binding.lifecycleOwner = this
        // binding.fullNames = registrationActivity?.registrationViewModel?.fullNames?.value
        //val parentView = binding.root
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            registrationActivity?.registrationViewModel?.setNames()
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