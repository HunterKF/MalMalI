import org.jetbrains.compose.ExperimentalComposeLibrary
import java.util.Properties

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.1")
    }
}
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinSerialization)
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
    id("dev.icerock.mobile.multiplatform-resources")
    id("app.cash.sqldelight") version "2.0.1"
    id("com.codingfeline.buildkonfig") version "+"



}
val coroutinesVersion = "1.7.3"
val ktorVersion = "2.3.5"
val dateTimeVersion = "0.4.1"
kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.16.1")
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0") // toUIColor here
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                implementation(platform("io.github.jan-tennert.supabase:bom:2.0.4"))
                implementation("io.github.jan-tennert.supabase:postgrest-kt")
                implementation("io.github.jan-tennert.supabase:compose-auth:2.0.4")
                implementation("io.github.jan-tennert.supabase:gotrue-kt:2.0.4")

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
//                implementation(libs.ktor.auth)
                implementation(libs.ktor.logging)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.sql.coroutines.extensions)
                implementation(libs.multiplatform.settings)
                implementation(libs.multiplatform.settings.no.arg)

                implementation(libs.decompose)
                implementation(libs.decompose.jetbrains)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.turbine)
                implementation(libs.multiplatform.settings.test)
                implementation(libs.mockative)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)

            dependencies {
                implementation(libs.sql.android.driver)
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activity.compose)
                implementation(libs.decompose)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.android)

                implementation(libs.kotlinx.coroutines.android)
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting {
            resources.srcDirs("build/generated/moko/iosX64Main/src")
        }
        val iosArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosArm64Main/src")
        }
        val iosSimulatorArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
        }
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.sql.native.driver)
                implementation(libs.ktor.client.ios)
                implementation(libs.ktor.client.darwin)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.jaegerapps.malmali"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jaegerapps.malmali"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation("androidx.core:core:1.10.1")
    commonMainApi("dev.icerock.moko:mvvm-core:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
    commonMainApi("dev.icerock.moko:resources-compose:0.23.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.21-1.0.15")

    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {

            add(it.name, "io.mockative:mockative-processor:2.0.1")
        }
//    implementation(libs.sqlite.driver)
}

multiplatformResources {
    disableStaticFrameworkWarning = true
    multiplatformResourcesPackage = "com.jaegerapps.malmali" // required
}

sqldelight {
    databases {
        create(name = "MalMalIDatabase") {
            packageName.set("com.jaegerapps.malmali.composeApp.database")
        }
    }
}

buildkonfig {
    packageName = "com.jaegerapps.malmali"

    defaultConfigs {
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use {
                localProperties.load(it)
            }
        }
        val supabaseApiKey = localProperties.getProperty("API_KEY") ?: "default_value"
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "API_KEY", supabaseApiKey)
        val gptApiKey = localProperties.getProperty("GPT_API_KEY") ?: "default_value"
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "GPT_API_KEY", gptApiKey)
    }
}