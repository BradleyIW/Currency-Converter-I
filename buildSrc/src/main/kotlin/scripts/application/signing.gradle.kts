package scripts.application

import java.io.FileInputStream
import java.util.Properties

object BuildTypes {
    const val RELEASE = "release"
}

val signingPropertiesFile = rootProject.file("signing.properties")
val signingProperties = Properties()
signingProperties.load(FileInputStream(signingPropertiesFile))

plugins {
    id("com.android.application") apply false
}

android {
    signingConfigs {
        create(BuildTypes.RELEASE) {
            storeFile = rootProject.file(getPropertiesValue("storeFile"))
            storePassword = getPropertiesValue("storePassword")
            keyAlias = getPropertiesValue("keyAlias")
            keyPassword = getPropertiesValue("keyPassword")
        }
    }
}

fun getPropertiesValue(key: String) = (signingProperties[key] as String).trim()
