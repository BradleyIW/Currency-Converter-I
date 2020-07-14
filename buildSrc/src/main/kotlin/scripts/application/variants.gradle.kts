package scripts.application

object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"
}

plugins {
    id("scripts.core.android") apply false
}

android {
    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isTestCoverageEnabled = true
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
}