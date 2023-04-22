@file:Suppress("UnstableApiUsage")

plugins {
  val kt = "1.8.20" // Change to 1.8.10 and tests pass
  kotlin("jvm") version kt
  kotlin("plugin.spring") version kt
  id("org.springframework.boot") version "3.0.6"
}

kotlin {
  jvmToolchain(17)
}

testing {
  suites.withType<JvmTestSuite>().configureEach {
    useJUnitJupiter()
    targets.configureEach {
      testTask {
      }
    }
  }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0-rc3")
  implementation("org.springframework.boot:spring-boot-starter-web:3.0.6")

  testImplementation(kotlin("test-junit"))
  testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.6")
}
