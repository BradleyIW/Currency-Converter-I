plugins {
    id(BuildPlugins.javaLibrary)
    id(BuildPlugins.Kotlin.plugin)
    id(ScriptPlugins.buildConfigKotlin)
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

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.Mockito.core)
    testImplementation(TestLibraries.coroutines)
}