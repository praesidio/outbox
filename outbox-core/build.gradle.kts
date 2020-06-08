plugins {
    `java-library`
    id("io.freefair.lombok") version "5.0.1"
}

val junitVersion = "5.5.1"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}

lombok {
    config.put("lombok.addLombokGeneratedAnnotation", "true")
}
