plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")}

android {
    namespace = "com.nestifff.learnwords"
    compileSdk = AppBuildConfig.CompileSDK

    signingConfigs {
        getByName("debug") {
            storeFile = file("words.jks")
            storePassword = "wordswords"
            keyAlias = "words"
            keyPassword = "wordswords"
        }
        create("release") {
            storeFile = file("words.jks")
            storePassword = "wordswords"
            keyAlias = "words"
            keyPassword = "wordswords"
        }
    }

    defaultConfig {
        applicationId = AppBuildConfig.PackageName
        minSdk = AppBuildConfig.MinSDK
        targetSdk = AppBuildConfig.TargetSDK
        versionCode = AppBuildConfig.VersionCode
        versionName = AppBuildConfig.VersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isDebuggable = false

            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependencies.App.Core.corKtx)
    implementation(Dependencies.App.Core.activityCompose)

    implementation(Dependencies.App.Lifecycle.lifecycleKtx)
    implementation(Dependencies.App.Lifecycle.viewModelCompose)

    implementation(Dependencies.App.Compose.ui)
    implementation(Dependencies.App.Compose.tooling)
    implementation(Dependencies.App.Compose.material)

    implementation(Dependencies.App.Accompanist.animatedNavigation)
    implementation(Dependencies.App.Accompanist.uiController)

    testImplementation(Dependencies.Common.Test.jUnit)
    androidTestImplementation(Dependencies.Common.Test.androidJUnit)
    androidTestImplementation(Dependencies.Common.Test.espresso)

    androidTestImplementation(Dependencies.App.Compose.uiTest)
    debugImplementation(Dependencies.App.Compose.toolingTest)
    debugImplementation(Dependencies.App.Compose.manifestTest)

    implementation(Dependencies.Common.Dagger.dagger)
    kapt(Dependencies.Common.Dagger.compiler)
}