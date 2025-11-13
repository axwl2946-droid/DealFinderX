package com.example.dealfinder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DealFinderApplication : Application() {
    // This class is required for Hilt to set up its dependency graph.
    // No custom logic is needed here for now.
}