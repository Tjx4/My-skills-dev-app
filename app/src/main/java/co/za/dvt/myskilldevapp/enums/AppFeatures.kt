package co.za.dvt.myskilldevapp.enums

import co.za.dvt.myskilldevapp.R

enum class AppFeatures(var featureName: String, var featureIcon: Int) {
    Characters("Characters", R.drawable.ic_character_dark),
    Houses("Houses", R.drawable.ic_house_dark),
    Spells("Spells", R.drawable.ic_spell_dark)
}