package com.zwb.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by zwb
 * Description
 * Date 2017/5/22.
 */

public class SlidingMenu extends HorizontalScrollView {

    private int mScreenWidth;//屏幕的宽
    private int mMenuWidth;//菜单的宽度
    //dp 距离右边的宽度
    private int rightPadding = 50;

    private View viewMenu;
    private View viewContent;

    private boolean once;//是否第一次测量

    private boolean isOpen;//是否打开

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化数据
     */
    private void init(Context context) {
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        rightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            viewMenu = viewGroup.getChildAt(0);
            viewContent = viewGroup.getChildAt(1);
            mMenuWidth = viewMenu.getLayoutParams().width = mScreenWidth - rightPadding;
            viewContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //刚开始的是 l等于mMenuWidth，完全展开时l等于0
//        ViewHelper.setTranslationX(viewMenu, l);
        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;
        ViewHelper.setScaleX(viewMenu, leftScale);
        ViewHelper.setScaleY(viewMenu, leftScale);
        ViewHelper.setAlpha(viewMenu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(viewMenu, mMenuWidth * scale * 0.6f);

        ViewHelper.setPivotX(viewContent, 0);
        ViewHelper.setPivotY(viewContent, viewContent.getHeight() / 2);
        ViewHelper.setScaleX(viewContent, rightScale);
        ViewHelper.setScaleY(viewContent, rightScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int scrollX = getScrollX();
            if (scrollX >= mMenuWidth / 2) {
                smoothScrollTo(mMenuWidth, 0);
                isOpen = false;
            } else {
                smoothScrollTo(0, 0);
                isOpen = true;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void open() {
        if (!isOpen) {
            smoothScrollTo(0, 0);
            isOpen = true;
        }
    }

    /**
     * 关闭菜单
     */
    public void close() {
        if (isOpen) {
            smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    public boolean isOpen() {
        return isOpen;
    }
}
