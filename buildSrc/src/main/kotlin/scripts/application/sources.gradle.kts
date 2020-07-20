package scripts.application

plugins {
    id("scripts.core.android") apply false
}

android {
    sourceSets { map { it.java.srcDir("src/${it.name}/kotlin") } }
}