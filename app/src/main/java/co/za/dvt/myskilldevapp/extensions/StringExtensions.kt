package co.za.dvt.myskilldevapp.extensions

import java.util.regex.Pattern


fun String.isValidUsername() =  isValidName() || isValidMobileNumber() || isValidEmail()
//fun String.isValidGender() = this is Gender
fun String.isValidOTP(): Boolean =  !this.isEmpty() && this.length > 3
fun String.isValidName(): Boolean =  !this.isEmpty() && this.length > 3
fun String.isValidMobileNumber(): Boolean =  evaluateRegex(this, "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")
fun String.isValidEmail(): Boolean =  evaluateRegex(this, "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
fun String.isValidPassword(): Boolean =  evaluateRegex(this, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
fun String.isMatchPasswords(passwordConfirmation: String?): Boolean = this == passwordConfirmation

private fun evaluateRegex(valu: String, regex: String): Boolean {
    val inputStr = valu.trim { it <= ' ' }
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(inputStr)
    return matcher.matches()
}
