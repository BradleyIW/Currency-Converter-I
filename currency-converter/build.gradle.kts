plugins {
    //Application plugins
    id(BuildPlugins.Android.library)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)

    //Script plugins
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
    api(project(Modules.Core.core))
    testImplementation(project(Modules.Core.core, Core.configTestArtifacts))

    implementation(project(Modules.Core.network))
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)
    implementation(Libraries.coroutines)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.core)
    testImplementation(TestLibraries.Mockito.inline)
}
