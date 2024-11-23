plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.cattleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cattleapp"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Зависимости для Retrofit и Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Зависимость для RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Зависимость для OkHttp (для логирования сетевых запросов)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Зависимость для Material Design компонентов
    implementation ("com.google.android.material:material:1.4.0")

    // Другие необходимые
    // зависимости
    implementation ("androidx.appcompat:appcompat:1.3.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.0")
}
