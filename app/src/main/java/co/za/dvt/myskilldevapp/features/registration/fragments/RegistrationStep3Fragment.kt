package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationStep3Fragment : BaseRegistrationFragment() {
    //private lateinit var binding: FragmentRegistrationStep3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_registration_step3, container, false)
        initViews(parentView)
        return parentView
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
            val registrationStep3Fragment = RegistrationStep3Fragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep3Fragment.arguments = payload
            registrationStep3Fragment.title = title
            registrationStep3Fragment.description = description
            return registrationStep3Fragment
        }
    }

}