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
    api(project(Modules.Core.android))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.Ktx.core)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)
    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.core)

    testImplementation(project(path = Modules.Core.test, configuration = Core.configTestArtifacts))
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.core)
}