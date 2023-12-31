plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("maven-publish")
}

android {
    namespace = "com.cccd.io.sdk.capture"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-exoplayer-dash:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")

    implementation("io.ktor:ktor-client-core:2.3.6")
    implementation("io.ktor:ktor-client-android:2.3.6")
    implementation("io.ktor:ktor-client-logging:2.3.6")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")

    implementation("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    implementation("org.jmrtd:jmrtd:0.7.40")
    implementation("com.madgag.spongycastle:prov:1.58.0.0")
    implementation("net.sf.scuba:scuba-sc-android:0.0.25")
    implementation("org.bouncycastle:bcpkix-jdk18on:1.77")


    implementation("androidx.camera:camera-core:1.2.1")
    implementation("androidx.camera:camera-camera2:1.2.1")
    implementation("androidx.camera:camera-lifecycle:1.2.1")
    implementation("androidx.camera:camera-video:1.2.1")
    implementation("androidx.camera:camera-view:1.2.1")
    implementation("androidx.camera:camera-extensions:1.2.1")
    implementation("com.github.mhshams:jnbis:2.0.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                groupId = "com.cccd.sdk"
                artifactId = "workflow"
                version = "1.0.0"

                afterEvaluate {
                    from(components["release"])
                }
            }
        }
    }
}