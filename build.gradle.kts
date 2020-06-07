import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Version {
    const val kotlinStdLib = "1.3.50"
    const val dagger = "2.27"
    const val gson = "2.8.5"
    const val web3j = "4.6.0"
    const val validateUtilKt = "16304cc35d"
    const val javaxAnnotationApi = "1.3.2"
    const val daggerCompiler = "2.28"
    const val kotlinTestJunit5 = "1.3.50"
    const val junitJupiter = "5.5.0"
    const val usoamicKt = "v1.2.1b2"
}

plugins {
    java
    kotlin("jvm") version "1.3.50"
    kotlin("kapt") version "1.3.50"
}

allprojects {
    group = "io.usoamic"
    version = "1.0.8"
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

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile("org.jetbrains.kotlin", "kotlin-stdlib", Version.kotlinStdLib)
    compile("com.google.dagger", "dagger", Version.dagger)
    compile("com.google.code.gson", "gson", Version.gson)
    compile("org.web3j", "core", Version.web3j)
    compile("com.github.usoamic", "usoamickt", Version.usoamicKt)
    kapt("com.google.dagger", "dagger-compiler", Version.dagger)

    testCompile("org.jetbrains.kotlin", "kotlin-test-junit5", Version.kotlinTestJunit5)
    testCompile ("org.junit.jupiter", "junit-jupiter", Version.junitJupiter)
    testCompile("com.google.dagger", "dagger", Version.dagger)
    kaptTest("com.google.dagger", "dagger-compiler", Version.daggerCompiler)
    testAnnotationProcessor("com.google.dagger", "dagger-compiler", Version.daggerCompiler)
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    // manifest Main-Class attribute is optional.
    // (Used only to provide default main class for executable jar)
    manifest {
        attributes["Main-Class"] = "io.usoamic.cli.App"
    }
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
    exclude(
        "META-INF/*.SF",
        "META-INF/*.DSA",
        "META-INF/*.RSA",
        "junit",
        "org.mockito",
        "org.hamcrest"
    )
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
    "test"(Test::class) {
        useJUnitPlatform()
    }
}