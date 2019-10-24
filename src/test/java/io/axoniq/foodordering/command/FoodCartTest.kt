package io.axoniq.foodordering.command

import io.axoniq.foodordering.api.CreateFoodCartCommand
import io.axoniq.foodordering.api.DeselectProductCommand
import io.axoniq.foodordering.api.FoodCartCreatedEvent
import io.axoniq.foodordering.api.ProductDeselectedEvent
import io.axoniq.foodordering.api.ProductDeselectionException
import io.axoniq.foodordering.api.ProductSelectedEvent
import io.axoniq.foodordering.api.SelectProductCommand
import org.assertj.core.api.Assertions.assertThat
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.ResultValidator
import org.axonframework.test.aggregate.TestExecutor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID


class FoodCartTest {

    private lateinit var foodCart: FixtureConfiguration<FoodCart>

    @BeforeEach
    fun setUp() {
        foodCart = AggregateTestFixture(FoodCart::class.java)
    }

    @Test
    fun `should create food cart`() {
        val foodCartId = UUID.randomUUID()!!
        foodCart.givenNoPriorActivity()
            .WHEN(CreateFoodCartCommand(foodCartId))
            .expectSuccessfulHandlerExecution()
            .expectEvents(FoodCartCreatedEvent(foodCartId))
    }

    @Test
    fun `should add product`() {
        val foodCartId = UUID.randomUUID()!!
        val productId = UUID.randomUUID()!!
        foodCart.GIVEN(FoodCartCreatedEvent(foodCartId))
            .WHEN(SelectProductCommand(foodCartId, productId, 1))
            .expectSuccessfulHandlerExecution()
            .expectEvents(ProductSelectedEvent(foodCartId, productId, 1))
            .expectState {
                assertThat(it?.selectedProducts).containsKey(productId)
            }
    }

    @Test
    fun `should sum selected products`() {
        val foodCartId = UUID.randomUUID()!!
        val productId = UUID.randomUUID()!!
        foodCart.GIVEN(FoodCartCreatedEvent(foodCartId), ProductSelectedEvent(foodCartId, productId, 1))
            .WHEN(SelectProductCommand(foodCartId, productId, 3))
            .expectSuccessfulHandlerExecution()
            .expectState {
                it as FoodCart
                assertThat(it.selectedProducts[productId]).isEqualTo(4)
            }
    }

    @Test
    fun `should subtract deselected products`() {
        val foodCartId = UUID.randomUUID()!!
        val productId = UUID.randomUUID()!!
        foodCart.GIVEN(FoodCartCreatedEvent(foodCartId), ProductSelectedEvent(foodCartId, productId, 1))
            .WHEN(DeselectProductCommand(foodCartId, productId, 1))
            .expectSuccessfulHandlerExecution()
            .expectEvents(ProductDeselectedEvent(foodCartId, productId, 1))
            .expectState {
                it as FoodCart
                assertThat(it.selectedProducts[productId]).isEqualTo(0)
            }
    }

    @Test
    fun `should fail to deselected products not in food cart`() {
        val foodCartId = UUID.randomUUID()!!
        val productId = UUID.randomUUID()!!
        foodCart.GIVEN(FoodCartCreatedEvent(foodCartId))
            .WHEN(DeselectProductCommand(foodCartId, productId, 1))
            .expectException(ProductDeselectionException::class.java)
    }
}

fun <T> FixtureConfiguration<T>.GIVEN(vararg event: Any): TestExecutor<T> = this.given(*event)
fun <T> TestExecutor<T>.WHEN(command: Any): ResultValidator<T> = this.`when`(command)
