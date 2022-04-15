package com.yabasoft.henripotier.utils

import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.Offer

// Updated after deadline
object Utils {

    fun calculateTotal(listBooks: List<Book>): Int {
        var totalPrice = 0
        for (item in listBooks) {
            totalPrice += item.price
        }
        return totalPrice
    }

    fun calculateTotalAfterDiscount(totalPrice: Int, discount: Int): Int {
        return totalPrice - discount
    }

    fun calculateMaxReduction(totalPrice: Int, offers: List<Offer>?): Int {
        val listOffers: MutableList<Int> = mutableListOf()
        if (offers != null) {
            for (offer in offers) {
                when (offer.type) {
                    "percentage" -> listOffers.add(calculatePercentage(totalPrice, offer.value))
                    "minus" -> listOffers.add(offer.value)
                    "slice" -> listOffers.add(calculateSlice(totalPrice, offer))
                    else -> {
                        listOffers.add(0)
                    }
                }
            }
        }
        return listOffers.maxOrNull() ?: 0
    }

    private fun calculatePercentage(totalPrice: Int, percentage: Int): Int {
        return totalPrice * percentage / 100
    }

    private fun calculateSlice(totalPrice: Int, offer: Offer): Int {
        return (totalPrice / offer.sliceValue) * offer.value
    }

}