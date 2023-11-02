import com.android.build.gradle.internal.dependency.getProvidedClasspath

plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.opt_1"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.opt_1"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("io.realm:realm-gradle-plugin:10.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation ("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.test.uiautomator:uiautomator:2.2.0")
    debugImplementation("androidx.test:monitor:1.6.1")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation("androidx.test:core-ktx:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:5.6.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")
    debugImplementation ("androidx.fragment:fragment-testing:1.6.2")
}
tasks.withType<Test> {
    testLogging {
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("started", "skipped", "passed", "failed")
        showStandardStreams = true
    }
}