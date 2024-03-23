// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
}