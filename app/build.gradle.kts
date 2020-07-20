plugins {
    //Plugins
    id(BuildPlugins.Android.application)
    id(BuildPlugins.Kotlin.android)
    id(BuildPlugins.Kotlin.androidExtensions)
    id(BuildPlugins.Kotlin.kapt)

    //Scripts
    id(ScriptPlugins.applicationConfig)
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
    implementation(Libraries.Android.constraint)
    implementation(Libraries.Android.appCompat)
    implementation(Libraries.Android.material)

    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Kotlin.ktxCore)
    implementation(Libraries.Kotlin.livedataKtx)
    implementation(Libraries.Kotlin.viewModelKtx)
    implementation(Libraries.Kotlin.ktxCore)
    implementation(Libraries.Kotlin.coroutines)

    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)

    implementation(Libraries.Koin.core)
    implementation(Libraries.Koin.viewModel)

    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.ktx)

    implementation(Libraries.Emoji.core)
    implementation(Libraries.Emoji.bundled)

    kapt(Libraries.Room.compiler)

    testImplementation(TestLibraries.Android.junit4)
    testImplementation(TestLibraries.Android.testCore)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.inline)

    androidTestImplementation(TestLibraries.Espresso.core)
    androidTestImplementation(TestLibraries.Espresso.accessibility)
    androidTestImplementation(TestLibraries.Espresso.contrib)
    androidTestImplementation(TestLibraries.Android.rules)
    androidTestImplementation(TestLibraries.Android.uiAutomator)
    androidTestImplementation(TestLibraries.Android.junitExt)
    androidTestImplementation(TestLibraries.Android.testRunner)
    androidTestImplementation(TestLibraries.Mockito.android)
    androidTestImplementation(TestLibraries.room)

    debugImplementation(TestLibraries.Espresso.idling)
    debugImplementation(DevLibraries.leakCanary)
}
