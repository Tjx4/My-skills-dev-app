package co.za.dvt.myskilldevapp.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import android.widget.Toast
import co.za.dvt.myskilldevapp.R


fun showShortToast(message: String, context: Context){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showLongToast(message: String, context: Context){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun showSuccessAlert(context: Context, title: String, message: String, buttonText: String = "Ok", callbackFun:  () -> Unit = {}){
    val ab = setupBasicMessage(title, message, buttonText, "", "", callbackFun, {}, {}, context)
    ab.setIcon(R.drawable.success_icon)
    showAlertMessage(ab, context)
}

fun showErrorAlert(context: Context, title: String, message: String, buttonText: String = "Ok", callbackFun: () -> Unit = {}){
    val ab = setupBasicMessage(title, message, buttonText, "", "", callbackFun, {}, {}, context)
    ab.setIcon(R.drawable.error_icon)
    showAlertMessage(ab, context)
}

fun showConfirmAlert(context: Context, title: String, message: String, yesButtonText: String, neutralButtonText: String, noButtonText: String, yesCallbackFun: () -> Unit, neutralCallback: () -> Unit = {}, noCallbackFun: () -> Unit){
    val ab = setupBasicMessage(title, message, yesButtonText, neutralButtonText, noButtonText, yesCallbackFun, neutralCallback, noCallbackFun, context)
    ab.setIcon(R.drawable.confirm_icon)
    showAlertMessage(ab, context)
}

private fun setupBasicMessage(title: String,message: String,
                              positiveButtonText: String?, neutralButtonText: String?, negetiveButtonText: String?,
                              positiveCallback: () -> Unit, neutralCallback: () -> Unit, negetiveCallback: () -> Unit,
                              context: Context
): AlertDialog.Builder {

    val ab = AlertDialog.Builder(context, R.style.AlertDialogCustom)
    ab.setMessage(message)
        .setTitle(title)
        .setPositiveButton(positiveButtonText) { dialogInterface, i ->
            positiveCallback()
        }

    if (neutralButtonText != null) {
        ab.setNeutralButton(neutralButtonText) { dialogInterface, i ->
            neutralCallback()
        }
    }

    if (negetiveButtonText != null) {
        ab.setNegativeButton(negetiveButtonText) { dialogInterface, i ->
            negetiveCallback()
        }
    }

    return ab
}

private fun showAlertMessage(ab: AlertDialog.Builder, context: Context) {
    val a = ab.create()
    a.requestWindowFeature(Window.FEATURE_NO_TITLE)
    a.show()

    a.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.lightText))
    a.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.resources.getColor(R.color.lightText))
    a.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(context.resources.getColor(R.color.lightText))
}