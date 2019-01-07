package com.sserra.coffee.coffeepages

import android.annotation.SuppressLint
import com.sserra.coffee.R
import com.sserra.coffee.click
import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.ToolbarCoffeeView
import com.sserra.coffee.onViewWithDescription

abstract class PageWithToolbar(private val toolbarId: Int, private val appBarId: Int) : Page() {
    fun appBar(block: CoffeeView.() -> Unit = {}): CoffeeView = CoffeeView(appBarId, block)
    fun toolbar(block: ToolbarCoffeeView.() -> Unit = {}): ToolbarCoffeeView =
            ToolbarCoffeeView(toolbarId, block)

    @SuppressLint("PrivateResource")
    fun navigateUp(): Page = apply { onViewWithDescription(R.string.abc_action_bar_up_description).click() }
}