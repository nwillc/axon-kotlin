import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jvmTargetVersion = JavaVersion.VERSION_1_8.toString()

val axonVersion: String by project
val hsqlVersion: String by project
val jupiterVersion: String by project

plugins {
    val kotlinVersion = "1.3.50"
    application
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("com.github.nwillc.vplugin") version "3.0.1"
    id("com.google.cloud.tools.jib") version "1.7.0"
}

apply(plugin = "io.spring.dependency-management")

group = "com.group1001.daap"
version = "0.0.1"

repositories {
    jcenter()
}

dependencies {
    listOf(
        kotlin("stdlib-jdk8"),
        kotlin("reflect"),
        "org.springframework.boot:spring-boot-starter-data-jpa",
        "org.springframework.boot:spring-boot-starter-web",
        "org.axonframework:axon-spring-boot-starter:$axonVersion"
    )
        .forEach { implementation(it) }

    listOf(
        "org.hsqldb:hsqldb:$hsqlVersion"
    )
        .forEach { runtime(it) }

    listOf(
        "org.junit.jupiter:junit-jupiter:$jupiterVersion",
        "org.axonframework:axon-test:$axonVersion",
        "org.springframework.boot:spring-boot-starter-test"
    )
        .forEach { testImplementation(it) }
}

application {
    mainClassName = "io.axoniq.foodordering.FoodOrderingApplication"
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTargetVersion
    }
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "skipped", "failed")
        }
    }
}

jib {
    to {
        image = "nwillc/foodcart"
        tags = setOf("latest")
    }
    container {
        ports = listOf("8080")
    }
}
