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

    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {

    implementation("com.squareup.okhttp3:okhttp:3.13.1")

    implementation("net.dv8tion:JDA:3.8.2_459")

    implementation("me.camdenorrb:KDI:+")
    implementation("me.camdenorrb:MiniBus:+")
    implementation("me.camdenorrb:KCommons:+")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "me/camdenorrb/purrbot/MainKt"
}