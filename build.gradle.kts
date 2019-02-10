import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("com.github.johnrengelman.shadow") version "4.0.4"
    kotlin("jvm") version "1.3.21"
}

group = "me.camdenorrb"
version = "1.0.0"

repositories {

    jcenter()

    mavenLocal()
    mavenCentral()
}

dependencies {

    implementation("net.dv8tion:JDA:+")
    implementation("me.camdenorrb:KDI:+")
    implementation("me.camdenorrb:MiniBus:+")
    implementation("org.slf4j:slf4j-simple:+")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "me/camdenorrb/purrbot/MainKt"
}