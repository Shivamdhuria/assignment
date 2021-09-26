object Build {
    private const val androidBuildToolsVersion = "7.1.0-alpha03"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    const val jUnit = "de.mannodermaus.gradle.plugins:android-junit5:${Junit.junit5}"
}