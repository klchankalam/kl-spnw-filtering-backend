import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("plugin.jpa") version "1.3.31"
	id("org.springframework.boot") version "2.1.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	id("org.asciidoctor.convert") version "1.5.3"
	war
	kotlin("jvm") version "1.3.31"
	kotlin("plugin.spring") version "1.3.31"
	kotlin("kapt") version "1.3.31"
}

group = "kl.spnw"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.querydsl:querydsl-jpa")
	implementation("javax.inject:javax.inject")
	kapt("com.querydsl:querydsl-apt:4.2.1:jpa")
	//implementation("org.liquibase:liquibase-core")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit", group = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("com.ninja-squad:springmockk:1.1.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.junit.jupiter:junit-jupiter-params")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
	runtime("com.h2database:h2")

	asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
	testCompile("org.springframework.restdocs:spring-restdocs-mockmvc")
}

val snippetsDir = file("build/generated-snippets")

tasks.asciidoctor {
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
}

tasks.test {
	useJUnitPlatform()
	systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
	outputs.dir(snippetsDir)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.build {
	dependsOn(tasks.asciidoctor)
//	from("${tasks.asciidoctor.getOut}/html5")
//	into("static/docs")
}
