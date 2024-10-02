package com.example.odes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmptyStateModel(
    val animationRes: Int,
    val title: String,
    val description: String,
    val buttonText: String
) : Parcelable