package com.tencent.easyapp.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * Created by parrzhang on 2014/7/23.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    private AbsListView view;

    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setView(AbsListView view){
        this.view=view;
    }

    @Override
    public boolean canChildScrollUp() {
        return view.getFirstVisiblePosition()!=0;
    }
}
