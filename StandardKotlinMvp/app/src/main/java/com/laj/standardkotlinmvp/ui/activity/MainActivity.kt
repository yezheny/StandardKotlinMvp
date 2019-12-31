package com.laj.standardkotlinmvp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.fresco.helper.ImageLoader
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.common.extensions.startActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sdv = find<SimpleDraweeView>(R.id.sdv_helloworld)
        ImageLoader.loadImage(
            sdv,
            "https://cdn.lajsf.com/nutrition-plan/image/default/common/488986194345989.jpg?w=750&h=722"
        )
        sdv.setOnClickListener {
            var kid: Long = 469484041527583
            startActivity<MoreNutritionsActivity>("kid" to kid)
        }
    }
}
