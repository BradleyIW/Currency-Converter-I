plugins {
    //Application plugins
    id(BuildPlugins.kotlin)
    id(BuildPlugins.javaLibrary)

    //Script plugins
    id(ScriptPlugins.pureKotlin)
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
