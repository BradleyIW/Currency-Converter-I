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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mutableMapOf(Pair("room.schemaLocation", "$projectDir/schemas"))
            }
        }
    }
}


dependencies {
    implementation(project(Modules.Core.android))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.Ktx.core)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.gsonConverter)
    implementation(Libraries.coroutines)
    implementation(Libraries.Koin.core)
    implementation(Libraries.Room.runtime)
    implementation(Libraries.Room.ktx)

    kapt(Libraries.Room.compiler)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    testImplementation(TestLibraries.Mockito.inline)

    androidTestImplementation(TestLibraries.Android.core)
    androidTestImplementation(TestLibraries.room)
    androidTestImplementation(TestLibraries.Android.junit)
    androidTestImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.Mockito.android)

    debugImplementation(project(Modules.Core.test))
}
