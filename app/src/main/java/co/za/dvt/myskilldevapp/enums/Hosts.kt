package co.za.dvt.myskilldevapp.enums

enum class Hosts(var url: String, var ip: String) {
    LocalHost("", ""),
    UAT("http://appicsoftware.co.za/", ""),
    PRODUCTION("", "")
}