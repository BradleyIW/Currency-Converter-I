private object Dependencies {
    const val androidBuildTools = "com.android.tools.build:gradle:4.0.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
}

plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
    google()
    mavenCentral()
}

dependencies {
    implementation(Dependencies.androidBuildTools)
    implementation(Dependencies.kotlinGradlePlugin)
}