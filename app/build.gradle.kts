plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.example.cocktailsdbapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cocktailsdbapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.androidx.app.compat)
    implementation(libs.material)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Glide
    implementation(libs.glide)

    // Room
    implementation (libs.androidx.room.ktx)
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)

    implementation(libs.expandableTextView)

}
