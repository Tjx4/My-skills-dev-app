package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.base.BaseFragment
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.features.login.fragments.UsersFragment

class RegistrationStep1Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        title = arguments?.getString(TITLE)


        return inflater.inflate(R.layout.fragment_registration_step1, container, false)
    }

    companion object {
        fun newInstance(title: String, description: String): BaseFragment {
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
