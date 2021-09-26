// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.hiltAndroid)
        classpath(View.navigationSafeArgs)
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.0.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}