plugins {
  kotlin("jvm")
  `java-gradle-plugin`
  id("com.vanniktech.maven.publish")
}

gradlePlugin {
  plugins {
    register("leakCanary") {
      id = "com.squareup.leakcanary.deobfuscation"
      implementationClass =
        "com.squareup.leakcanary.deobfuscation.LeakCanaryLeakDeobfuscationPlugin"
    }
  }

  sourceSets["test"].java.srcDirs("src/test/test-project/src/main/java")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinPlugin)
  implementation(libs.androidPlugin)
  compileOnly(gradleApi())

  testImplementation(libs.assertjCore)
  testImplementation(libs.junit)
}
