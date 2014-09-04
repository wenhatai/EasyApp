package com.rockerhieu.emojicon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atm.emojicon.R;
import com.com.rockerhieu.emojicon.emoji.Emojicon;
import com.com.rockerhieu.emojicon.emoji.QqClassic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by parrzhang on 2014/8/8.
 */
public class EmojiconFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final String EmojiIconData = "Emoji";

    private ViewPager mViewPager;
    private List<Emojicon> mData;
    private List<List<Emojicon>> mEmoijLists;
    private LinearLayout mMarkLayout;
    private List<ImageView> mMarkImageViews;
    private Emojicon mDeleteEmoij;
    private final int mNumRows = 3;//显示3行
    private final int mNumColumns = 7;//显示列

    private OnEmojiconClickedListener mOnEmojiconClickedListener;
    private OnEmojiconBackspaceClickedListener mOnEmojiconBackspaceClickedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle == null || bundle.getSerializable(EmojiIconData) == null) {
            mData = Arrays.asList(QqClassic.DATA);
        } else {
            Emojicon[] o = (Emojicon[]) getArguments().getSerializable(EmojiIconData);
            mData = Arrays.asList(o);
        }
        mDeleteEmoij = Emojicon.fromChar(EmojiconHandler.DeleteCode);
        View view = inflater.inflate(R.layout.emojicon_grid, null);
        mViewPager = (ViewPager) view.findViewById(R.id.emoij_grid_pager);
        mMarkLayout = (LinearLayout) view.findViewById(R.id.emoij_mark_layout);
        init();
        return view;
    }

    private void init() {
        int size = mNumRows * mNumColumns - 1;
        int length = mData.size() / size;
        mEmoijLists = new ArrayList<List<Emojicon>>(length);
        mMarkImageViews = new ArrayList<ImageView>(length);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int markSize = (int) getResources().getDimension(R.dimen.emoij_mark_size);
        layoutParams.height = markSize;
        layoutParams.width = markSize;
        layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.emoij_mark_marign);
        for (int i = 0; i < length; i++) {
            int start = i * size;
            int end = (i + 1) * size;
            end = end > mData.size() ? mData.size() : end;
            List<Emojicon> emojicons = getData(start, end);
            mEmoijLists.add(emojicons);
            ImageView markImageView = new ImageView(getActivity());
            if (i == 0) {
                markImageView.setImageResource(R.drawable.face_mark_press);
            } else {
                markImageView.setImageResource(R.drawable.face_mark_unpress);
            }
            markImageView.setLayoutParams(layoutParams);
            mMarkLayout.addView(markImageView);
            mMarkImageViews.add(markImageView);
        }
        mViewPager.setAdapter(new EmoijsViewPager());
        mViewPager.setOnPageChangeListener(new OnPagerChange());
    }

    private List<Emojicon> getData(int start, int end) {
        List<Emojicon> emojicons = new ArrayList<Emojicon>(end - start + 1);
        for (int i = start; i < end; i++) {
            emojicons.add(mData.get(i));
        }
        emojicons.add(mDeleteEmoij);
        return emojicons;
    }

    private class EmoijsViewPager extends PagerAdapter {

        @Override
        public int getCount() {
            return mEmoijLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GridView gridView = (GridView) LayoutInflater.from(getActivity()).inflate(R.layout.emojicon_grid_item, null);
            List<Emojicon> emojicons = mEmoijLists.get(position);
            gridView.setAdapter(new EmojiAdapter(getActivity(), emojicons.toArray(new Emojicon[emojicons.size()])));
            gridView.setOnItemClickListener(EmojiconFragment.this);
            container.addView(gridView);
            return gridView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class OnPagerChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mMarkImageViews.size(); i++) {
                if (position != i) {
                    mMarkImageViews.get(i).setImageResource(R.drawable.face_mark_unpress);
                } else {
                    mMarkImageViews.get(i).setImageResource(R.drawable.bg_face_pressed);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Emojicon emojicon = (Emojicon) parent.getItemAtPosition(position);
        if (mDeleteEmoij.equals(emojicon)) {
            if (mOnEmojiconBackspaceClickedListener != null) {
                mOnEmojiconBackspaceClickedListener.onEmojiconBackspaceClicked();
            }
        } else {
            if (mOnEmojiconClickedListener != null) {
                mOnEmojiconClickedListener.onEmojiconClicked(emojicon);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mOnEmojiconClickedListener == null) {
            if (activity instanceof OnEmojiconClickedListener) {
                mOnEmojiconClickedListener = (OnEmojiconClickedListener) activity;
            } else if (getParentFragment() instanceof OnEmojiconClickedListener) {
                mOnEmojiconClickedListener = (OnEmojiconClickedListener) getParentFragment();
            }
        }
        if (mOnEmojiconBackspaceClickedListener == null) {
            if (getActivity() instanceof OnEmojiconBackspaceClickedListener) {
                mOnEmojiconBackspaceClickedListener = (OnEmojiconBackspaceClickedListener) getActivity();
            } else if (getParentFragment() instanceof OnEmojiconBackspaceClickedListener) {
                mOnEmojiconBackspaceClickedListener = (OnEmojiconBackspaceClickedListener) getParentFragment();
            }
        }
    }

    public void setOnEmojiconClickedListener(OnEmojiconClickedListener onEmojiconClickedListener) {
        this.mOnEmojiconClickedListener = onEmojiconClickedListener;
    }

    public void setOnEmojiconBackspaceClickedListener(OnEmojiconBackspaceClickedListener onEmojiconBackspaceClickedListener) {
        this.mOnEmojiconBackspaceClickedListener = onEmojiconBackspaceClickedListener;
    }

    public OnEmojiconBackspaceClickedListener getOnEmojiconBackspaceClickedListener() {
        return mOnEmojiconBackspaceClickedListener;
    }

    public OnEmojiconClickedListener getOnEmojiconClickedListener() {
        return mOnEmojiconClickedListener;
    }

    @Override
    public void onDetach() {
        mOnEmojiconClickedListener = null;
        mOnEmojiconBackspaceClickedListener = null;
        super.onDetach();
    }

    public interface OnEmojiconClickedListener {
        void onEmojiconClicked(Emojicon emojicon);
    }

    public interface OnEmojiconBackspaceClickedListener {
        void onEmojiconBackspaceClicked();
    }
}
