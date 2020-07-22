package scripts.application

import java.io.FileInputStream
import java.util.Properties

object BuildTypes {
    const val RELEASE = "release"
}
val signingProperties = Properties()

if (signingPropertiesExists()) {
    val signingPropertiesFile = rootProject.file("signing.properties")
    signingProperties.load(FileInputStream(signingPropertiesFile))
}

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

fun getPropertiesValue(key: String) =
    if (signingPropertiesExists()) {
        (signingProperties[key] as String).trim()
    } else " "

fun signingPropertiesExists() =
    rootProject.file("signing.properties").exists()

