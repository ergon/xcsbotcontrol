plugins {
    application
    java
    `maven-publish`
}

application {
    mainClassName = "ch.ergon.xcsbotcontrol.BotController"
}

tasks.jar {
    archiveName = "xcsbotcontrol.jar"
    manifest {
        attributes["Main-Class"] = "ch.ergon.xcsbotcontrol.BotController"
    }

    from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
}

dependencies {
    compile("com.google.code.gson:gson:2.7")
    compile("com.google.guava:guava:28.0-jre")
    compile("info.picocli:picocli:4.1.4")
    annotationProcessor("info.picocli:picocli-codegen:4.1.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testCompile("org.mockito:mockito-core:3.2.4")
    testCompile("org.mockito:mockito-junit-jupiter:3.2.4")
}

tasks.withType<JavaCompile> {
    val compilerArgs = options.compilerArgs
    compilerArgs.add("-Aproject=xcsbotcontrol")
}

group = "ch.ergon.xcsbotcontrol"
version = "1.0.2"

publishing {
    publications {
        register("gpr", MavenPublication::class) {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ergon/xcsbotcontrol")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("PASSWORD")
            }
        }
    }

}
