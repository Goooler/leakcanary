plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
}

android {
  resourcePrefix = "leak_canary_plumber"
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    minSdk = properties["minSdk"].toString().toInt()
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
  buildFeatures.buildConfig = false
  lintOptions {
    disable("GoogleAppIndexingWarning")
    error("ObsoleteSdkInt")
    checkOnly("Interoperability")
  }
}

dependencies {
  api(project(":shark-log"))
  api(project(":leakcanary-android-utils"))

  implementation(libs.kotlin.stdlib)
  // Optional dependency
  compileOnly(libs.androidX.fragment)
}
