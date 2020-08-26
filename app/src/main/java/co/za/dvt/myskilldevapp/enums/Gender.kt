package co.za.dvt.myskilldevapp.enums

enum class Gender constructor(var id: Int, var sex: String, var genderTitle: String, var genderPronoun: String) {
    Neutral(0, "Neutral", "Neutral", "Neutral"),
    Male(1, "Male", "Man", "He"),
    Female(2, "Female", "Woman", "She")
}