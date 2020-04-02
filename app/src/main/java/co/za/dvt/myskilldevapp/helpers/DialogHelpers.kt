package co.za.dvt.myskilldevapp.helpers

import android.os.Bundle
import co.za.dvt.myskilldevapp.constants.LAYOUT
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment

fun getFragmentDialog(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment) : BaseDialogFragment {
    val payload = Bundle()
    payload.putString(TITLE, title)
    payload.putInt(LAYOUT, Layout)

    newFragmentBaseBase.arguments = payload
    return newFragmentBaseBase
}

fun showDialogFragment(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment, activity: BaseActivity) {
    if(activity.activeDialogFragment != null){
        hideCurrentLoadingDialog(activity)
    }

    val ft = activity.supportFragmentManager.beginTransaction()
    var newFragment = getFragmentDialog(title, Layout, newFragmentBaseBase)
    newFragment.show(ft, "dialog")
}