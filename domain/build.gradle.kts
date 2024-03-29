plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.nestifff.words.domain"
    compileSdk = AppBuildConfig.CompileSDK

    defaultConfig {
        minSdk = AppBuildConfig.MinSDK
        targetSdk = AppBuildConfig.TargetSDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
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
    testImplementation(Dependencies.Common.Test.jUnit)
    androidTestImplementation(Dependencies.Common.Test.androidJUnit)
    androidTestImplementation(Dependencies.Common.Test.espresso)

    implementation(Dependencies.Common.Dagger.dagger)
    kapt(Dependencies.Common.Dagger.compiler)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    detektPlugins(Dependencies.Common.DetektPlugins.composeRules)
}