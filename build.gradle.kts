plugins {
    java
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

configure(subprojects) {
    group = "ru.testit"
    version = version

    apply(plugin = "maven-publish")
    apply(plugin = "java")

    publishing {
        publications {
            create<MavenPublication>("maven") {
                suppressAllPomMetadataWarnings()
                versionMapping {
                    allVariants {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set(project.name)
                    description.set("Module ${project.name} of TestIT Framework.")
                    url.set("https://github.com/testit-tms/adapters-java")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    scm {
                        developerConnection.set("scm:git:git://github.com/testit-tms/adapters-java")
                        connection.set("scm:git:git://github.com/testit-tms/adapters-java")
                        url.set("https://github.com/testit-tms/adapters-java")
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://github.com/testit-tms/adapters-java/issues")
                    }
                }
            }
        }
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<Sign>().configureEach {
        onlyIf { !project.version.toString().endsWith("-SNAPSHOT") }
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }

    tasks.jar {
        manifest {
            attributes(mapOf(
            "Specification-Title" to project.name,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
            ))
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

    publishing.publications.named<MavenPublication>("maven") {
        pom {
            from(components["java"])
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}