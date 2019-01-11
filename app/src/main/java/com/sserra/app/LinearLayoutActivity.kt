package com.sserra.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.scroll_activity.*

class RelativeLayoutActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) = ctx.startActivity(Intent(ctx, RelativeLayoutActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.relative_layout_activity)
    }
}

class LinearLayoutActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) = ctx.startActivity(Intent(ctx, LinearLayoutActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linear_layout_activity)
    }
}