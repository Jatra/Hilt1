package uk.co.jatra.hilt1

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Annotation required to for the annotation processor to
//create the dependency injection capability
@HiltAndroidApp
class Hilt1Application: Application()