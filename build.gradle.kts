@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.8.20" // Change to 1.8.10 and tests pass
}

kotlin {
  jvmToolchain(17)
}

testing.suites.withType<JvmTestSuite>().configureEach {
  useJUnitJupiter()
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions { javaParameters = true }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  testImplementation(kotlin("test-junit"))

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
  implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.15.0")
}
