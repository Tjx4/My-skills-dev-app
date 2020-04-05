package co.za.dvt.myskilldevapp.extensions

import java.util.regex.Pattern

fun String.isValidLuckyNumber() = evaluateRegex(this, "/^[0-6]+\$/")

private fun evaluateRegex(valu: String, regex: String): Boolean {
    val inputStr = valu.trim { it <= ' ' }
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(inputStr)
    return matcher.matches()
}
