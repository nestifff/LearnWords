buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(Dependencies.Gradle.gradleBuild)
        classpath(Dependencies.Gradle.gradleKotlin)
        classpath(Dependencies.Gradle.composeDetekt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    plugins.withType<io.gitlab.arturbosch.detekt.DetektPlugin> {
        configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            buildUponDefaultConfig = true
            config = files("$rootDir/config/detekt/detekt.yml")
            baseline = file("$rootDir/config/detekt/baseline.xml")
            // autoCorrect = true
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
