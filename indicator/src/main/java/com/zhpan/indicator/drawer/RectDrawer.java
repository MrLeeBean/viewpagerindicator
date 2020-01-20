package com.zhpan.indicator.drawer;

import android.graphics.Canvas;

import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.model.IndicatorOptions;
import com.zhpan.indicator.utils.IndicatorUtils;

/**
 * <pre>
 *   Created by zhpan on 2020/1/17.
 *   Description:
 * </pre>
 */
public class RectDrawer extends BaseDrawer {

    RectDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int pageSize = mIndicatorOptions.getPageSize();
        if (pageSize > 1) {
            if (isWidthEquals() && mIndicatorOptions.getSlideMode() != IndicatorSlideMode.NORMAL) {
                drawUncheckedSlider(canvas, pageSize);
                drawCheckedSlider(canvas);
            } else {    // 单独处理normalWidth与checkedWidth不一致的情况
                for (int i = 0; i < pageSize; i++) {
                    drawInequalitySlider(canvas, i);
                }
            }
        }
    }

    private void drawUncheckedSlider(Canvas canvas, int pageSize) {
        for (int i = 0; i < pageSize; i++) {
            mPaint.setColor(mIndicatorOptions.getNormalColor());
            float sliderHeight = mIndicatorOptions.getSliderHeight();
            float left = i * (maxWidth) + i * +mIndicatorOptions.getIndicatorGap() + (maxWidth - minWidth);
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private void drawInequalitySlider(Canvas canvas, int i) {
        int normalColor = mIndicatorOptions.getNormalColor();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        if (i < currentPosition) {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap;
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        } else if (i == currentPosition) {
            mPaint.setColor(mIndicatorOptions.getCheckedColor());
            float left = i * minWidth + i * indicatorGap;
            mRectF.set(left, 0, left + minWidth + (maxWidth - minWidth), sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        } else {
            mPaint.setColor(normalColor);
            float left = i * minWidth + i * indicatorGap + (maxWidth - minWidth);
            mRectF.set(left, 0, left + minWidth, sliderHeight);
            drawRoundRect(canvas, sliderHeight, sliderHeight);
        }
    }

    private void drawCheckedSlider(Canvas canvas) {
        mPaint.setColor(mIndicatorOptions.getCheckedColor());
        switch (mIndicatorOptions.getSlideMode()) {
            case IndicatorSlideMode.SMOOTH:
                drawSmoothSlider(canvas);
                break;
            case IndicatorSlideMode.WORM:
                drawWormSlider(canvas);
                break;
        }
    }

    private void drawWormSlider(Canvas canvas) {
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float slideProgress = mIndicatorOptions.getSlideProgress();
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float distance = mIndicatorOptions.getIndicatorGap() + mIndicatorOptions.getNormalIndicatorWidth();
        float startCoordinateX = IndicatorUtils.getCoordinateX(mIndicatorOptions, maxWidth, currentPosition);
        float left = startCoordinateX + Math.max(distance * (slideProgress - 0.5f) * 2.0f, 0) - mIndicatorOptions.getNormalIndicatorWidth() / 2;
        float right = startCoordinateX + Math.min((distance * slideProgress * 2), distance) + mIndicatorOptions.getNormalIndicatorWidth() / 2;
        mRectF.set(left, 0, right, sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    private void drawSmoothSlider(Canvas canvas) {
        int currentPosition = mIndicatorOptions.getCurrentPosition();
        float indicatorGap = mIndicatorOptions.getIndicatorGap();
        float sliderHeight = mIndicatorOptions.getSliderHeight();
        float left = currentPosition * (maxWidth) + currentPosition * +indicatorGap + (maxWidth + indicatorGap) * mIndicatorOptions.getSlideProgress();
        mRectF.set(left, 0, left + maxWidth, sliderHeight);
        drawRoundRect(canvas, sliderHeight, sliderHeight);
    }

    protected void drawRoundRect(Canvas canvas, float rx, float ry) {
        drawDash(canvas);
    }

    protected void drawDash(Canvas canvas) {
    }
}
