package com.github.beetsbyninn.beets;

public enum InfoObject {

    FIRST_SLIDE(R.layout.first_welcome_slide),
    SECOND_SLIDE(R.layout.second_welcome_slide);

    private int mLayoutId;

    InfoObject(int layoutId) {
        mLayoutId = layoutId;
    }


    public int getLayoutId() {
        return mLayoutId;
    }

}