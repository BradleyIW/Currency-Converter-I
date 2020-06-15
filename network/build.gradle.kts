plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)

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
    api(project(Modules.core))
    testImplementation(project(Modules.core, Core.configName))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)
    implementation(Libraries.retrofitCore)
    implementation(Libraries.retrofitConverter)
    implementation(Libraries.coroutines)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.mockito)
    testImplementation(TestLibraries.mockitoInline)
}
