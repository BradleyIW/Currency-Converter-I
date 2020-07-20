# Currency-Converter 

A simple currency converter application

# Gradle Tasks
 - ```./gradlew checkQuality```: Works as an internal CI task to compile the app, run unit tests, run static code analysis and generate test code coverage report 
 - ```./gradlew runUnitTests```: Runs all Unit Tests.
 - ```./gradlew codeCoverage```: Generates a report for test code coverage
 - ```./gradlew staticCodeAnalysis```: Runs static code analysis on the codebase

# Signing 
Signing config requires a local signing.properties file + keystore. You'll need to store the keystore locally 
(outside of the project to avoid security concerns around reverse engineering) on your machine and update the storeFile property 
with the new location to properly build a signed release apk.