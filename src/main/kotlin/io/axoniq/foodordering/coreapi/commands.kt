package io.axoniq.foodordering.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

typealias FoodCartId = UUID
typealias ProductId = UUID

data class CreateFoodCartCommand(
    @TargetAggregateIdentifier val foodCartId: FoodCartId
)

data class SelectProductCommand(
    @TargetAggregateIdentifier val foodCartId: FoodCartId,
    val productId: ProductId,
    val quantity: Int
)

data class DeselectProductCommand(
    @TargetAggregateIdentifier val foodCartId: FoodCartId,
    val productId: ProductId,
    val quantity: Int
)

data class ConfirmOrderCommand(
    @TargetAggregateIdentifier val foodCartId: FoodCartId
)
