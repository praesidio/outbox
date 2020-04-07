plugins {
    `java-library`
}

val junitVersion = "5.5.1"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}
