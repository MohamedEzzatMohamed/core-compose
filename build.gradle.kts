plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {

    // Target declarations - add or remove as needed below. These define
    // which platforms this KMP module supports.
    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
    androidLibrary {
        namespace = "com.shared.core"
        //noinspection GradleDependency
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {
        }

        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    // For iOS targets, this is also where you should
    // configure native binary output. For more information, see:
    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks

    // A step-by-step guide on how to include this library in an XCode
    // project can be found here:
    // https://developer.android.com/kotlin/multiplatform/migrate
    val xcfName = "coreKit"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    jvm()

    sourceSets {

        androidMain {
            dependencies {

                // Android Basics & Compose
                api(compose.preview)
                api("androidx.core:core-ktx:1.12.0")
                api("androidx.activity:activity-compose:1.11.0")
                api("androidx.compose.ui:ui-tooling-preview")


                val lifecycle = "2.9.3"
                api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
                api("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
                api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
                api("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
                api("androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")
                api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle")
                api("androidx.lifecycle:lifecycle-common-java8:$lifecycle")
                api("androidx.lifecycle:lifecycle-service:$lifecycle")


                // Network (Ktor)
                api("io.ktor:ktor-client-android:3.3.0")
                api("io.ktor:ktor-client-okhttp:3.3.0")


                // Coroutines
                api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

                // Koin
                val koin = "3.5.3"
                api("io.insert-koin:koin-android:$koin")
                api("io.insert-koin:koin-androidx-compose:$koin")

                //Firebase
                api("com.google.firebase:firebase-common-ktx:21.0.0")
                api("com.google.firebase:firebase-analytics")
                api("com.google.firebase:firebase-crashlytics")

            }
        }

        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:2.3.20")

                //Lifecycle
                api("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
                api("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

                //Navigation
                api("org.jetbrains.androidx.navigation:navigation-compose:2.9.0")

                //Serialization
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

                //Koin
                val koinVersion = "4.1.1"
                api("io.insert-koin:koin-compose:$koinVersion")
                api("io.insert-koin:koin-compose-viewmodel:$koinVersion")
                api("io.insert-koin:koin-compose-viewmodel-navigation:$koinVersion")

                //Kotor
                val ktorVersion = "3.3.0"
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-auth:$ktorVersion")
                api("io.ktor:ktor-client-logging:$ktorVersion")
                api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                api("io.ktor:ktor-client-websockets:$ktorVersion")
                api("io.ktor:ktor-network:${ktorVersion}")


                //Coroutines
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")


                //Data Store
                api("androidx.datastore:datastore:1.2.1")
                api("androidx.datastore:datastore-preferences:1.2.1")


                //Coil
                val coil = "3.0.4"
                api("io.coil-kt.coil3:coil-compose:$coil")
                api("io.coil-kt.coil3:coil-network-ktor3:$coil")

                val composeIcons = "1.1.1"
                api("br.com.devsrsouza.compose.icons:tabler-icons:$composeIcons")
                api("br.com.devsrsouza.compose.icons:feather:$composeIcons")

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)
                api(compose.components.uiToolingPreview)

                implementation(compose.components.resources)
            }
        }


        iosMain {
            dependencies {
                api("io.ktor:ktor-client-darwin:3.3.0")
            }
        }

        jvmMain.dependencies {
            api("io.ktor:ktor-client-cio:3.3.0")

            api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")

            api("com.fazecast:jSerialComm:[2.0.0,3.0.0)")
        }

        commonTest {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:2.3.20")
            }
        }



        getByName("androidDeviceTest") {
            dependencies {
                implementation("androidx.test:runner:1.7.0")
                implementation("androidx.test:core:1.7.0")
                implementation("androidx.test.ext:junit:1.3.0")
            }
        }
    }

}


dependencies {
    "androidMainApi"(platform("com.google.firebase:firebase-bom:33.1.0"))
}
