plugins {
  id("com.android.application")
  kotlin("android")
  // Required to run obfuscated instrumentation tests:
  // ./gradlew leakcanary-android-sample:connectedCheck -Pminify
  id("com.slack.keeper")
}

android {
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    applicationId = "com.example.leakcanary"
    minSdk = 16
    targetSdk = properties["compileSdk"].toString().toInt()

    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArgument("listener", "leakcanary.FailTestOnLeakRunListener")

    // Run ./gradlew leakcanary-android-sample:connectedCheck -Porchestrator
    if (project.hasProperty("orchestrator")) {
      testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
      }
    }
  }

  buildTypes {
    // Build with ./gradlew leakcanary-android-sample:installDebug -Pminify
    getByName("debug") {
      isMinifyEnabled = project.hasProperty("minify")
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
    }
    getByName("release") {
      signingConfig = signingConfigs["debug"]
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  dexOptions {
    dexInProcess = false
  }

  lintOptions {
    disable("GoogleAppIndexingWarning")
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

keeper {
  variantFilter {
    setIgnore(!project.hasProperty("minify"))
  }
}

dependencies {
  debugImplementation(project(":leakcanary-android"))
  releaseImplementation(project(":leakcanary-android-release"))
  // Optional
  releaseImplementation(project(":leakcanary-object-watcher-android"))

  implementation(libs.kotlin.stdlib)

  testImplementation(libs.junit)
  testImplementation(libs.robolectric)

  androidTestImplementation(project(":leakcanary-android-instrumentation"))
  androidTestImplementation(libs.androidX.test.espresso)
  androidTestImplementation(libs.androidX.test.rules)
  androidTestImplementation(libs.androidX.test.runner)
  androidTestUtil(libs.androidX.test.orchestrator)
}
