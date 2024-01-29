import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	kotlin("kapt") version "1.8.22"
	kotlin("plugin.noarg") version "1.8.22"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
}

group = "daniil"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.2.27")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation ("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.retry:spring-retry:2.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation ("io.github.oshai:kotlin-logging-jvm:5.1.0")
	implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
	kapt("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
	implementation("org.jboss.logging:jboss-logging:3.5.3.Final")
	implementation("net.bramp.ffmpeg:ffmpeg:0.8.0")
	implementation("org.bytedeco:javacv-platform:1.5.9")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

allOpen {
	annotation("jakarta.persistence.Entity")
}

noArg {
	annotation("daniil.backend.annotation.NoArgsConstructor")
}