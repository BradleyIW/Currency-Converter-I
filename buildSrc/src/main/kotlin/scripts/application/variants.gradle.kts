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
        }

        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}