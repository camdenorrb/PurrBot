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

    implementation("net.dv8tion:JDA:+")
    implementation("org.slf4j:slf4j-simple:+")
    implementation("com.google.code.gson:gson:2.8.5")

    implementation("com.squareup.okhttp3:okhttp:3.13.1")

    implementation("me.camdenorrb:KDI:+")
    implementation("me.camdenorrb:MiniBus:+")
    implementation("me.camdenorrb:KCommons:+")

    implementation("org.jetbrains.kotlin:kotlin-reflect:+")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "me/camdenorrb/purrbot/Main"
}