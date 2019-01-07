package com.sserra.coffee.coffeepages

import com.sserra.coffee.changeToLandscape
import com.sserra.coffee.changeToPortrait

abstract class Page {
    fun changeToLandscape(): Page = apply { changeToLandscape }
    fun changeToPortrait(): Page = apply { changeToPortrait }
}