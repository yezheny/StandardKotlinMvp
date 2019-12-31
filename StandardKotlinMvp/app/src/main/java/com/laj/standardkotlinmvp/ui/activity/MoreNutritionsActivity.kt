package com.laj.standardkotlinmvp.ui.activity

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.base.activity.BaseActivity
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.common.extensions.verticalLinearLayoutManager
import com.laj.standardkotlinmvp.model.NutritionalEntity
import com.laj.standardkotlinmvp.model.NutritionalInfoModel
import com.laj.standardkotlinmvp.presenter.IngredientsPresenter
import com.laj.standardkotlinmvp.presenter.views.IIngredientsView

/**
 * created by yezhengyu on 2019/7/28 14:43
 */
class MoreNutritionsActivity : BaseActivity<IIngredientsView, IngredientsPresenter>(), IIngredientsView {

    lateinit var mTvDesc: TextView
    lateinit var mTvFormTitle: TextView
    lateinit var mRv: RecyclerView

    private var mParams = hashMapOf<String, Any>()
    var mKid: Long = 0

    var mAdapter = NutritionsAdapter()

    override fun getLayout(): Int = R.layout.activity_more_nutritions

    override fun initView() {
        setTitle("营养元素")
        var tvShow = find<TextView>(R.id.tv_mod_btn)
        tvShow.setTextColor(ContextCompat.getColor(this, R.color.COLOR_41D282))
        tvShow.text = "符号说明"
        mTvDesc = find(R.id.more_nutritions_tv_desc)
        mTvFormTitle = find(R.id.more_nutritions_tv_form_title)
        mRv = find(R.id.more_nutritions_rv)
        mRv.layoutManager = verticalLinearLayoutManager()
        mRv.adapter = mAdapter
        mRv.isNestedScrollingEnabled = false
    }

    override fun initData() {
        mKid = intent.getLongExtra("kid", 0L)
        mParams["foodKid"] = mKid
    }

    override fun loadData() {
        mPresenter.getMoreNutritions(mParams)
    }

    override fun <K> showResponse(k: K, msg: Int) {
        if (k is NutritionalInfoModel) {
            mTvDesc.text = k.description
            mTvFormTitle.text = "${k.foodName}的成分表如下"
            var nutritionInfoList = k.foodCompInfoVOList
            if (!nutritionInfoList.isNullOrEmpty()) {
                mAdapter.setNewData(nutritionInfoList)
            }
        }
    }

    override fun getThis(): IIngredientsView = this

    class NutritionsAdapter: BaseQuickAdapter<NutritionalEntity, BaseViewHolder>(R.layout.item_nutrition_info) {

        override fun convert(helper: BaseViewHolder, item: NutritionalEntity) {
            if (item.alias.isNullOrEmpty()) {
                helper.setText(R.id.nutrition_info_tv_name, item.name)
            } else {
                helper.setText(R.id.nutrition_info_tv_name, item.alias)
            }
            helper.setText(R.id.nutrition_info_tv_unit, item.normalUnit)
            helper.setText(R.id.nutrition_info_tv_percentage, item.normalValue)
        }

    }
}