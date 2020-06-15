plugins {
    //Application plugins
    id(BuildPlugins.kotlin)
    id(BuildPlugins.javaLibrary)

    //Script plugins
    id(ScriptPlugins.pureKotlin)
}

tasks.register<Jar>(Core.configArtifactName) {
    dependsOn("testClasses")
    archiveBaseName.set("${project.name}-test")
    from(sourceSets["test"].output.classesDirs)
}

val testConfig = configurations.create(Core.configName) {
    extendsFrom(configurations["testCompile"])
}

artifacts {
    add(Core.configName, tasks.named<Jar>(Core.configArtifactName))
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    testImplementation(TestLibraries.testRunner)
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.mockito)
}