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
        classpath(Navigation.navigationSafeArgs)
        classpath(Build.jUnit)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}