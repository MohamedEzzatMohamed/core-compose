plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.shared.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // Android Core & Lifecycle
    api(libs.androidx.core.ktx)
    api(libs.androidx.activity.compose)

    val lifecycleVersion = "2.8.7"
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    api("androidx.lifecycle:lifecycle-service:$lifecycleVersion")

    // Compose
    api(platform(libs.androidx.compose.bom))
    api("androidx.compose.runtime:runtime")
    api("androidx.compose.foundation:foundation")
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")
    api("androidx.compose.material:material-icons-core")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navigation
    api("androidx.navigation:navigation-compose:2.8.7")

    // Kotlin & Coroutines & Serialization
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

    // Koin
    val koinVersion = "4.0.2"
    api("io.insert-koin:koin-core:$koinVersion")
    api("io.insert-koin:koin-android:$koinVersion")
    api("io.insert-koin:koin-androidx-compose:$koinVersion")
    api("io.insert-koin:koin-compose:$koinVersion")
    api("io.insert-koin:koin-compose-viewmodel:$koinVersion")
    api("io.insert-koin:koin-compose-viewmodel-navigation:$koinVersion")

    // Networking (Ktor)
    val ktorVersion = "3.1.0"
    api("io.ktor:ktor-client-core:$ktorVersion")
    api("io.ktor:ktor-client-okhttp:$ktorVersion")
    api("io.ktor:ktor-client-android:$ktorVersion")
    api("io.ktor:ktor-client-auth:$ktorVersion")
    api("io.ktor:ktor-client-logging:$ktorVersion")
    api("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    api("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    api("io.ktor:ktor-client-websockets:$ktorVersion")
    api("io.ktor:ktor-network:$ktorVersion")

    // Ktor Monitor Logging
    api("ro.cosminmihu.ktor:ktor-monitor-logging:1.13.0")

    // Image Loading (Coil)
    val coilVersion = "3.0.4"
    api("io.coil-kt.coil3:coil-compose:$coilVersion")
    api("io.coil-kt.coil3:coil-network-ktor3:$coilVersion")

    // Local Data (DataStore)
    api("androidx.datastore:datastore:1.1.2")
    api("androidx.datastore:datastore-preferences:1.1.2")

    // Icons
    val composeIcons = "1.1.1"
    api("br.com.devsrsouza.compose.icons:tabler-icons:$composeIcons")
    api("br.com.devsrsouza.compose.icons:feather:$composeIcons")

    // Paging3
    val pagingVersion = "3.3.5"
    api("androidx.paging:paging-common:$pagingVersion")
    api("androidx.paging:paging-compose:$pagingVersion")
    api("androidx.paging:paging-runtime:$pagingVersion")

    // Firebase
    api(platform("com.google.firebase:firebase-bom:33.9.0"))
    api("com.google.firebase:firebase-common-ktx")
    api("com.google.firebase:firebase-analytics")
    api("com.google.firebase:firebase-crashlytics")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation("androidx.test:core:1.6.1")
}
