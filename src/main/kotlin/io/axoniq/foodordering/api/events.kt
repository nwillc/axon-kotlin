package io.axoniq.foodordering.api

data class FoodCartCreatedEvent(
        val foodCartId: FoodCartId
)

data class ProductSelectedEvent(
        val foodCartId: FoodCartId,
        val productId: ProductId,
        val quantity: Int
)

data class ProductDeselectedEvent(
        val foodCartId: FoodCartId,
        val productId: ProductId,
        val quantity: Int
)

data class OrderConfirmedEvent(
        val foodCartId: FoodCartId
)
