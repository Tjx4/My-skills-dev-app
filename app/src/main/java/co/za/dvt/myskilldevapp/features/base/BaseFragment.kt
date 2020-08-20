package co.za.dvt.myskilldevapp.features.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment()  {
    var title: String? = null
    var description: String? = null
}