package com.blumer.msu.lifecycle

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean,  var answered: Boolean = false)