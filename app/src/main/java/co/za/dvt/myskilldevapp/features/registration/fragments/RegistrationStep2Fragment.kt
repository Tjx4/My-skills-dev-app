package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.base.BaseFragment

class RegistrationStep2Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_registration_step2, container, false)
    }

    companion object {
        fun newInstance(title: String, description: String): BaseFragment {
            val registrationStep2Fragment = RegistrationStep2Fragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep2Fragment.arguments = payload
            registrationStep2Fragment.title = title
            registrationStep2Fragment.description = description
            return registrationStep2Fragment
        }
    }

}
