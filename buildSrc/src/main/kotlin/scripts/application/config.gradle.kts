package scripts.application

object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

plugins {
    id("scripts.application.android")
}

android {
    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = false
            applicationIdSuffix = ".${BuildTypes.DEBUG}"
            isDebuggable = true
        }

        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets { map { it.java.srcDir("src/${it.name}/kotlin") } }
}