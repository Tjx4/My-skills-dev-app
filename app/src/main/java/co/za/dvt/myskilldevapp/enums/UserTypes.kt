package co.za.dvt.myskilldevapp.enums

import co.za.dvt.myskilldevapp.R

enum class UserTypes(var id: Int, var typeName: String, var description: String, var folder: String, var icon: Int) {
    Member(0, "Member", "This is a Member", "members", R.drawable.ic_member_light),
    Host(0, "Host", "This is a host", "stylists", R.drawable.ic_host_light)
}