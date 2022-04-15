package com.yabasoft.henripotier.utils

import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.Offer

class Utils {

    companion object {
        fun calculateTotal(listBooks: List<Book>): Int {
            var totalPrice = 0
            for (item in listBooks) {
                totalPrice += item.price
            }
            return totalPrice
        }

        fun calculateTotalDiscount(totalPrice: Int, discount: Int): Int {
            return totalPrice - discount
        }

        fun calculateMaxOffer(totalPrice: Int, offers: List<Offer>): Int {
            val listOffers: MutableList<Int> = mutableListOf<Int>()
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
            return listOffers.max() ?: 0
        }

        fun calculatePercentage(totalPrice: Int, percentage: Int): Int {
            return totalPrice * percentage / 100
        }

        fun calculateSlice(totalPrice: Int, offer: Offer): Int {
            return (totalPrice / offer.sliceValue) * offer.value
        }
    }
}