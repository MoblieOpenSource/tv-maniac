plugins {
    id("tvmaniac.kmm.library")
}


kotlin {
    android()
    ios()

    sourceSets {
        sourceSets["commonMain"].dependencies {
            implementation(projects.core.database)
            implementation(projects.core.util)
            implementation(projects.data.trailers.api)

            implementation(libs.coroutines.core)
        }
    }
}

android {
    namespace = "com.thomaskioko.tvmaniac.trailers.testing"
}