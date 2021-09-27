plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraint)
    implementation(AndroidX.lifecycleLiveData)
    implementation(AndroidX.lifecycleViewModel)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConvertor)

    implementation(OKhttp.okHttpInterceptor)
    implementation(OKhttp.okHttpInterceptor)

    implementation(Navigation.navigationKtx)
    implementation(Navigation.navigationFrag)

    implementation(Google.material)
    implementation(Lottie.lottie)
    implementation(Glide.glide)
    kapt(Glide.compiler)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    testImplementation(UnitTest.jupiter_api)
    testRuntimeOnly(UnitTest.jupiter_engine)
    testImplementation (UnitTest.mock_web_server)
}