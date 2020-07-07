plugins {
    id(BuildPlugins.Android.library)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)
    id(BuildPlugins.Kotlin.kapt)
    id(ScriptPlugins.buildConfigLibrary)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = Instrumentation.testRunner
    }
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    implementation(TestLibraries.junit4)
    implementation(TestLibraries.Mockito.android)
    implementation(TestLibraries.coroutines)
    implementation(TestLibraries.espresso)
    implementation(TestLibraries.testRules)
    implementation(TestLibraries.uiAutomator)
}