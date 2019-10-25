package io.axoniq.foodordering.query

import io.axoniq.foodordering.api.FindFoodCartQuery
import io.axoniq.foodordering.api.FoodCartCreatedEvent
import io.axoniq.foodordering.api.ProductDeselectedEvent
import io.axoniq.foodordering.api.ProductId
import io.axoniq.foodordering.api.ProductSelectedEvent
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

import java.util.Collections

@Component
internal class FoodCartProjector(private val foodCartViewRepository: FoodCartViewRepository) {

    @EventHandler
    fun on(event: FoodCartCreatedEvent) {
        val foodCartView = FoodCartView(event.foodCartId, mutableMapOf())
        foodCartViewRepository.save(foodCartView)
    }

    @EventHandler
    fun on(event: ProductSelectedEvent) {
        foodCartViewRepository.findById(event.foodCartId)
            .ifPresent { foodCartView -> foodCartView.addProducts(event.productId, event.quantity) }
    }

    @EventHandler
    fun on(event: ProductDeselectedEvent) {
        foodCartViewRepository.findById(event.foodCartId)
            .ifPresent { foodCartView -> foodCartView.removeProducts(event.productId, event.quantity) }
    }

    @QueryHandler
    fun handle(query: FindFoodCartQuery): FoodCartView {
        return foodCartViewRepository.findById(query.foodCartId).orElse(null)
    }
}
