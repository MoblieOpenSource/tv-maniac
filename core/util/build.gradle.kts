plugins {
    id("tvmaniac.kmm.library")
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
}


kotlin {
    android()
    ios()

    sourceSets {
        sourceSets["androidMain"].dependencies {
            implementation(libs.kotlinx.datetime)
        }

        sourceSets["commonMain"].dependencies {
            api(libs.ktor.serialization)

            implementation(libs.coroutines.core)
            implementation(libs.kermit)
            implementation(libs.kotlinInject.runtime)
            implementation(libs.ktor.core)
            implementation(libs.yamlkt)
        }


        sourceSets["iosMain"].dependencies {
            implementation(libs.kotlinx.datetime)
        }

    }
}

android {
    namespace = "com.thomaskioko.tvmaniac.util"

    sourceSets["main"].apply {
        resources.srcDirs("src/commonMain/resources") // <-- add the commonMain Resources
    }
}


dependencies {
    add("kspIosX64", libs.kotlinInject.compiler)
    add("kspIosArm64", libs.kotlinInject.compiler)
}