plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tmdb_test.store.app"
    compileSdk = Libs.BuildConfig.compileSdk

    defaultConfig {
        minSdk = Libs.BuildConfig.minSdk
        targetSdk = Libs.BuildConfig.targetSdk

        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            consumerProguardFiles("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Libs.BuildConfig.CompileOptions.sourceCompatibility
        targetCompatibility = Libs.BuildConfig.CompileOptions.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Libs.BuildConfig.KotlinOptions.jvmTarget
    }
    sourceSets {
        this[Libs.SourceSet.Main.name].java.srcDirs(*Libs.SourceSet.Main.sourceSets)
        this[Libs.SourceSet.Test.name].java.srcDirs(*Libs.SourceSet.Test.sourceSets)
    }
}

dependencies {
    implementation(project(":store:base"))
    implementation(project(":store:env"))
    implementation(project(":store:state"))
    implementation(project(":store:reducer"))
    implementation(project(":util"))
    implementation(libs.bundles.store.app)
    kapt(libs.bundles.store.app.kapt)
}