package com.sserra.coffee.coffeepages

import android.annotation.SuppressLint
import com.sserra.coffee.R
import com.sserra.coffee.click
import com.sserra.coffee.coffeviews.CoffeeView
import com.sserra.coffee.coffeviews.ToolbarCoffeeView
import com.sserra.coffee.onViewWithDescription

abstract class PageWithToolbar<T>(private val toolbarId: Int, private val appBarId: Int) : Page<PageWithToolbar<T>>() {

    val appBar: CoffeeView<*> get() = CoffeeView<Any>(appBarId)
    val toolbar: ToolbarCoffeeView get() = ToolbarCoffeeView(toolbarId)

    @SuppressLint("PrivateResource")
    fun navigateUp(): PageWithToolbar<T> = apply { onViewWithDescription(R.string.abc_action_bar_up_description).click() }
}