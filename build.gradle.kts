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
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlin.time.ExperimentalTime",
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-Xopt-in=coil.annotation.ExperimentalCoilApi",
            )
        }
    }
}
