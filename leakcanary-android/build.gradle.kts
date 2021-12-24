plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("com.vanniktech.maven.publish")
}

dependencies {
  api(project(":leakcanary-android-core"))

  implementation(libs.kotlin.stdlib)
}

android {
  compileSdk = properties["compileSdk"].toString().toInt()
  defaultConfig {
    minSdk = properties["minSdk"].toString().toInt()
  }
  buildFeatures.buildConfig = false
  lintOptions {
    disable("GoogleAppIndexingWarning")
    // junit references java.lang.management
    ignore("InvalidPackage")
    checkOnly("Interoperability")
  }
}
