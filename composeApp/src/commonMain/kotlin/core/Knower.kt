package core

object Knower {
    fun Knower.d(title: String, message: String) {
        println("DEBUG $title \n $message")
    }

    fun Knower.e(title: String, message: String) {
        println("ERROR $title \n  $message")
    }
}