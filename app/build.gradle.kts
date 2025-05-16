import java.util.Properties




plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.firebase.crashlytics)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.pirrera.tvshelf"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }
    defaultConfig {
        applicationId = "com.pirrera.tvshelf"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "FIREBASE_API_KEY", "\"${localProperties["FIREBASE_API_KEY"]}\"")
        buildConfigField("String", "FIREBASE_APP_ID", "\"${localProperties["FIREBASE_APP_ID"]}\"")
        buildConfigField("String", "FIREBASE_PROJECT_ID", "\"${localProperties["FIREBASE_PROJECT_ID"]}\"")
        buildConfigField("String", "FIREBASE_DATABASE_URL", "\"${localProperties["FIREBASE_DATABASE_URL"]}\"")
        buildConfigField("String", "FIREBASE_STORAGE_BUCKET", "\"${localProperties["FIREBASE_STORAGE_BUCKET"]}\"")
        buildConfigField("String", "TVSHELF_API_KEY", "\"${localProperties["TVSHELF_API_KEY"]}\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.messaging.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.auth)

    implementation("com.google.firebase:firebase-bom:32.0.0")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.1")
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    //implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}

kapt {
    correctErrorTypes = true
}