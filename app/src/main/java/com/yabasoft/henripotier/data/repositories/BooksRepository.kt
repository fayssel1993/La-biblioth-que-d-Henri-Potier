package com.yabasoft.henripotier.data.repositories

import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.entities.CommercialOffers
import com.yabasoft.henripotier.data.local.dao.BookDao
import com.yabasoft.henripotier.data.remote.api.HenriPotierAPI
import com.yabasoft.henripotier.utils.BaseApiResponse
import com.yabasoft.henripotier.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
class BooksRepository(
    private val api: HenriPotierAPI,
    private val dao: BookDao
) : BaseApiResponse() {

    suspend fun getAllBooks(): Flow<NetworkResult<List<Book>>> {
        return flow {
            emit(safeApiCall { api.getAllBooks() })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getCommercialOffers(ids: String): Flow<NetworkResult<CommercialOffers>> {
        return flow {
            emit(safeApiCall { api.getCommercialOffers(ids) })
        }.flowOn(Dispatchers.IO)
    }


    suspend fun save(book: Book) = dao.save(book)

    suspend fun delete(book: Book) = dao.delete(book)


    fun getAllBooksInCart() = dao.getAll()

    @Deprecated("not used after deadline")
    suspend fun findAllIsbnCode() = dao.findAllIsbnCode()

}