package core

object Knower {
    fun Knower.d(title: String, message: String) {
        println("Knower: DEBUG $title \n $message")
    }
    fun Knower.t(title: String, message: String) {
        println("Knower: TEST $title \n $message")
    }

    fun Knower.e(title: String, message: String) {
        println("Knower: ERROR $title \n  $message")
    }
}