package com.all.officereader.helpers.callbacks;

import java.io.File;

public interface ItemFileClickListener {
    void onItemClick(File file);
    void onAddToBookmark(File file);
    void onCreateShortCut(File file);
    void onShareFile(File file);
    void onRemoveBookmark(File file);
}
