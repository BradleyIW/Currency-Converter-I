package scripts

tasks.register("checkQuality") {
    group = "Continuous improvement"
    description = "This task runs code quality and reporting tasks to run and report quality analytics for improvement"
    dependsOn("compileApp", "runUnitTests", "codeCoverage", "staticCodeAnalysis")
}

tasks.register("staticCodeAnalysis") {
    group = "Quality"
    description = "This performs a static code analysis on the codebase and reports any particular issues found"
    dependsOn("detektAll")
}

tasks.register("codeCoverage") {
    group = "Reporting"
    description = "Infrastructure task for running codeCoverage"
    dependsOn(":app:jacocoReport")
}

tasks.register("compileApp") {
    description = "compiles the codebase."
    dependsOn(":app:compileDebugSources")
}

tasks.register("runUnitTests") {
    group = "Testing"
    description = "compiles the codebase."
    dependsOn(":app:testDebugUnitTest")
}


