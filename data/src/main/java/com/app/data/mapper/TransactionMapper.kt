package com.app.data.mapper

import com.app.domain.model.Transaction
import java.util.Date
import com.app.data.base.entity.Transaction as Entity

/**
 * Internal class responsible for mapping between transaction data entities and domain models.
 *
 * This class provides methods for converting instances of the data entity class [Entity]
 * to corresponding domain models of class [Transaction] objects, and vice versa.
 */
internal class TransactionMapper {

    /**
     * Converts a data entity [Entity] to a domain model [Transaction].
     *
     * @param entity The data entity to be converted.
     * @return The corresponding domain model [Transaction].
     */
    fun toModel(entity: Entity): Transaction = Transaction(
        type = Transaction.Type.valueOf(entity.type),
        amount = entity.amount
    )

    /**
     * Converts a domain model [Transaction] to a data entity [Entity].
     *
     * @param transaction The domain model [Transaction] to be converted.
     * @return The corresponding data entity [Entity].
     */

    fun toEntity(transaction: Transaction): Entity = Entity(
        type = transaction.type.name,
        amount = transaction.amount,
        date = Date(),
        accountId = transaction.account?.id
    )
}
