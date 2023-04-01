import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { `kotlin-dsl` }

dependencies {
    implementation(libs.build.spotless)
    implementation(libs.build.kotlin)
}

afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions { jvmTarget = JavaVersion.VERSION_17.toString() }
    }
}

gradlePlugin {
    plugins {
        register("spotless") {
            id = "dev.yashgarg.streamer.spotless"
            implementationClass = "dev.yashgarg.streamer.gradle.SpotlessPlugin"
        }
        register("githooks") {
            id = "dev.yashgarg.streamer.githooks"
            implementationClass = "dev.yashgarg.streamer.gradle.GitHooksPlugin"
        }
    }
}
