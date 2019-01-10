package com.sserra.coffee.coffeviews

import android.view.View
import androidx.test.espresso.assertion.ViewAssertions
import com.sserra.coffee.machers.LayoutManager
import com.sserra.coffee.machers.RecyclerViewMatcher
import com.sserra.coffee.machers.withLayoutManager
import com.sserra.coffee.onViewById
import com.sserra.coffee.scrollRecyclerToPos
import junit.framework.Assert.fail
import org.hamcrest.Matcher
import kotlin.reflect.KClass

class ItemsFactory(block: ItemsFactory.() -> Unit) {
    val items: Items = Items()

    init {
        block()
    }

    inline fun <reified T> add(noinline view: (matcher: Matcher<View>) -> BaseCoffeeView<*>) {
        items[T::class] = view
    }
}

class Items : HashMap<KClass<*>, (matcher: Matcher<View>) -> BaseCoffeeView<*>>()

class RecyclerCoffeeView(val id: Int, block: ItemsFactory.() -> Unit) : BaseCoffeeView<RecyclerCoffeeView>(onViewById(id)) {

    val itemsFactory: ItemsFactory = ItemsFactory(block)

    val recyclerViewMatcher: RecyclerViewMatcher = RecyclerViewMatcher(id)

    val itemCount: RecyclerCoffeeView
        get() = apply {
            check = Check.ItemCount
        }

    val usingGridLayoutManager: RecyclerCoffeeView
        get() = apply {
            viewInteraction.check(ViewAssertions.matches(withLayoutManager(LayoutManager.GRID)))
        }

    val usingLinearLayoutManager: RecyclerCoffeeView
        get() = apply {
            viewInteraction.check(ViewAssertions.matches(withLayoutManager(LayoutManager.LINEAR)))
        }

    fun scrollToPos(pos: Int): RecyclerCoffeeView = apply { viewInteraction.scrollRecyclerToPos(pos) }

    fun scrollToTop(): RecyclerCoffeeView = apply {
        scrollToPos(0)
    }

    inline fun <reified T : BaseCoffeeView<*>> atPos(pos: Int, block: T.() -> Unit) {
        val factory = itemsFactory.items[T::class]
        if (factory == null) {
            fail("No view provided for ${T::class}")
        }

        val matcher = recyclerViewMatcher.atPosition(pos)
        val view = factory!!.invoke(matcher) as T
        block.invoke(view)
    }
}
