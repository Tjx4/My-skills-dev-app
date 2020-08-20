package co.za.dvt.myskilldevapp.features.base

import android.content.Context
import androidx.fragment.app.Fragment
import co.za.dvt.myskilldevapp.features.registration.RegistrationActivity

abstract class BaseRegistrationFragment : Fragment()  {
    var title: String? = null
    var description: String? = null
    protected var registrationActivity: RegistrationActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registrationActivity = context as RegistrationActivity
    }

}