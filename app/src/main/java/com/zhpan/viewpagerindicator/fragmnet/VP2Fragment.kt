package com.zhpan.viewpagerindicator.fragmnet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.zhpan.indicator.annotation.AIndicatorOrientation
import com.zhpan.indicator.annotation.AIndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorOrientation
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import com.zhpan.indicator.utils.IndicatorUtils
import com.zhpan.viewpagerindicator.R
import com.zhpan.viewpagerindicator.adapter.ViewPager2Adapter
import kotlinx.android.synthetic.main.fragment_viewpager2.*

/**
 * @author zhangpan
 * @date 2020/11/23
 */
class VP2Fragment : BaseFragment() {

    @AIndicatorSlideMode
    private var mSlideMode = IndicatorSlideMode.SMOOTH
    private var mCheckId = R.id.rb_circle
    private var normalWidth: Float = 0f
    private var checkedWidth: Float = 0f

    @AIndicatorOrientation
    private var orientation: Int = IndicatorOrientation.INDICATOR_HORIZONTAL

    @androidx.annotation.ColorInt
    private var normalColor: Int = 0

    @androidx.annotation.ColorInt
    private var checkedColor: Int = 0


    companion object {
        @JvmStatic
        fun getInstance(): VP2Fragment {
            return VP2Fragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dp12 = resources.getDimensionPixelOffset(R.dimen.dp_12)
        val dp20 = resources.getDimensionPixelOffset(R.dimen.dp_20)
        val dp10 = resources.getDimension(R.dimen.dp_10)
        view_pager2.adapter = ViewPager2Adapter(getData(4))
        figureIndicator.setRadius(resources.getDimensionPixelOffset(R.dimen.dp_20))
        figureIndicator.setTextSize(resources.getDimensionPixelSize(R.dimen.dp_16))
                .setupWithViewPager(view_pager2)
        figureIndicator.setBackgroundColor(Color.parseColor("#aa118EEA"))
        normalColor = ContextCompat.getColor(context!!, R.color.red_normal_color)
        checkedColor = ContextCompat.getColor(context!!, R.color.red_checked_color)
        normalWidth = dp20.toFloat()
        checkedWidth = normalWidth
        drawableIndicator.apply {
            setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.dp_2_5))
            setIndicatorDrawable(R.drawable.heart_empty, R.drawable.heart_red)
            setIndicatorSize(dp20, dp20, dp20, dp20)
            setupWithViewPager(view_pager2)
        }

        vectorIndicator.apply {
            setIndicatorGap(resources.getDimensionPixelOffset(R.dimen.dp_2_5))
            setIndicatorDrawable(R.drawable.banner_indicator_nornal, R.drawable.banner_indicator_focus)
            setIndicatorSize(dp12, dp12, resources.getDimensionPixelOffset(R.dimen.dp_26), dp12)
            setupWithViewPager(view_pager2)
        }

