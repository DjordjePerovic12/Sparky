import java.io.IOException
import java.util.Properties

plugins {
    `version-catalog`
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.androidx.navigation.safeargs)
    id("kotlin-parcelize")
    alias(libs.plugins.hilt.android.plugin)
}

val appName = "Sparky"

val majorVersion = "0.0"

fun String.runCommand(
    workingDir: File = File("."), timeoutAmount: Long = 60, timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String =
    ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex())).directory(
        workingDir
    ).redirectOutput(ProcessBuilder.Redirect.PIPE).redirectError(ProcessBuilder.Redirect.PIPE)
        .start().apply { waitFor(timeoutAmount, timeoutUnit) }.run {
            val error = errorStream.bufferedReader().readText().trim()
            if (error.isNotEmpty()) {
                throw IOException(error)
            }
            inputStream.bufferedReader().readText().trim()
        }

fun generateVersionCode(): Int? {
    var command = "git rev-list --all --count"
    var result = command.runCommand(workingDir = rootDir)
    if (result.isEmpty()) {
        command = "PowerShell -Command git rev-list HEAD --count"
        result = command.runCommand(workingDir = rootDir)
    }
    if (result.isEmpty()) result = "1"
    return result.toIntOrNull()
}
android {
    namespace = "ltd.bokadev.sparky_social_media"
    compileSdk = 34

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        manifestPlaceholders["appIcon"] = "@mipmap/ic_launcher"
        manifestPlaceholders["appIconRounded"] = "@mipmap/ic_launcher_round"
        compileSdkPreview = "UpsideDownCake"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "- Debug"
            resValue("string", "app_name", "$appName Debug")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("apiTest") {
            dimension = "version"
            applicationIdSuffix = ".test"
            versionNameSuffix = "-test"
            val properties = Properties()
            properties.load(project.rootProject.file("local.properties").inputStream())

            val apiKey = properties.getProperty("API_KEY")
            buildConfigField("String", "BASE_URL", "\"https://sparky.pl-coding.com:8081/\"")
            buildConfigField("String", "API_KEY", apiKey)
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.navigation.compose)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.paging)
    implementation(libs.coil.kt.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.calendar.compose)
    ksp(libs.room.compiler)
    implementation(libs.signalr)
//    implementation(libs.java.websocket)

    coreLibraryDesugaring(libs.android.tools.desugar)

//    implementation(libs.gms.play.service.location)
    implementation(libs.lottie)


    implementation(libs.timber)

    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)


    //The bundle replaces multiple independent instances of dependencies using the same version with one instance to implement them all

    implementation(libs.bundles.activity)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.compose)
//    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okHttp)
//    implementation(libs.bundles.moshi)
    implementation(libs.bundles.lifecycle)

}