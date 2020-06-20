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
    const val mockito = "3.1.0"
    const val robolectric = "4.3.1"
    const val assertJ = "3.16.1"
    const val testRunner = "1.1.0"
    const val espresso = "3.2.0"
    const val testExtensions = "1.1.1"
    const val testRules = "1.1.0"
    const val lifecycle = "2.2.0"
    const val koin = "2.1.6"
    const val room = "2.2.5"
    const val gson = "2.8.6"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object BuildPlugins {
    object Android {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    }

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val android = "kotlin-android"
        const val androidExtensions = "kotlin-android-extensions"
        const val plugin = "kotlin"
        const val kapt = "kotlin-kapt"
    }
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

    object Lifecycle {
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    }

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.jetpack}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object TestLibraries {
    object Mockito {
        const val core = "org.mockito:mockito-core:${Versions.mockito}"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtensions}"
    const val testRules = "androidx.test:rules:${Versions.testRules}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val room = "androidx.room:room-testing:${Versions.room}"
}

object ScriptPlugins {
    const val buildConfigApplication = "scripts.application.config"
    const val buildConfigLibrary = "scripts.library.config"
}

object Modules {
    object Core {
        const val network = ":core-network"
        const val android = ":core-android"
        const val storage = ":core-storage"
        const val test = ":core-test"
    }

    object Feature {
        const val currency = ":feature-currency-feed"
    }
}