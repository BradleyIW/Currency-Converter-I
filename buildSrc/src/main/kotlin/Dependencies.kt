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
    const val mockito = "3.3.0"
    const val robolectric = "4.3.1"
    const val testRunner = "1.1.0"
    const val material = "1.1.0"
    const val emoji = "1.1.0"
    const val espresso = "3.2.0"
    const val testExtensions = "1.1.1"
    const val testRules = "1.1.0"
    const val lifecycle = "2.2.0"
    const val koin = "2.1.6"
    const val room = "2.2.5"
    const val uiAutomator = "2.2.0"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object BuildPlugins {
    object Android {
        const val application = "com.android.application"
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val android = "kotlin-android"
        const val androidExtensions = "kotlin-android-extensions"
        const val kapt = "kotlin-kapt"
    }

    object Jacoco {
        private object Versions {
            const val jacoco = "0.8.5"
        }
        const val android = "jacoco-android"
        const val gradlePlugin = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
    }

    object Detekt {
        private object Versions {
            const val detekt = "1.9.1"
        }

        const val gradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    }
}

object Instrumentation {
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Libraries {
    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Ktx {
        const val core = "androidx.core:core-ktx:${Versions.ktx}"
    }

    object Koin {
        const val core = "org.koin:koin-android:${Versions.koin}"
        const val viewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:$${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
    }


    const val emoji = "androidx.emoji:emoji:${Versions.emoji}"
    const val emojiBundled = "com.android.support:support-emoji-bundled:${Versions.emoji}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object TestLibraries {

    object Espresso {
        const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"

        //Androidx isn't support yet. Please see:
        //https://github.com/android/android-test/issues/492
        const val accessibility = "com.android.support.test.espresso:espresso-accessibility:${Versions.espresso}"
    }

    object Mockito {
        const val inline = "org.mockito:mockito-inline:${Versions.mockito}"
        const val android = "org.mockito:mockito-android:${Versions.mockito}"
    }

    object Android {
        const val core = "androidx.test:core:1.2.0"
        const val junit = "androidx.test.ext:junit:1.1.0"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtensions}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
    const val room = "androidx.room:room-testing:${Versions.room}"
}

object ScriptPlugins {
    const val buildConfigApplication = "scripts.application.config"
    const val detekt = "scripts.detekt"
    const val jacoco = "scripts.jacoco"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "2.3"
        const val fragment = "1.2.5"
    }

    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}