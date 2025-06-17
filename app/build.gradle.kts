import java.util.Properties

// Load local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-parcelize")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.movie_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movie_app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            val apiKey = localProperties.getProperty("TMDB_API_KEY", "")
            val accessToken = localProperties.getProperty("TMDB_ACCESS_TOKEN", "")
            buildConfigField("String", "TMDB_API_KEY", "\"$apiKey\"")
            buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$accessToken\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            val apiKey = localProperties.getProperty("TMDB_API_KEY", "")
            val accessToken = localProperties.getProperty("TMDB_ACCESS_TOKEN", "")
            buildConfigField("String", "TMDB_API_KEY", "\"$apiKey\"")
            buildConfigField("String", "TMDB_ACCESS_TOKEN", "\"$accessToken\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core-data"))
    implementation(project(":utils-extension"))


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // constraint layout
    implementation (libs.androidx.constraintlayout.compose)

    implementation(libs.jetbrains.compose.navigation)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    annotationProcessor( libs.androidx.room.compiler)
    // coil
    implementation(libs.coil.compose)

    // pager
    implementation(libs.compose.foundation)

    // datastore preference
    implementation(libs.datastore)
    implementation(libs.datastore.preferences)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)


    //ktor
    implementation(libs.bundles.ktor)

    // coil
    implementation(libs.bundles.coil)

    // media3
    implementation(libs.bundles.media3)

    // paging3
    implementation(libs.bundles.paging3)

    //datetime
    implementation(libs.kotlinx.datetime)

}