package com.app.data.source.transaction

import com.app.data.base.dao.TransactionDao
import com.app.data.mapper.TransactionMapper
import com.app.data.util.toDate
import com.app.domain.model.Account
import com.app.domain.model.Transaction
import com.app.domain.repository.TransactionRepository
import java.time.YearMonth

/**
 * Internal class implementing the [TransactionRepository] interface.
 *
 * This class serves as a data source for financial operations, utilizing an [TransactionMapper]
 * for mapping between data entities and domain models and an [TransactionDao] for database interaction.
 *
 * @property transactionMapper The mapper responsible for converting between transaction data entities and domain models.
 * @property transactionDao The data access object (DAO) providing methods for interacting with the transactionn database.
 */
internal class TransactionDataSource(
    private val transactionMapper: TransactionMapper = TransactionMapper(),
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun findBy(account: Account, page: Int, limit: Int): List<Transaction> {
        val offset = (page - 1) * limit
        val entities = transactionDao.findBy(account.id, limit, offset)
        return entities.map(transactionMapper::toModel)
    }

    override suspend fun findBy(account: Account, date: YearMonth): List<Transaction> {
        val start = date.atDay(1).toDate()
        val end = date.atEndOfMonth().toDate()
        val entities = transactionDao.findBy(account.id, start, end)
        return entities.map(transactionMapper::toModel)
    }

    override suspend fun add(transaction: Transaction): Boolean {
        // Just to be sure that the transaction has an associated account before recording
        assert(transaction.account != null) {
            "Account must not be null when recording a transaction"
        }
        val entity = transactionMapper.toEntity(transaction)
        transactionDao.insert(entity)
        return true
    }
}
