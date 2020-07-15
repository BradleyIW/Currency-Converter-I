plugins {
    id(ScriptPlugins.infrastructure)
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath(BuildPlugins.Android.gradlePlugin)
        classpath(BuildPlugins.Kotlin.gradlePlugin)
        classpath(BuildPlugins.Jacoco.gradlePlugin)
        classpath(BuildPlugins.Detekt.gradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}