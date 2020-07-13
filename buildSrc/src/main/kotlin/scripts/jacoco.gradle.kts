package scripts

import com.android.build.gradle.internal.tasks.factory.dependsOn

subprojects {
    apply(plugin = "jacoco")

    task<JacocoReport>("mergedJacocoReport") {
        group = "Reporting"
        description = "Generate Jacoco coverage reports on the debug build."
        dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")
        reports {
            xml.isEnabled = true
            html.isEnabled = true
            html.destination = file("${buildDir}/jacoco/html")
        }

        classDirectories.setFrom(
            fileTree(project.buildDir) {
                include(
                    "**/classes/**/main/**",
                    "**/intermediates/classes/debug/**",
                    "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
                    "**/tmp/kotlin-classes/debug/**"
                )
                exclude(
                    "**/R.class",
                    "**/R\$*.class",
                    "**/BuildConfig.*",
                    "**/Manifest*.*",
                    "**/Manifest$*.class",
                    "**/*Test*.*",
                    "**/Injector.*",
                    "**/*Adapter*.*",
                    "**/*Callback*.*",
                    "android/**/*.*",
                    "**/*Ext*.*",
                    "**/di/*.*",
                    "**/BaseTextWatcher.*",
                    "**/*Database.*",
                    "**/*Response.*",
                    "**/*Application.*",
                    "**/*Entity.*",
                    "**/*Error*.*",
                    "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
                    "**/*\$inlined$*.*" // Kotlin specific, Jacoco can not handle several "$" in class name.
                )
            }
        )

        sourceDirectories.setFrom(
            fileTree(project.projectDir) {
                include(
                    "src/main/java/**",
                    "src/main/kotlin/**",
                    "src/debug/java/**",
                    "src/debug/kotlin/**"
                )
            }
        )

        executionData.setFrom(
            fileTree(project.buildDir) {
                include(
                    "**/*.exec", "**/*.ec"
                )
            }
        )
    }
}