plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.fitcheck"
    compileSdk = 37 // Đã nâng cấp lên 37 theo yêu cầu của androidx.core

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.fitcheck"
        minSdk = 24
        targetSdk = 37 // Đồng bộ targetSdk lên 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Sửa lại cú pháp minify cho chuẩn ổn định
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // 1. Material Design 3 (Giao diện bo góc, UI hiện đại)
    implementation("com.google.android.material:material:1.11.0")

    // 2. Room Database (Cơ sở dữ liệu cục bộ cho Tuần 3)
    val room_version = "2.8.5"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // 3. Lifecycle (ViewModel & LiveData cho kiến trúc MVVM)
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
}