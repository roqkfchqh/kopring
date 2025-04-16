import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	val kotlinVersion = "2.1.10"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion

	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "ko.pring"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { "build/generated-snippets" }

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "org.mockito")
	}
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("mysql:mysql-connector-java:8.0.33")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("io.mockk:mockk:1.13.10")
	compileOnly("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
		jvmTarget.set(JvmTarget.JVM_17)
	}
}

tasks.test {
	useJUnitPlatform()
	outputs.dir(snippetsDir)
}

tasks.named<org.asciidoctor.gradle.jvm.AsciidoctorTask>("asciidoctor") {
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
	attributes(
		mapOf(
			"snippets" to snippetsDir
		)
	)
}

tasks.register<Copy>("copyDocs") {
	dependsOn(tasks.asciidoctor)
	from(file("build/docs/asciidoc"))
	into(file("src/main/resources/static/docs"))
}