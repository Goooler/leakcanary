import java.io.FileOutputStream
import java.util.*

plugins {
  kotlin("jvm")
  application
  id("com.vanniktech.maven.publish")
}

application {
  mainClass.set("shark.MainKt")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

// Workaround for https://stackoverflow.com/questions/48988778
// /cannot-inline-bytecode-built-with-jvm-target-1-8-into-bytecode-that-is-being-bui
tasks.compileKotlin {
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

dependencies {
  api(project(":shark-android"))

  implementation(libs.clikt)
  implementation(libs.jline)
  implementation(libs.kotlin.stdlib)
}

val generatedVersionDir = "${buildDir}/generated-version"

sourceSets {
  main {
    output.dir(generatedVersionDir, "builtBy" to "generateVersionProperties")
  }
}

tasks.register("generateVersionProperties") {
  doLast {
    val propertiesFile = file("$generatedVersionDir/version.properties")
    propertiesFile.parentFile.mkdirs()
    val properties = Properties()
    properties.setProperty("version_name", properties["VERSION_NAME"].toString())
    properties.store(FileOutputStream(propertiesFile), null)
  }
}.let {
  tasks["processResources"].dependsOn(it.get())
}
