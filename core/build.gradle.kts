plugins {
    //Application plugins
    id(BuildPlugins.Kotlin.plugin)
    id(BuildPlugins.javaLibrary)

    //Script plugins
    id(ScriptPlugins.pureKotlin)
}

tasks.register<Jar>(Core.configArtifactName) {
    dependsOn("testClasses")
    archiveBaseName.set("${project.name}-test")
    from(sourceSets["test"].output.classesDirs)
}

configurations.create(Core.configTestArtifacts) {
    extendsFrom(configurations["testCompile"])
}

artifacts {
    add(Core.configTestArtifacts, tasks.named<Jar>(Core.configArtifactName))
}

dependencies {
    implementation(Libraries.kotlinStdLib)
    testImplementation(TestLibraries.testRunner)
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.Mockito.core)
}