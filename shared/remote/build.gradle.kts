
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import util.libs
import java.io.FileInputStream
import java.util.Properties

plugins {
    `kmm-domain-plugin`
    kotlin("plugin.serialization") version ("1.6.10")
    id("com.codingfeline.buildkonfig")
}

android {
    namespace = "com.thomaskioko.tvmaniac.remote"
}

dependencies {
    commonMainImplementation(libs.ktor.core)
    commonMainImplementation(libs.ktor.logging)
    commonMainImplementation(libs.ktor.serialization)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kermit)

    androidMainImplementation(libs.ktor.android)
    androidMainImplementation(libs.squareup.sqldelight.driver.android)

    iosMainImplementation(libs.ktor.ios)

    commonTestImplementation(kotlin("test"))
    commonTestImplementation(projects.shared.core.test)

    commonTestImplementation(libs.testing.ktor.mock)
    commonTestImplementation(libs.testing.turbine)
    commonTestImplementation(libs.testing.kotest.assertions)
    commonTestImplementation(libs.testing.mockk.common)

    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.testing.androidx.junit)
}

buildkonfig {
    val properties = Properties()
    val secretsFile = file("../../local.properties")
    if (secretsFile.exists()) {
        properties.load(FileInputStream(secretsFile))
    }

    packageName = "com.thomaskioko.tvmaniac.remote"
    defaultConfigs {
        buildConfigField(STRING, "TMDB_API_KEY", properties["TMDB_API_KEY"] as String)
    }
}