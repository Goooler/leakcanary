plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
}

android {
  resourcePrefix = "leak_canary_"
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    minSdk = 16
    buildConfigField("String", "LIBRARY_VERSION", "\"${properties["VERSION_NAME"]}\"")
    consumerProguardFiles("consumer-proguard-rules.pro")
  }
  lintOptions {
    disable("GoogleAppIndexingWarning")
    error("ObsoleteSdkInt")
    checkOnly("Interoperability")
  }
}

dependencies {
  api(project(":shark-android"))
  api(project(":leakcanary-android-utils"))

  implementation(libs.kotlin.stdlib)
  implementation(libs.okio2)
}
