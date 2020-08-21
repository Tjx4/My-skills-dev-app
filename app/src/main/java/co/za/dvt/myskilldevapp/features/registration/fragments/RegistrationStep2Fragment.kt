package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationStep2Fragment : BaseRegistrationFragment() {

    private var signInBtn: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var parentView = inflater.inflate(R.layout.fragment_registration_step2, container, false)
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