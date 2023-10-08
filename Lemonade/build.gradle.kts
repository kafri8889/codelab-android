buildscript {
    val kotlin_version by extra("1.9.0")
    val compose_version by extra("1.5.0")

    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0-beta06")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0-beta06" apply false
    id("com.android.library") version "8.2.0-beta06" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
}
tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
