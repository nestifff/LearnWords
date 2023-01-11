object Dependencies {
    const val kotlinVersion = "1.7.20"
    const val composeVersion = "1.3.1"
    const val composeCompilerVersion = "1.3.2"

    object Common {

        object Test {
            const val jUnit = "junit:junit:4.13.2"
            const val androidJUnit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }


    object App {

        object Core {
            const val corKtx = "androidx.core:core-ktx:1.9.0"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val material = "androidx.compose.material:material:$composeVersion"
            const val tooling = "androidx.compose.ui:ui-tooling-preview:$composeVersion"

            const val manifestTest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        }

        object Accompanist {
            private const val accompanistVersion = "0.27.0"

            const val uiController = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
        }

        object Lifecycle {
            const val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
            const val activityCompose = "androidx.activity:activity-compose:1.6.0"
        }

        object Material {
            private const val material3Version = "1.0.1"

            const val material3 = "androidx.compose.material3:material3:$material3Version"
            const val materialWindowSize = "androidx.compose.material3:material3-window-size-class:$material3Version"
        }
    }

    object Data {

        object Json {
            const val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0"
            const val kotlinxSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
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
