package co.za.dvt.myskilldevapp.enums

enum class Gender constructor(var id: Int, var gender: String, var genderTitle: String, var genderPronoun: String) {
    Male(1, "Male", "Man", "He"),
    Female(2, "Female", "Woman", "She")
}