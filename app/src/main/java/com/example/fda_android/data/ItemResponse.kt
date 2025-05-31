package com.example.fda_android.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ItemResponse(
    val data: ItemData,
    val success: Boolean
) : Parcelable


@Parcelize
data class CustomizationQuestion(
    val options: List<Option>,
    val questionId: String,
    val questionText: String,
    val selectedOptionId: String
) : Parcelable


@Parcelize
data class ItemData(
    val customizationQuestions: List<CustomizationQuestion>,
    val itemDescription: String,
    val itemId: String,
    val itemImage: String,
    val itemName: String,
    val itemPrice: String,
    val cartItemCount: String
) : Parcelable


@Parcelize
data class Option(
    val optionId: String,
    val optionText: String
) : Parcelable