plugins {
    //Application plugins
    id(BuildPlugins.Kotlin.plugin)
    id(BuildPlugins.javaLibrary)

    //Script plugins
    id(ScriptPlugins.pureKotlin)
}

dependencies {
    api(project(Modules.Core.core))
    testImplementation(project(Modules.Core.core, Core.configTestArtifacts))

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
