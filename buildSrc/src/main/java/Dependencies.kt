object Dependencies {

    object Project {
        const val app = ":app"
        const val data = ":data"
        const val domain = ":domain"
    }

    object Gradle {
        const val gradleBuild = "com.android.tools.build:gradle:${Versions.gradle}"
        const val gradleKotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val composeDetekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.19.0"
    }

    object Common {

        object Test {
            const val jUnit = "junit:junit:4.13.2"
            const val androidJUnit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }

        object Dagger {
            const val daggerVersion = "2.44.2"
            const val dagger = "com.google.dagger:dagger:$daggerVersion"
            const val compiler = "com.google.dagger:dagger-compiler:$daggerVersion"
        }

        object DetektPlugins {
            const val composeRules = "ru.kode:detekt-rules-compose:1.0.1"
        }
    }


    object App {

        object Core {
            const val corKtx = "androidx.core:core-ktx:1.9.0"
            const val activityCompose = "androidx.activity:activity-compose:1.6.0"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"

            const val toolingTest = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val manifestTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        }

        object Accompanist {
            private const val accompanistVersion = "0.27.0"

            const val uiController =
                "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
            const val animatedNavigation =
                "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
        }

        object Lifecycle {
            const val lifecycleVersion = "2.5.1"
            const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
        }

        object Material {
            private const val material3Version = "1.0.1"

            const val material3 = "androidx.compose.material3:material3:$material3Version"
            const val materialWindowSize =
                "androidx.compose.material3:material3-window-size-class:$material3Version"
        }

        object Collections {
            const val immutableCollections =
                "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5"
        }

    }

    object Data {

        object Coroutines {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
        }

        object Json {
            const val kotlinxSerialization =
                "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0"
            const val kotlinxSerializationConverter =
                "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        }

        object DataStore {
            const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
        }

        object Retrofit {
            private const val retrofitVersion = "2.9.0"
            const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
            const val okhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
        }

        object Room {
            private const val roomVersion = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$roomVersion"
            const val compiler = "androidx.room:room-compiler:$roomVersion"
            const val ktx = "androidx.room:room-ktx:$roomVersion"
        }
    }
}
