package core.util
 fun List<Any>.toDbString(): String {
    return this.joinToString("|&|")
}
fun String.toListString(): List<String> {
    return this.split("|&|")
}
fun String.toListInt(): List<Int> {
    return this.split("|&|").map { it.toInt() }
}