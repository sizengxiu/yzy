package com.yzy.model;

/**
 * @user szx
 * @date 2020/11/3 21:37
 */
public class ScanLog {
    private String filePath;
    private String  illegalWord;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getIllegalWord() {
        return illegalWord;
    }

    public void setIllegalWord(String illegalWord) {
        this.illegalWord = illegalWord;
    }
}
