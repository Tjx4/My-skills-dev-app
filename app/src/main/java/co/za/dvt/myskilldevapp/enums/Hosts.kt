package co.za.dvt.myskilldevapp.enums

enum class Hosts(var url: String, var ip: String) {
    LocalHost("http://localhost:8888/platinum_access_backend/", ""),
    UAT("http://appicsoftware.co.za/", ""),
    PRODUCTION("", "")
}