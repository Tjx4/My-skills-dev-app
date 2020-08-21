package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.databinding.FragmentRegistrationStep2Binding
import co.za.dvt.myskilldevapp.enums.Gender
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment

class RegistrationPersonalDetailsFragment : BaseRegistrationFragment() {
    lateinit var binding: FragmentRegistrationStep2Binding
    private var signInBtn: Button? = null
    private var genderContainerRg: RadioGroup? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegistrationStep2Binding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.registerName = registrationActivity?.registrationViewModel?.name?.value
        binding.registerSurname = registrationActivity?.registrationViewModel?.surname?.value
        binding.registerEmail = registrationActivity?.registrationViewModel?.email?.value
        binding.registerMobileNumber = registrationActivity?.registrationViewModel?.mobileNumber?.value?.toString()?: ""
        binding.registerPassword = registrationActivity?.registrationViewModel?.password?.value
        binding.registerConfirmPassword = registrationActivity?.registrationViewModel?.confirmPassword?.value
        val parentView = binding.root

        initViews(parentView)
        return parentView
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {

        }
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
            registrationActivity?.moveToFinalStep()

registrationActivity?.registrationViewModel?.name?.value
registrationActivity?.registrationViewModel?.surname?.value
registrationActivity?.registrationViewModel?.email?.value
        }
    }

    companion object {
        fun newInstance(title: String, description: String): BaseRegistrationFragment {
            val registrationStep2Fragment = RegistrationPersonalDetailsFragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep2Fragment.arguments = payload
            registrationStep2Fragment.title = title
            registrationStep2Fragment.description = description
            return registrationStep2Fragment
        }
    }

}
