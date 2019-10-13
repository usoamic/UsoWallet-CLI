import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

allprojects {
    group = "io.usoamic.cli"
    version = "1.0.1"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile("org.jetbrains.kotlin", "kotlin-stdlib", "1.3.50")
    compile("com.google.dagger", "dagger", "2.23.2")
    compile("com.google.code.gson", "gson", "2.8.5")
    compile("org.web3j", "core", "4.3.1")
    compile("com.github.usoamic", "usoamic-kotlin", "v1.0.10")
    kapt("com.google.dagger", "dagger-compiler", "2.23.2")

    testCompile("org.jetbrains.kotlin", "kotlin-test-junit5", "1.3.40")
    testCompile ("org.junit.jupiter", "junit-jupiter", "5.5.0")
    testCompile("com.google.dagger", "dagger", "2.23.2")
    kaptTest("com.google.dagger", "dagger-compiler", "2.23.2")
    testAnnotationProcessor("com.google.dagger", "dagger-compiler", "2.23.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}