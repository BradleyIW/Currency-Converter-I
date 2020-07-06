plugins {
    id(BuildPlugins.Android.application)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)
    id(ScriptPlugins.buildConfigApplication)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        applicationId = appId
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = Instrumentation.testRunner
    }
}

dependencies {
    api(project(Modules.Core.android))
    implementation(project(Modules.Core.network))
    implementation(project(Modules.Core.storage))
    implementation(project(Modules.Feature.currency))

    implementation(Libraries.emoji)
    implementation(Libraries.emojiBundled)
    implementation(Libraries.Koin.core)
    implementation(Libraries.Koin.viewModel)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.Ktx.core)
    implementation(Libraries.constraint)
    implementation(Libraries.material)

    testImplementation(TestLibraries.junit4)
}
