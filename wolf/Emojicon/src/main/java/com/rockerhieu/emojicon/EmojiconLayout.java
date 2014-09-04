package com.rockerhieu.emojicon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atm.emojicon.R;
import com.com.rockerhieu.emojicon.emoji.Partition;
import com.com.rockerhieu.emojicon.emoji.QqClassic;


/**
 * Created by parrzhang on 2014/8/8.
 */
public class EmojiconLayout extends Fragment {
    private EmojiconFragment mEmoijFragment;
    private EmojiconFragment.OnEmojiconClickedListener mOnEmojiconClickedListener;
    private EmojiconFragment.OnEmojiconBackspaceClickedListener mOnEmojiconBackspaceClickedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emojicon_layout, null);

        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.emoij_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                Bundle bundle = new Bundle();
                EmojiconFragment fragment = new EmojiconFragment();
                if(checkId == R.id.emoij_radiobutton){
                    bundle.putSerializable(EmojiconFragment.EmojiIconData, Partition.DATA);
                }else if(checkId == R.id.qq_raidobutton){
                    bundle.putSerializable(EmojiconFragment.EmojiIconData,
                            QqClassic.DATA);
                }
                fragment.setArguments(bundle);
                changeFragment(fragment);
            }
        });
        mEmoijFragment = new EmojiconFragment();
        changeFragment(mEmoijFragment);
        return view;
    }


    public void setOnEmojiconClickedListener(EmojiconFragment.OnEmojiconClickedListener onEmojiconClickedListener) {
        this.mOnEmojiconClickedListener = onEmojiconClickedListener;
        if(mEmoijFragment!=null){
            mEmoijFragment.setOnEmojiconClickedListener(mOnEmojiconClickedListener);
        }
    }

    public void setOnEmojiconBackspaceClickedListener(EmojiconFragment.OnEmojiconBackspaceClickedListener onEmojiconBackspaceClickedListener) {
        this.mOnEmojiconBackspaceClickedListener = onEmojiconBackspaceClickedListener;
        if(mEmoijFragment!=null){
            mEmoijFragment.setOnEmojiconBackspaceClickedListener(mOnEmojiconBackspaceClickedListener);
        }
    }


    public EmojiconFragment getEmoijFragment() {
        return mEmoijFragment;
    }

    private void changeFragment(EmojiconFragment fragment) {
        mEmoijFragment = fragment;
        if(mOnEmojiconBackspaceClickedListener!=null){
            mEmoijFragment.setOnEmojiconBackspaceClickedListener(mOnEmojiconBackspaceClickedListener);
        }
        if(mOnEmojiconClickedListener != null){
            mEmoijFragment.setOnEmojiconClickedListener(mOnEmojiconClickedListener);
        }
        getFragmentManager().beginTransaction().replace(R.id.emoij_fragment, mEmoijFragment).commitAllowingStateLoss();
    }
}
