plugins {
    java
    application
    id("org.danilopianini.gradle-java-qa") version "1.26.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
    mavenCentral()
}

spotbugs {
    ignoreFailures.set(false)
    excludeFilter = file("spotbugs-exclude.xml")
}

val javaFXModules = listOf("base","controls","fxml","swing","graphics")
val supportedPlatforms = listOf("linux","mac","win") 
val javaFxVersion = 17

dependencies {
    implementation("org.apache.logging.log4j:log4j-core:2.22.1")

    for (platform in supportedPlatforms) {
	    for (module in javaFXModules) {
	        implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
	    }
    }
}


tasks.javadoc {
    isFailOnError = false
}

//val mainClass: start.LudoPartyApp

application {
    // The following allows to run with: ./gradlew -PmainClass=start.LudoPartyApp run
    mainClass.set("start.LudoPartyApp")
}