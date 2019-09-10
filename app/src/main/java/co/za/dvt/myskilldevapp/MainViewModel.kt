package co.za.dvt.myskilldevapp

class MainViewModel {
    var message: String

    init {
        message = "Hello MVVM"
    }

    fun showAllGood(){
        message = "All good"
    }
}