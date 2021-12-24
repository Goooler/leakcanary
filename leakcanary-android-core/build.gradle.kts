plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
}

android {
  resourcePrefix = "leak_canary_"
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    minSdk = properties["minSdk"].toString().toInt()
    buildConfigField("String", "LIBRARY_VERSION", "\"${properties["VERSION_NAME"]}\"")
    consumerProguardFiles("consumer-proguard-rules.pro")
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  lintOptions {
    disable("GoogleAppIndexingWarning")
    error("ObsoleteSdkInt")
    checkOnly("Interoperability")
  }
}

dependencies {
  api(project(":shark-android"))
  api(project(":leakcanary-object-watcher-android"))
  api(project(":leakcanary-object-watcher-android-androidx"))
  api(project(":leakcanary-object-watcher-android-support-fragments"))
  implementation(project(":plumber-android"))

  implementation(libs.kotlin.stdlib)

  testImplementation(libs.assertjCore)
  testImplementation(libs.junit)
  testImplementation(libs.kotlin.reflect)
  testImplementation(libs.mockito)
  testImplementation(libs.mockitoKotlin)
  androidTestImplementation(libs.androidX.test.espresso)
  androidTestImplementation(libs.androidX.test.rules)
  androidTestImplementation(libs.androidX.test.runner)
  androidTestImplementation(project(":shark-hprof-test"))
}
