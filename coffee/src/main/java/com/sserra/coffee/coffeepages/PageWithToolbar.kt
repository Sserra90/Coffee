package com.sserra.coffee.coffeepages

import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.ToolbarCoffeeView

abstract class PageWithToolbar(private val toolbarId: Int, private val appBarId: Int) : Page() {
    fun appBar(block: CoffeeView.() -> Unit = {}): CoffeeView = CoffeeView(appBarId, block)
    fun toolbar(block: ToolbarCoffeeView.() -> Unit = {}): ToolbarCoffeeView =
            ToolbarCoffeeView(toolbarId, block)
}