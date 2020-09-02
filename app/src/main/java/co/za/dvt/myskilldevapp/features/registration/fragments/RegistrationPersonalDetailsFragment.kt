package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.FragmentRegistrationStep2Binding
import co.za.dvt.myskilldevapp.enums.Gender
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationPersonalDetailsFragment : BaseRegistrationFragment() {
    lateinit var binding: FragmentRegistrationStep2Binding
    private var signInBtn: Button? = null

    lateinit var parentView: View
    private var registerNameTxt: EditText? = null
    private var genderContainerRg: RadioGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_step2,container, false)
        binding.lifecycleOwner = this
        binding.registrationViewModel = registrationActivity?.registrationViewModel
        parentView = binding.root
        initViews(parentView)
        return parentView
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {

        }
        else{
            hackIt()
        }
    }

    fun hackIt(){
        registerNameTxt = parentView.findViewById(R.id.txtRegisterName)
        //registrationActivity?.registrationViewModel?.name?.value = registerNameTxt?.text.toString()
    }

    fun initViews(parentView: View){

        genderContainerRg = parentView.findViewById(R.id.rgGenderContainer)
        genderContainerRg?.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rdoFemale -> {
                        registrationActivity?.registrationViewModel?.setGender(Gender.Female)
                    }
                    R.id.rdoMale -> {
                        registrationActivity?.registrationViewModel?.setGender(Gender.Female)
                    }
                }
            })

        signInBtn = parentView.findViewById(R.id.btnSignIn)
        signInBtn?.setOnClickListener {
            registrationActivity?.enableFinalizeStep()
            registrationActivity?.moveToFinalStep()
        }
    }

    companion object {
        fun newInstance(title: String, description: String, isEnabled: Boolean): BaseRegistrationFragment {
            val registrationStep2Fragment = RegistrationPersonalDetailsFragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep2Fragment.arguments = payload
            registrationStep2Fragment.isEnabled = isEnabled
            registrationStep2Fragment.title = title
            registrationStep2Fragment.description = description
            return registrationStep2Fragment
        }
    }

}
