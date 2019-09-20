package co.za.dvt.myskilldevapp.helpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.za.dvt.myskilldevapp.constants.LAYOUT
import co.za.dvt.myskilldevapp.constants.TITLE
import co.za.dvt.myskilldevapp.features.activities.BaseActivity
import co.za.dvt.myskilldevapp.features.fragments.BaseDialogFragment

fun getFragmentDialog(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment, activity: BaseActivity) : BaseDialogFragment {
    val payload = Bundle()
    payload.putString(TITLE, title)
    payload.putInt(LAYOUT, Layout)

    newFragmentBaseBase.arguments = payload
    activity.activeDialogFragment = newFragmentBaseBase
    return newFragmentBaseBase
}

fun showFragmentDialog(title: String, Layout: Int, newFragmentBaseBase: BaseDialogFragment, activity: BaseActivity) {
    val ft = activity.supportFragmentManager.beginTransaction()
    var newFragment = getFragmentDialog(title, Layout, newFragmentBaseBase, activity)
    newFragment.show(ft, "dialog")
    activity.activeDialogFragment = newFragmentBaseBase
}