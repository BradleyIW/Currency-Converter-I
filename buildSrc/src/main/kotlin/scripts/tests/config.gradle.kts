package scripts.kotlin

plugins {
    id("java-library") apply false
    id("kotlin") apply false
}

sourceSets { map { it.java.srcDir("src/${it.name}/kotlin") } }
