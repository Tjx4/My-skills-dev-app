package co.za.dvt.myskilldevapp.helpers

import java.text.SimpleDateFormat
import java.util.*

fun getYearMonthDayAndTime() = SimpleDateFormat("Y-MM-dd hh:mm").format(Date())