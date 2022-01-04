import java.util.Properties

plugins {
  id("org.jetbrains.kotlin.jvm")
  id("application")
  id("com.vanniktech.maven.publish")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

// Workaround for https://stackoverflow.com/questions/48988778
// /cannot-inline-bytecode-built-with-jvm-target-1-8-into-bytecode-that-is-being-bui
tasks.compileKotlin {
  kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

application {
  mainClass.set("shark.MainKt")
}

val generatedVersionDir = "${buildDir}/generated-version"

sourceSets["main"].output.dir(generatedVersionDir, "builtBy" to "generateVersionProperties")

dependencies {
  api(project(":shark-android"))

  implementation(libs.clikt)
  implementation(libs.jline)
  implementation(libs.kotlin.stdlib)
}

tasks.register("generateVersionProperties") {
  doLast {
    val propertiesFile = file("$generatedVersionDir/version.properties")
    propertiesFile.parentFile.mkdirs()
    Properties().also {
      it.setProperty("version_name", properties["VERSION_NAME"].toString())
      it.store(propertiesFile.outputStream(), null)
    }
  }
}.let {
  tasks.processResources.get().dependsOn(it.get())
}

