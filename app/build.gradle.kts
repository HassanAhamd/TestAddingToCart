plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.saveArgs)
    kotlin("plugin.serialization") version "1.9.23"

}

android {
    namespace = "com.example.shoppingtest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.shoppingtest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName","EStoreTestAppApp_Vcode_${versionCode}_Vname_${versionName}")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



    //Text and Sizeing Libs
    implementation (libs.sdp.android)

    //hilt
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.hilt.android.compiler)



    //Glide
    implementation (libs.bumb.glide)

    // Page Indicator
    implementation(libs.dotsindicator)
    //Gson to Json
    implementation(libs.gson)

    //lifecycle
//    implementation (libs.androidx.lifecycle.livedata.ktx)
//    implementation (libs.androidx.lifecycle.process)

    //Kotlin runtime Worker
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.kotlinx.serialization.json)


    //Viewmodel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")


   // implementation ("com.github.razaghimahdi:Android-Loading-Dots:1.3.2")

}