package com.sam.asternews.utils

import com.sam.asternews.core.domain.model.SavedNews

sealed class OneTimeEvents{
    data class Navigation(val route:String):OneTimeEvents()
    object PopBackStack:OneTimeEvents()
    data class SnackBar(val message:String,val action:String):OneTimeEvents()
}
