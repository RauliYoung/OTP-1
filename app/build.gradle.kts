import com.android.build.gradle.internal.tasks.JacocoTask
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.type

plugins {
    id("com.android.application")
    id ("com.google.gms.google-services")
    id("com.google.secrets_gradle_plugin") version "0.6.1"
    id("jacoco")
}

jacoco{
    version = "0.8.8"
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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
        debug {
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
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
    implementation("androidx.test:monitor:1.6.1")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation ("org.mockito:mockito-core:5.6.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:rules:1.5.0")

}
tasks.withType<Test> {
    testLogging {
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("started", "skipped", "passed", "failed")
        showStandardStreams = true
    }
    finalizedBy(tasks.withType<JacocoReport>())
}
// ./gradlew createDebugCoverageReport for coverage
// ./gradlew test for unit test
tasks.withType<JacocoReport> {
    dependsOn(tasks.withType<Test>())
    group = "Verification" // existing group containing tasks for generating linting reports etc.
    description = "Generate Jacoco coverage reports for the 'local' debug build."

    reports {
        //html.required = true
        //xml.required = false
        //html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }

    executionData.from("${layout.buildDirectory}/outputs/unit_test_code_coverage/localDebugUnitTest/testLocalDebugUnitTest.exec")

    classDirectories.from("${layout.buildDirectory}/tmp/kotlin-classes/localDebug")

    sourceDirectories.from("${layout.buildDirectory}/src/main/java")
}
