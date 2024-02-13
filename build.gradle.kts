// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt.android.plugin) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
}


buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        // Add the dependency for the Google services Gradle plugin
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}