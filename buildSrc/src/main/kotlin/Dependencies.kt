const val appId = "bradley.wilson.rxcurrency"
private const val kotlinVersion = "1.3.72"

private object SharedVersions {
    const val coroutines = "1.3.7"
    const val room = "2.2.5"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object BuildPlugins {
    object Android {
        const val application = "com.android.application"
    }

    object Kotlin {
        const val android = "kotlin-android"
        const val androidExtensions = "kotlin-android-extensions"
        const val kapt = "kotlin-kapt"
    }
}

object Instrumentation {
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Libraries {
    object Retrofit {
        private object Versions {
            const val retrofit = "2.6.2"
        }

        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Kotlin {
        private object Versions {
            const val lifecycle = "2.2.0"
            const val ktx = "1.3.0"

        }

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${SharedVersions.coroutines}"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
        const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    }

    object Koin {
        private object Versions {
            const val koin = "2.1.6"
        }
        const val core = "org.koin:koin-android:${Versions.koin}"
        const val viewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:$${SharedVersions.room}"
        const val compiler = "androidx.room:room-compiler:${SharedVersions.room}"
        const val ktx = "androidx.room:room-ktx:${SharedVersions.room}"
    }

    object Emoji {
        private object Versions {
            const val emoji = "1.1.0"
        }

        const val core = "androidx.emoji:emoji:${Versions.emoji}"
        const val bundled = "com.android.support:support-emoji-bundled:${Versions.emoji}"
    }

    object Android {
        private object Versions {
            const val jetpack = "1.1.0"
            const val constraintLayout = "1.1.3"
            const val material = "1.1.0"
        }

        const val material = "com.google.android.material:material:${Versions.material}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    }
}

object TestLibraries {

    object Espresso {
        private object Versions {
            const val espresso = "3.2.0"
        }

        const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val idling = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"

        //Androidx isn't support yet. Please see:
        //https://github.com/android/android-test/issues/492
        const val accessibility = "com.android.support.test.espresso:espresso-accessibility:${Versions.espresso}"
    }

    object Mockito {
        private object Versions {
            const val mockito = "3.3.0"
        }

        const val inline = "org.mockito:mockito-inline:${Versions.mockito}"
        const val android = "org.mockito:mockito-android:${Versions.mockito}"
    }

    object Junit {

    }

    object Android {
        private object Versions {
            const val testRules = "1.1.0"
            const val testExtensions = "1.1.1"
            const val testRunner = "1.1.0"
            const val junit4 = "4.13"
            const val uiAutomator = "2.2.0"
            const val testCore = "2.1.0"
        }

        const val core = "androidx.test:core:1.2.0"
        const val junit4 = "junit:junit:${Versions.junit4}"
        const val junitExt = "androidx.test.ext:junit:${Versions.testExtensions}"
        const val rules = "androidx.test:rules:${Versions.testRules}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
        const val testCore = "androidx.arch.core:core-testing:${Versions.testCore}"
    }

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${SharedVersions.coroutines}"
    const val room = "androidx.room:room-testing:${SharedVersions.room}"
}

object ScriptPlugins {
    const val applicationConfig = "scripts.application.config"
    const val infrastructure = "scripts.infrastructure"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "2.3"
        const val fragment = "1.2.5"
    }

    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}