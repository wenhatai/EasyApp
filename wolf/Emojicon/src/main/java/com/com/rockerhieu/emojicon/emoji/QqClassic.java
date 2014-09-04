package com.com.rockerhieu.emojicon.emoji;

import com.atm.emojicon.R;

/**
 * Created by parrzhang on 2014/8/6.
 * qq 经典表情
 */
public class QqClassic {
    public static final char EmoijCode = 0xee14;

    public static final int Size = 140;

    public static final Emojicon[] DATA = new Emojicon[Size];
    public static final int[] EmoijDrawable = new int[]{R.drawable.face_qq0,
            R.drawable.face_qq1,R.drawable.face_qq2,R.drawable.face_qq3,R.drawable.face_qq4, R.drawable.face_qq5,R.drawable.face_qq6,R.drawable.face_qq7,R.drawable.face_qq8,
            R.drawable.face_qq9,R.drawable.face_qq10,R.drawable.face_qq11,R.drawable.face_qq12,R.drawable.face_qq13,R.drawable.face_qq14,R.drawable.face_qq15,R.drawable.face_qq16,
            R.drawable.face_qq17,R.drawable.face_qq18,R.drawable.face_qq19,R.drawable.face_qq20,R.drawable.face_qq21,R.drawable.face_qq22,R.drawable.face_qq23,R.drawable.face_qq24,
            R.drawable.face_qq25,R.drawable.face_qq26,R.drawable.face_qq27,R.drawable.face_qq28,R.drawable.face_qq29,R.drawable.face_qq30,R.drawable.face_qq31,R.drawable.face_qq32,
            R.drawable.face_qq33,R.drawable.face_qq34,R.drawable.face_qq35,R.drawable.face_qq36,R.drawable.face_qq37,R.drawable.face_qq38,R.drawable.face_qq39,R.drawable.face_qq40,
            R.drawable.face_qq41,R.drawable.face_qq42,R.drawable.face_qq43,R.drawable.face_qq44,R.drawable.face_qq45,R.drawable.face_qq46,R.drawable.face_qq47,R.drawable.face_qq48,
            R.drawable.face_qq49,R.drawable.face_qq50,R.drawable.face_qq51,R.drawable.face_qq52,R.drawable.face_qq53,R.drawable.face_qq54,R.drawable.face_qq55,R.drawable.face_qq56,
            R.drawable.face_qq57,R.drawable.face_qq58,R.drawable.face_qq59,R.drawable.face_qq60,R.drawable.face_qq61,R.drawable.face_qq62,R.drawable.face_qq63,R.drawable.face_qq64,
            R.drawable.face_qq65,R.drawable.face_qq66,R.drawable.face_qq67,R.drawable.face_qq68,R.drawable.face_qq69,R.drawable.face_qq70,R.drawable.face_qq71,R.drawable.face_qq72,
            R.drawable.face_qq73,R.drawable.face_qq74,R.drawable.face_qq75,R.drawable.face_qq76,R.drawable.face_qq77,R.drawable.face_qq78,R.drawable.face_qq79,R.drawable.face_qq80,
            R.drawable.face_qq81,R.drawable.face_qq82,R.drawable.face_qq83,R.drawable.face_qq84,R.drawable.face_qq85,R.drawable.face_qq86,R.drawable.face_qq87,R.drawable.face_qq88,
            R.drawable.face_qq89,R.drawable.face_qq90,R.drawable.face_qq91,R.drawable.face_qq92,R.drawable.face_qq93,R.drawable.face_qq94,R.drawable.face_qq95,R.drawable.face_qq96,
            R.drawable.face_qq97,R.drawable.face_qq98,R.drawable.face_qq99,R.drawable.face_qq100,R.drawable.face_qq101,R.drawable.face_qq102,R.drawable.face_qq103,R.drawable.face_qq104,
            R.drawable.face_qq105,R.drawable.face_qq106,R.drawable.face_qq107,R.drawable.face_qq108,R.drawable.face_qq109,R.drawable.face_qq110,R.drawable.face_qq111,R.drawable.face_qq112,
            R.drawable.face_qq113,R.drawable.face_qq114,R.drawable.face_qq115,R.drawable.face_qq116,R.drawable.face_qq117,R.drawable.face_qq118,R.drawable.face_qq119,R.drawable.face_qq120,
            R.drawable.face_qq121,R.drawable.face_qq122,R.drawable.face_qq123,R.drawable.face_qq124,R.drawable.face_qq125,R.drawable.face_qq126,R.drawable.face_qq127,R.drawable.face_qq128,
            R.drawable.face_qq129,R.drawable.face_qq130,R.drawable.face_qq131,R.drawable.face_qq132,R.drawable.face_qq133,R.drawable.face_qq134,R.drawable.face_qq135,R.drawable.face_qq136,
            R.drawable.face_qq137,R.drawable.face_qq138,R.drawable.face_qq139 };

    static {
        char index = EmoijCode;
        for(int i = 0;i<Size;i++){
            index++;
            DATA[i] = Emojicon.fromChar(index);
        }
    }
}
