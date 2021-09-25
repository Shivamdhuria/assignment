plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
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
//    implementation(AndroidX.lifecycleVmKtx)
//    implementation(AndroidX.lifecycleRuntime)

    implementation(Google.material)
    implementation(View.constraint)

    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConvertor)

    implementation(OKhttp.okHttpInterceptor)
    implementation(OKhttp.okHttpInterceptor)

    implementation(View.navigationKtx)
    implementation(View.navigationFrag)
//    implementation(View.navigationSafeArgs)

    implementation(Lottie.lottie)
    implementation(Glide.glide)
    kapt(Glide.compiler)

    implementation(Hilt.android)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    kapt(Hilt.compiler)

    androidTestImplementation(Junit.junit4)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation(HiltTest.hiltAndroidTesting)
//    kaptAndroidTest(Hilt.compiler)
}