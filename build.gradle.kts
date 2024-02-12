plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    kotlin("jvm") version "1.9.21" apply false

}
buildscript {
    dependencies {
        classpath ("dev.icerock.moko:resources-generator:0.23.0")
        classpath(kotlin("gradle-plugin", version = "1.9.21"))
    }
}