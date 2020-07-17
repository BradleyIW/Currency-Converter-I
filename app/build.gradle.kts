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
    implementation(Libraries.Ktx.core)
    implementation(Libraries.constraint)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)
    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.core)
    implementation(Libraries.Koin.viewModel)
    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.ktx)
    implementation(Libraries.emoji)
    implementation(Libraries.emojiBundled)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.Ktx.core)
    implementation(Libraries.material)
    implementation(Libraries.livedataKtx)
    implementation(Libraries.viewModelKtx)

    kapt(Libraries.Room.compiler)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.inline)
    testImplementation(TestLibraries.testCore)

    androidTestImplementation(TestLibraries.Espresso.core)
    androidTestImplementation(TestLibraries.Espresso.accessibility)
    androidTestImplementation(TestLibraries.Espresso.contrib)
    androidTestImplementation(TestLibraries.Android.core)
    androidTestImplementation(TestLibraries.Android.junit)
    androidTestImplementation(TestLibraries.room)
    androidTestImplementation(TestLibraries.testRules)
    androidTestImplementation(TestLibraries.uiAutomator)
    androidTestImplementation(TestLibraries.testExtJunit)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.koinTest)
    androidTestImplementation(TestLibraries.Mockito.android)


    debugImplementation(DevLibraries.leakCanary)
    debugImplementation(DevLibraries.fragmentTesting)
}
