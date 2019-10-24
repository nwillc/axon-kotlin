package io.axoniq.foodordering.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

class CreateFoodCartCommand(
        @TargetAggregateIdentifier val foodCartId: UUID
)

data class SelectProductCommand(
        @TargetAggregateIdentifier val foodCartId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class DeselectProductCommand(
        @TargetAggregateIdentifier val foodCartId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class ConfirmOrderCommand(
        @TargetAggregateIdentifier val foodCartId: UUID
)
