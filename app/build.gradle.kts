plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.nestifff.learnwords"
    compileSdk = AppBuildConfig.CompileSDK

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
    implementation(Dependencies.App.Lifecycle.lifecycleKtx)
    implementation(Dependencies.App.Lifecycle.activityCompose)
    implementation(Dependencies.App.Compose.ui)
    implementation(Dependencies.App.Compose.tooling)
    implementation(Dependencies.App.Compose.material)
    testImplementation(Dependencies.Common.Test.jUnit)
    androidTestImplementation(Dependencies.Common.Test.androidJUnit)
    androidTestImplementation(Dependencies.Common.Test.espresso)
    androidTestImplementation(Dependencies.App.Compose.uiTest)
    debugImplementation(Dependencies.App.Compose.tooling)
    debugImplementation(Dependencies.App.Compose.manifestTest)
    debugImplementation(Dependencies.App.Accompanist.uiController)
}