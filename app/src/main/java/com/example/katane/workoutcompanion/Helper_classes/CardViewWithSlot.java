package com.example.katane.workoutcompanion.Helper_classes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

public class CardViewWithSlot extends CardView {
    private Slot slot;

    public CardViewWithSlot(Context context, CardView cardView){
        super(context);
    }

    public CardViewWithSlot(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CardViewWithSlot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }
    public CardViewWithSlot(Context context) {
        super(context);
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }
}
