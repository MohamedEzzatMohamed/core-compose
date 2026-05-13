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
                api(dependencyNotation = "org.jetbrains.compose.ui:ui-tooling-preview:1.10.3")
                api(dependencyNotation = "androidx.core:core-ktx:1.12.0")
                api(dependencyNotation = "androidx.activity:activity-compose:1.11.0")
                api(dependencyNotation = "androidx.compose.ui:ui-tooling-preview")


                val lifecycle = "2.9.3"
                api(dependencyNotation = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-common-java8:$lifecycle")
                api(dependencyNotation = "androidx.lifecycle:lifecycle-service:$lifecycle")


                // Network (Ktor)
                api(dependencyNotation = "io.ktor:ktor-client-android:3.3.0")
                api(dependencyNotation = "io.ktor:ktor-client-okhttp:3.3.0")


                // Coroutines
                api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

                // Koin
                val koin = "4.2.1"
                api(dependencyNotation = "io.insert-koin:koin-android:$koin")
                api(dependencyNotation = "io.insert-koin:koin-androidx-compose:$koin")

                //Firebase
                api(dependencyNotation = "com.google.firebase:firebase-common-ktx:21.0.0")
                api(dependencyNotation = "com.google.firebase:firebase-analytics")
                api(dependencyNotation = "com.google.firebase:firebase-crashlytics")

            }
        }

        commonMain {
            dependencies {
                // 1. Kotlin & Coroutines
                implementation("org.jetbrains.kotlin:kotlin-stdlib:2.3.21")
                api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
                api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

                // 2. Compose Core
                api(dependencyNotation = "org.jetbrains.compose.runtime:runtime:1.10.3")
                api(dependencyNotation = "org.jetbrains.compose.foundation:foundation:1.10.3")
                api(dependencyNotation = "org.jetbrains.compose.ui:ui:1.10.3")
                api(dependencyNotation = "org.jetbrains.compose.ui:ui-tooling-preview:1.10.3")
                api(dependencyNotation = "org.jetbrains.compose.material3:material3:1.9.0")
                implementation(dependencyNotation = "org.jetbrains.compose.components:components-resources:1.11.0")

                // 3. Lifecycle & Navigation
                api(dependencyNotation = "org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
                api(dependencyNotation = "org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
                api(dependencyNotation = "org.jetbrains.androidx.navigation:navigation-compose:2.9.0")

                // 4. Dependency Injection (Koin)
                val koinVersion = "4.2.1"
                api(dependencyNotation = "io.insert-koin:koin-core:$koinVersion")
                api(dependencyNotation = "io.insert-koin:koin-compose:$koinVersion")
                api(dependencyNotation = "io.insert-koin:koin-compose-viewmodel:$koinVersion")
                api(dependencyNotation = "io.insert-koin:koin-compose-viewmodel-navigation:$koinVersion")

                // 5. Networking (Ktor)
                val ktorVersion = "3.3.0"
                api(dependencyNotation = "io.ktor:ktor-client-core:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-client-auth:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-client-logging:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-client-content-negotiation:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-client-websockets:$ktorVersion")
                api(dependencyNotation = "io.ktor:ktor-network:$ktorVersion")

                // Ktor Monitor Logging
                api(dependencyNotation = "ro.cosminmihu.ktor:ktor-monitor-logging:1.13.0")

                // 6. Image Loading (Coil)
                val coilVersion = "3.0.4"
                api(dependencyNotation = "io.coil-kt.coil3:coil-compose:$coilVersion")
                api(dependencyNotation = "io.coil-kt.coil3:coil-network-ktor3:$coilVersion")

                // 7. Local Data (DataStore)
                api(dependencyNotation = "androidx.datastore:datastore:1.2.1")
                api(dependencyNotation = "androidx.datastore:datastore-preferences:1.2.1")

                // 8. Icons & UI Utilities
                val composeIcons = "1.1.1"
                api(dependencyNotation = "br.com.devsrsouza.compose.icons:tabler-icons:$composeIcons")
                api(dependencyNotation = "br.com.devsrsouza.compose.icons:feather:$composeIcons")
            }
        }


        iosMain {
            dependencies {
                // 1. Ktor Engine (IOS)
                api(dependencyNotation = "io.ktor:ktor-client-darwin:3.3.0")
            }
        }

        jvmMain.dependencies {
            // 1. Ktor Engine (Desktop)
            api(dependencyNotation = "io.ktor:ktor-client-cio:3.3.0")

            // 2. Coroutines Swing Extension
            api(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")

            // 3. Hardware / Serial Communication
            api(dependencyNotation = "com.fazecast:jSerialComm:[2.0.0,3.0.0)")
        }

        commonTest {
            dependencies {
                implementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-test:2.3.20")
            }
        }



        getByName("androidDeviceTest") {
            dependencies {
                implementation(dependencyNotation = "androidx.test:runner:1.7.0")
                implementation(dependencyNotation = "androidx.test:core:1.7.0")
                implementation(dependencyNotation = "androidx.test.ext:junit:1.3.0")
            }
        }
    }

}


dependencies {
    "androidMainApi"(platform(notation = "com.google.firebase:firebase-bom:33.1.0"))
}
