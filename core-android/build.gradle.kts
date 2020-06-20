plugins {
    id(BuildPlugins.Android.library)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)
    id(ScriptPlugins.buildConfigLibrary)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = TestLibraries.testRunner
    }
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutines)
    implementation(Libraries.appCompat)
    implementation(Libraries.Ktx.core)
    implementation(Libraries.Lifecycle.runtime)
    implementation(Libraries.Lifecycle.extensions)

    testImplementation(TestLibraries.testRunner)
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.Mockito.core)
}