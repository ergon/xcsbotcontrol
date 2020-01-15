plugins {
    application
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
    jcenter()
}

dependencies {
    implementation("ch.ergon.xcsbotcontrol:xcsbotcontrol:1.0")
}

application {
    mainClassName = "ch.ergon.xcsbotcontrol.BotController"
}
