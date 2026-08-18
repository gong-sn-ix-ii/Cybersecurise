plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.develop.cybersecurise"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.develop.cybersecurise"
        minSdk = 31
        targetSdk = 34
        versionCode = 9
        versionName = "1.0.9"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }

    }

//    chaquopy {
//        sourceSets {
//            getByName("main") {
//                srcDir("src/main/python")
//            }
//        }
//        defaultConfig {
//            version = "3.8"
//            buildPython("C:/Users/Gong/AppData/Local/Programs/Python/Python312/python.exe")
//
//            pip {
//                install("joblib==1.2.0")
////                install("pythainlp")
////                install("scikit-learn")
////                install("./src/main/python/scikit_learn-1.5.1.tar.gz")
//            }
//
//        }
//
//    }


    buildFeatures{
        viewBinding = true
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
}

dependencies {



    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.material:material:1.12.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")

    implementation("org.tensorflow:tensorflow-lite-select-tf-ops:2.16.1") // or your required version
    implementation("org.tensorflow:tensorflow-lite:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-task-text:0.4.4")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx:20.0.4")
    implementation("com.google.firebase:firebase-database-ktx:20.0.4")

    // Libraries for UI components
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
