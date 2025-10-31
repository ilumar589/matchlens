import io.quarkus.gradle.tasks.QuarkusDev
import io.quarkus.gradle.tasks.QuarkusRun
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.allopen") version "2.2.20"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-openai")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:quarkus-langchain4j-bom:${quarkusPlatformVersion}"))
    implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-core")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-easy-rag")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkiverse.langchain4j:quarkus-langchain4j-pgvector")
    implementation("io.quarkus:quarkus-redis-client")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-hibernate-orm")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

group = "org.quarkystats"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_24
    targetCompatibility = JavaVersion.VERSION_24
}

tasks.named<QuarkusDev>("quarkusDev") {
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

// (Optional) Also for `quarkusRun` if you use it
tasks.withType<QuarkusRun>().configureEach {
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")
}

// 1) Apply --add-opens to all JavaExec-based runs (covers quarkusDev)
tasks.withType<JavaExec>().configureEach {
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")

}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    jvmArgs = listOf("--add-opens=java.base/java.lang=ALL-UNNAMED")

}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
        javaParameters = true
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}