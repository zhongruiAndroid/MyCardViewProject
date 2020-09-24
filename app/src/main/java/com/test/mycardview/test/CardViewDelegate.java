//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.test.mycardview.test;

import android.graphics.drawable.Drawable;
import android.view.View;

interface CardViewDelegate {
    void setCardBackground(Drawable var1);

    Drawable getCardBackground();

    boolean getUseCompatPadding();

    boolean getPreventCornerOverlap();

    void setShadowPadding(int var1, int var2, int var3, int var4);

    void setMinWidthHeightInternal(int var1, int var2);

    View getCardView();
}
