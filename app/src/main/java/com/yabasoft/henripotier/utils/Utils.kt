package com.xebia.hpotier.util

import com.xebia.hpotier.data.remote.domain.Offer
import com.xebia.hpotier.data.room.entity.CartBooks

class CalculesUtils {

    companion object {
        fun calculeTotal(listBooks : List<CartBooks>): Int {
            var totalPrice = 0
            for (item in listBooks) {
                totalPrice = totalPrice + item.price
            }
            return totalPrice
        }

        fun calculeTotalDiscount(totalPrice : Int, discount : Int) : Int {
            return totalPrice - discount
        }

        fun calculateMaxOffre(totalPrice: Int, offers : List<Offer>) : Int {
            var listOffers : MutableList<Int> = mutableListOf<Int>()
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

        fun calculatePercentage(totalPrice : Int, percentage : Int): Int {
            return totalPrice * percentage / 100
        }

        fun calculateSlice(totalPrice: Int, offer: Offer): Int {
            return ( totalPrice / offer.sliceValue ) * offer.value
        }
    }
}