        indicatorView.apply {
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(dp10, dp10)
            setSliderHeight(resources.getDimension(R.dimen.dp_5))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setOrientation(orientation)
            setupWithViewPager(view_pager2)
        }
        initRadioGroup()

//        indicatorView.setPageSize(view_pager2!!.adapter!!.itemCount)
//        indicatorView.notifyDataChanged()
//        view_pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                indicatorView.onPageScrolled(position, positionOffset, positionOffsetPixels)
//            }
//
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                indicatorView.onPageSelected(position)
//            }
//        })

    }


    private fun initRadioGroup() {
        rbOrientation.setOnCheckedChangeListener { _, checkedId -> changeOrientation(checkedId) }
        radioGroupStyle.setOnCheckedChangeListener { _, checkedId -> checkedChange(checkedId) }
        radioGroupMode.setOnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
            when (checkedId) {
                R.id.rb_normal -> {
                    normalWidth = resources.getDimension(R.dimen.dp_20)
                    checkedWidth = normalWidth;
                    mSlideMode = IndicatorSlideMode.NORMAL
                }
                R.id.rb_worm -> {
                    normalWidth = resources.getDimension(R.dimen.dp_20)
                    checkedWidth = normalWidth;
                    mSlideMode = IndicatorSlideMode.WORM
                }
                R.id.rb_smooth -> {
                    normalWidth = resources.getDimension(R.dimen.dp_20)
                    checkedWidth = normalWidth;
                    mSlideMode = IndicatorSlideMode.SMOOTH
                }
                R.id.rb_scale -> {
                    normalWidth = resources.getDimension(R.dimen.dp_20)
                    checkedWidth = normalWidth * 1.2.toFloat()
                    mSlideMode = IndicatorSlideMode.SCALE
                }
                R.id.rb_color -> {
                    normalWidth = resources.getDimension(R.dimen.dp_20)
                    checkedWidth = normalWidth * 1.2.toInt()
                    mSlideMode = IndicatorSlideMode.COLOR
                }
            }
            checkedChange(mCheckId)
        }
        rb_circle.performClick()
    }

    private fun changeOrientation(checkedId: Int) {
        when (checkedId) {
            R.id.rb_horizontal -> {
                orientation = IndicatorOrientation.INDICATOR_HORIZONTAL
            }
            R.id.rb_vertical -> {
                orientation = IndicatorOrientation.INDICATOR_VERTICAL
            }
            R.id.rb_rtl -> {
                orientation = IndicatorOrientation.INDICATOR_RTL
            }
        }
        checkedChange(mCheckId)
    }

    private fun checkedChange(checkedId: Int) {
        mCheckId = checkedId
        when (checkedId) {
            R.id.rb_circle -> setupCircleIndicator()
            R.id.rb_dash -> setupDashIndicator()
            R.id.rb_round_rect -> setupRoundRectIndicator()
        }
    }

    private fun setupRoundRectIndicator() {
        if (mSlideMode == IndicatorSlideMode.SCALE || mSlideMode == IndicatorSlideMode.NORMAL) {
            normalWidth = resources.getDimension(R.dimen.dp_8)
            checkedWidth = resources.getDimension(R.dimen.dp_20)
        } else {
            normalWidth = checkedWidth
        }
        indicatorView.apply {
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setSliderGap(IndicatorUtils.dp2px(4f).toFloat())
            setSlideMode(mSlideMode)
            setSliderHeight(resources.getDimensionPixelOffset(R.dimen.dp_6).toFloat())
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(normalWidth, checkedWidth)
            setOrientation(orientation)
            notifyDataChanged()
        }
    }

    private fun setupDashIndicator() {
        if (mSlideMode == IndicatorSlideMode.SCALE || mSlideMode == IndicatorSlideMode.NORMAL) {
            normalWidth = resources.getDimension(R.dimen.dp_12)
            checkedWidth = resources.getDimension(R.dimen.dp_20)
        } else {
            normalWidth = checkedWidth
        }
        indicatorView.apply {
            setIndicatorStyle(IndicatorStyle.DASH)
            setSliderHeight(resources.getDimensionPixelOffset(R.dimen.dp_6).toFloat())
            setSlideMode(mSlideMode)
            setSliderGap(resources.getDimension(R.dimen.dp_6))
            setSliderWidth(normalWidth, checkedWidth)
            setSliderColor(normalColor, checkedColor)
            setOrientation(orientation)
            notifyDataChanged()
        }
    }

    private fun setupCircleIndicator() {
        if (mSlideMode == IndicatorSlideMode.SCALE || mSlideMode == IndicatorSlideMode.NORMAL) {
            normalWidth = resources.getDimension(R.dimen.dp_16)
            checkedWidth = normalWidth * 1.4.toFloat()
        } else {
            normalWidth = checkedWidth
        }

        indicatorView.apply {
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setSliderGap(resources.getDimension(R.dimen.dp_6))
            setSlideMode(mSlideMode)
            setSliderHeight(resources.getDimension(R.dimen.dp_8))
            setSliderColor(normalColor, checkedColor)
            setSliderWidth(normalWidth, checkedWidth)
            setOrientation(orientation)
            notifyDataChanged()
        }
    }
}