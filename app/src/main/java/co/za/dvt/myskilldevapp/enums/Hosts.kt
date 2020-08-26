package co.za.dvt.myskilldevapp.enums

enum class Hosts(var url: String, var ip: String) {
    LocalHost("http://localhost:8888/platinum_access_backend/", "http://10.0.2.2:8888/platinum_access_backend/"),
    UAT("http://appicsoftware.co.za/api/Platinum_access_backend/", "http://160.153.129.204/api/Platinum_access_backend/"),
    PRODUCTION("", "")
}