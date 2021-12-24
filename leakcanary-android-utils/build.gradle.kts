plugins {
  id("com.android.library")
  kotlin("android")
  id("com.vanniktech.maven.publish")
}

android {
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    minSdk = properties["minSdk"].toString().toInt()
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

  implementation(libs.kotlin.stdlib)
}
