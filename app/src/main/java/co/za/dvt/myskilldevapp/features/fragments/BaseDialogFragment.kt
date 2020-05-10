package co.za.dvt.myskilldevapp.features.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import co.za.dvt.myskilldevapp.R
import co.za.dvt.myskilldevapp.constants.LAYOUT

abstract class BaseDialogFragment : DialogFragment() {
    protected var clickedView: View? = null
    protected var activity: AppCompatActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.attributes.windowAnimations = R.style.DialogTheme

        val layout = arguments!!.getInt(LAYOUT)
        return inflater.inflate(layout, container, false)
    }

    protected fun setViewClickEvents(views: Array<View>) {
        for (view in views) {
            if (view == null)
                continue

            view.setOnClickListener { v -> onFragmentViewClickedEvent(v) }
        }
    }

    open fun hideLoaderAndShowEnterMessage() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity?
    }

    protected fun setListviewClickEvents(listView: ListView) {
        listView.setOnItemClickListener { parent, view, position, id ->
            onFragmentViewClickedEvent(view)
        }
    }

    protected fun onFragmentViewClickedEvent(view: View) {
        dialog?.dismiss()
    }

}