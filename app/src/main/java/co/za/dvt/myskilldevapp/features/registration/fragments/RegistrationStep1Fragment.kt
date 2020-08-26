package co.za.dvt.myskilldevapp.features.registration.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.adapters.UserTypeAdapter
import co.za.dvt.myskilldevapp.enums.UserTypes
import co.za.dvt.myskilldevapp.features.base.BaseRegistrationFragment
import co.za.dvt.myskilldevapp.helpers.showShortToast

class RegistrationStep1Fragment : BaseRegistrationFragment(), UserTypeAdapter.UserTypeClickListener {

    var userTypesRv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var parentView = inflater.inflate(R.layout.fragment_registration_step1, container, false)
        initViews(parentView)
        return parentView
    }

    //Todo: Fix bug not trigering when navigating programatically
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if (menuVisible) {

        }
    }

    fun initViews(parentView: View) {
        var userTypeAdapter = UserTypeAdapter(registrationActivity!!)
        userTypeAdapter.setClickListener(this)

        userTypesRv = parentView.findViewById(R.id.rvUserTypes)
        userTypesRv?.adapter = userTypeAdapter

        val gridLayoutManager = GridLayoutManager(registrationActivity, 2)
        gridLayoutManager.initialPrefetchItemCount = UserTypes.values().size
        userTypesRv?.onFlingListener = null
        userTypesRv?.layoutManager = gridLayoutManager
    }

    companion object {
        fun newInstance(title: String, description: String, isEnabled: Boolean): BaseRegistrationFragment {
            val registrationStep1Fragment = RegistrationStep1Fragment()
            var payload = Bundle()
            //payload.putString(TITLE, title)
            registrationStep1Fragment.arguments = payload
            registrationStep1Fragment.isEnabled = isEnabled
            registrationStep1Fragment.title = title
            registrationStep1Fragment.description = description
            return registrationStep1Fragment
        }
    }

    override fun onItemClick(view: View, position: Int) {
        val userType = UserTypes.values()[position]
        registrationActivity?.registrationViewModel?.setUserType(userType)
        isEnabled = true
    }
}
