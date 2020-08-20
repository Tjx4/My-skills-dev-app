package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment
import co.za.dvt.myskilldevapp.helpers.showShortToast

class RegistrationStep1Fragment : BaseRegistrationFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration_step1, container, false)
    }

    //Todo: Fix bug not trigering when navigating programatically
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {
            showShortToast("RegistrationStep1Fragment", registrationActivity!!)
        }
    }

    companion object {
        fun newInstance(title: String, description: String): BaseRegistrationFragment {
            val registrationStep1Fragment = RegistrationStep1Fragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep1Fragment.arguments = payload
            registrationStep1Fragment.title = title
            registrationStep1Fragment.description = description
            return registrationStep1Fragment
        }
    }
}
