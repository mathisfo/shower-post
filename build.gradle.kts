import com.soywiz.korge.gradle.*

buildscript {
	val korgePluginVersion: String by project

	repositories {
		mavenLocal()
		mavenCentral()
		google() // Google's Maven repository
		maven { url = uri("https://plugins.gradle.org/m2/") }
	}
	dependencies {
		classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion");
		classpath("com.google.gms:google-services:4.3.10")
		// Import the Firebase BoM
		classpath(platform("com.google.firebase:firebase-bom:29.2.0"));
		// Add the dependency for the Firebase SDK for Google Analytics
		// When using the BoM, don't specify versions in Firebase dependencies
		classpath("com.google.firebase:firebase-analytics-ktx")

		// Add the dependencies for any other desired Firebase products
		// https://firebase.google.com/docs/android/setup#available-libraries
	}
}

apply<KorgeGradlePlugin>()

korge {
	id = "com.battleofwits.BattleOfWits"
	supportBox2d()
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	targetJs()
	targetDesktop()
	targetIos()
	targetAndroidIndirect() // targetAndroidDirect()
}

/*
allprojects {
	repositories {
		google()  // Google's Maven repository
	}
}
 */

apply(plugin="com.android.application")
apply(plugin="com.google.gms.google-services")

