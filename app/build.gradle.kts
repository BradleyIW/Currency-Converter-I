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

    sourceSets { map { it.java.srcDir("src/${it.name}/kotlin") } }
}

dependencies {
    implementation(Libraries.Kotlin.ktxCore)
    implementation(Libraries.Android.constraint)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)
    implementation(Libraries.Kotlin.coroutines)
    implementation(Libraries.Koin.core)
    implementation(Libraries.Koin.viewModel)
    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.ktx)
    implementation(Libraries.Emoji.core)
    implementation(Libraries.Emoji.bundled)
    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Android.appCompat)
    implementation(Libraries.Kotlin.ktxCore)
    implementation(Libraries.Android.material)
    implementation(Libraries.Kotlin.livedataKtx)
    implementation(Libraries.Kotlin.viewModelKtx)
    implementation(TestLibraries.Espresso.idling)

    kapt(Libraries.Room.compiler)

    testImplementation(TestLibraries.Android.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.inline)
    testImplementation(TestLibraries.Android.testCore)

    androidTestImplementation(TestLibraries.Espresso.core)
    androidTestImplementation(TestLibraries.Espresso.accessibility)
    androidTestImplementation(TestLibraries.Espresso.contrib)
    androidTestImplementation(TestLibraries.room)
    androidTestImplementation(TestLibraries.Android.rules)
    androidTestImplementation(TestLibraries.Android.uiAutomator)
    androidTestImplementation(TestLibraries.Android.junitExt)
    androidTestImplementation(TestLibraries.Android.testRunner)
    androidTestImplementation(TestLibraries.Mockito.android)

    debugImplementation(DevLibraries.leakCanary)
    debugImplementation(DevLibraries.fragmentTesting)
}
