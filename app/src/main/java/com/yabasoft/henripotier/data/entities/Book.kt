package com.yabasoft.henripotier.data.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
@Entity(tableName = "books")
data class Book(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("isbn")
    val isbn: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("cover")
    val cover: String,
) {
    @Ignore
    @SerializedName("synopsis")
    val synopsis: List<String> = listOf()
}
