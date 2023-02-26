import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("java")
  id("org.springframework.boot") version "3.0.2"
  id("io.spring.dependency-management") version "1.1.0"
  kotlin("jvm") version "1.7.22"
  kotlin("plugin.spring") version "1.7.22"
  kotlin("plugin.jpa") version "1.7.22"
}

noArg { annotation("com.rwu.assignmentmanagementsystem") }
allOpen {
  annotation("com.rwu.assignmentmanagementsystem")
}

group = "com.rwu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

extra["springCloudVersion"] = "2022.0.1"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//  implementation("org.liquibase:liquibase-core")
  implementation("mysql:mysql-connector-java:8.0.30")
  testImplementation("org.springframework.boot:spring-boot-starter-test")

  //misc
  implementation("org.json:json:20211205")

  //database
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

// Resilience4J
//  implementation("io.github.resilience4j:resilience4j-spring-boot2")

  // Caching
//  implementation("org.ehcache:ehcache")
//  implementation("javax.cache:cache-api")}
}
dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
  }
}



tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}
