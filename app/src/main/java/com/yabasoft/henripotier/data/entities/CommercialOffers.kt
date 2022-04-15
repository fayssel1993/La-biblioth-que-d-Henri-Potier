package com.yabasoft.henripotier.data.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
data class CommercialOffers(@SerializedName("offers") val offers: List<Offer>)

data class Offer(
    @SerializedName("sliceValue") val sliceValue: Int,
    @SerializedName("type") val type: String,
    @SerializedName("value") val value: Int
)
