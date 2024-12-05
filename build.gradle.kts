import Libraries

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

allprojects {
    group = "com.example"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java")

    dependencies {
        implementation(Libraries.kotlinStdLib)

        testImplementation(Libraries.junitJupiter)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")

    dependencies {
        implementation(Libraries.springBootStarter)
        implementation(Libraries.springBootStarterWeb)

        testImplementation(Libraries.springBootStarterTest)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    sourceSets {
        main {
            java {
                srcDir("src/main/java")
            }
            kotlin {
                srcDir("src/main/kotlin")
            }
            resources {
                srcDir("src/main/resources")
            }
        }
        test {
            java {
                srcDir("src/test/java")
            }
            kotlin {
                srcDir("src/test/kotlin")
            }
            resources {
                srcDir("src/test/resources")
            }
        }
    }
}