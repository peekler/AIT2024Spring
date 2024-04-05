package hu.bme.aut.graderoomdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Application class needs to be registered in the Manifest file

@HiltAndroidApp
class MainApplication : Application() {
}