plugins {
    kotlin("jvm")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("java")
}

dependencies {
    implementation(Libraries.kotlin)
    implementation(Libraries.springBootStarter)
    implementation(Libraries.springBootStarterWeb)

    testImplementation(Libraries.springBootStarterTest)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}