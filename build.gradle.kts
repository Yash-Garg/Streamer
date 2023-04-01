// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")

import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.hilt) apply false

    id("dev.yashgarg.streamer.spotless")
    id("dev.yashgarg.streamer.githooks")
}

val clean by tasks.existing(Delete::class) { delete(rootProject.buildDir) }

afterEvaluate {
    tasks.prepareKotlinBuildScriptModel.dependsOn(tasks.copyGitHooks, tasks.installGitHooks)
}
