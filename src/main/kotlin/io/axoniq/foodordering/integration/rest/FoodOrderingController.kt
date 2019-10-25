package io.axoniq.foodordering.integration.rest

import io.axoniq.foodordering.api.CreateFoodCartCommand
import io.axoniq.foodordering.api.DeselectProductCommand
import io.axoniq.foodordering.api.FindFoodCartQuery
import io.axoniq.foodordering.api.FoodCartId
import io.axoniq.foodordering.api.ProductId
import io.axoniq.foodordering.api.SelectProductCommand
import io.axoniq.foodordering.query.FoodCartView
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.UUID
import java.util.concurrent.CompletableFuture

@RequestMapping("/foodCart")
@RestController
internal class FoodOrderingController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway
) {

    @PostMapping("/create")
    fun createFoodCart(): CompletableFuture<UUID> = commandGateway.send(CreateFoodCartCommand(UUID.randomUUID()))

    @PostMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    fun selectProduct(
        @PathVariable("foodCartId") foodCartId: String,
        @PathVariable("productId") productId: String,
        @PathVariable("quantity") quantity: Int?
    ) =
        commandGateway.send<Any>(
            SelectProductCommand(
                UUID.fromString(foodCartId),
                UUID.fromString(productId),
                quantity!!
            )
        )

    @PostMapping("/{foodCartId}/deselect/{productId}/quantity/{quantity}")
    fun deselectProduct(
        @PathVariable("foodCartId") foodCartId: String,
        @PathVariable("productId") productId: String,
        @PathVariable("quantity") quantity: Int?
    ) =
        commandGateway.sendAndWait<Any>(
            DeselectProductCommand(
                UUID.fromString(foodCartId),
                UUID.fromString(productId),
                quantity!!
            )
        )

    @GetMapping("/{foodCartId}")
    fun findFoodCart(@PathVariable("foodCartId") foodCartId: String): CompletableFuture<FoodCartView> =
        queryGateway.query(
            FindFoodCartQuery(UUID.fromString(foodCartId)),
            ResponseTypes.instanceOf(FoodCartView::class.java)
        )
}
