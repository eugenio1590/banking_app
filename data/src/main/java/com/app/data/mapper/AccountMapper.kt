package com.app.data.mapper

import com.app.domain.model.Account
import com.app.data.base.entity.Account as Entity

/**
 * Internal class responsible for mapping between account data entities and domain models.
 *
 * This class provides methods for converting instances of the data entity class [Entity]
 * to corresponding domain models of class [Account], and vice versa.
 */
internal class AccountMapper {

    /**
     * Converts a data entity [Entity] to a domain model [Account].
     *
     * @param entity The data entity to be converted.
     * @return The corresponding domain model [Account].
     */
    fun toModel(entity: Entity): Account = Account(
        id = entity.id,
        type = Account.Type.valueOf(entity.type),
        balance = entity.balance
    )

    /**
     * Converts a domain model [Account] to a data entity [Entity].
     *
     * @param account The domain model [Account] to be converted.
     * @return The corresponding data entity [Entity].
     */
    fun toEntity(account: Account): Entity = Entity(
        id = account.id,
        type = account.type.name,
        balance = account.balance
    )
}
