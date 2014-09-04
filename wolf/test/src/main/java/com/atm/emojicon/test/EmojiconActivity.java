package com.atm.emojicon.test;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.com.rockerhieu.emojicon.emoji.Emojicon;
import com.example.main.R;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconFragment;
import com.rockerhieu.emojicon.EmojiconLayout;

/**
 * Created by limingzhang on 2014/9/4.
 */
public class EmojiconActivity extends FragmentActivity implements
        EmojiconFragment.OnEmojiconBackspaceClickedListener,
        EmojiconFragment.OnEmojiconClickedListener{
    private EmojiconEditText emojiconEditText;
    private EmojiconLayout mEmojiconLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emojicon);
        emojiconEditText = (EmojiconEditText)findViewById(R.id.post_comment_text);
        mEmojiconLayout = new EmojiconLayout();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentlayout, mEmojiconLayout)
                .commitAllowingStateLoss();
    }

    @Override
    public void onEmojiconBackspaceClicked() {
        emojiconEditText.backspace();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        emojiconEditText.input(emojicon);
    }
}
