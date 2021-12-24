plugins {
  kotlin("jvm")
  id("com.vanniktech.maven.publish")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  implementation(libs.kotlin.stdlib)
  api(project(":shark-log"))

  testImplementation(libs.assertjCore)
  testImplementation(libs.junit)
}
