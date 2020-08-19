package co.za.dvt.myskilldevapp.helpers

import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.LAYOUT
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment
import co.za.dvt.myskilldevapp.features.fragments.LoadingSpinnerFragment

fun showLoadingDialog(loadingMessage: String, activity: BaseActivity) {
    var loadingSpinnerFragment = LoadingSpinnerFragment.newInstance("")
    loadingSpinnerFragment.isCancelable = false
    showDialogFragment(loadingMessage, R.layout.fragment_loading_spinner, loadingSpinnerFragment, activity)

}

fun hideCurrentLoadingDialog(activity: BaseActivity) {
    activity.activeDialogFragment?.dismiss()
}

fun showDialogFragment(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment, activity: BaseActivity) {
    activity?.activeDialogFragment?.dismiss()
    val ft = activity.supportFragmentManager.beginTransaction()
    var newFragment = getFragmentDialog(title, Layout, newFragmentBaseBase)
    newFragment.show(ft, "dialog")
    activity.activeDialogFragment = newFragment
}

private fun getFragmentDialog(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment) : BaseDialogFragment {
    val payload = newFragmentBaseBase.arguments
    payload?.putString(TITLE, title)
    payload?.putInt(LAYOUT, Layout)

    newFragmentBaseBase.arguments = payload
    return newFragmentBaseBase
}

