package com.jaegerapps.malmali.common.mappers

fun booleanToLong(value: Boolean): Long {
    return if (value) 1 else 0
}
fun longToBoolean(value: Long): Boolean {
    return value == 1L
}