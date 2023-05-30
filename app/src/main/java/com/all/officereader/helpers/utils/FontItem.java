package com.all.officereader.helpers.utils;

public class FontItem {
    String text;
    boolean isSelected;

    public FontItem() {
    }

    public FontItem(String text, boolean isSelected) {
        this.text = text;
        this.isSelected = isSelected;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
