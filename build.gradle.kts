import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

allprojects {
    repositories.applyDefault()

    plugins.apply("checks.dependency-updates")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            useIR = true
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                "-Xopt-in=coil.annotation.ExperimentalCoilApi",
            )
        }
    }
}

/**
 * Disable iosTest Task for now. Using mockk causes the build to fail. Revisit later.
 * Action:
 * - Resolve issue or replace dependency
 */
project.gradle.startParameter.excludedTaskNames.add("compileTestKotlinIos")