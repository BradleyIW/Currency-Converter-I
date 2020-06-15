private object Dependencies {
    private const val kotlinVersion = "1.3.72"
    const val androidBuildTools = "com.android.tools.build:gradle:4.0.0"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

}

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
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