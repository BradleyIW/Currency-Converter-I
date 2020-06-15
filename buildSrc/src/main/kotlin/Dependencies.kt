const val appId = "bradley.wilson.rxcurrency"
private const val kotlinVersion = "1.3.72"

private object Versions {
    const val jetpack = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val ktx = "1.3.0"
    const val retrofit = "2.6.2"
    const val coroutines = "1.3.7"
    const val buildToolsVersion = "4.0.0"
    const val junit4 = "4.13"
    const val mockito = "2.7.22"
    const val robolectric = "4.3.1"
    const val assertJ = "3.16.1"
    const val testRunner = "1.1.0"
    const val espresso = "3.2.0"
    const val testExtensions = "1.1.1"
    const val testRules = "1.1.0"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlin = "kotlin"
    const val javaLibrary = "java-library"
}

object Libraries {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
    const val retrofitCore = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object TestLibraries {
    const val junit4 = "junit:junit:${Versions.junit4}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtensions}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}

object ScriptPlugins {
    const val buildConfigApplication = "scripts.application.config"
    const val buildConfigLibrary = "scripts.library.config"
    const val pureKotlin = "scripts.kotlin.config"
}

object Modules {
    const val network = ":network"
    const val core = ":core"
}