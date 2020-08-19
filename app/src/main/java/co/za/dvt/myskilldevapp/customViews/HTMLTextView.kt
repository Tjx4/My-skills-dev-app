package co.za.dvt.myskilldevapp.customViews

import android.content.Context
import android.os.Build
import android.text.Html
import androidx.appcompat.widget.AppCompatTextView

class HTMLTextView(context: Context?) : AppCompatTextView(context) {

    init {
       // text = Html.fromHtml(text.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(text.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            text = Html.fromHtml(text.toString());
        }
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.text = Html.fromHtml(text.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            this.text = Html.fromHtml(text.toString());
        }
    }
}