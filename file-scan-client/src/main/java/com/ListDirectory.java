package com;

import java.io.File;

/**
 * @user szx
 * @date 2020/11/2 23:38
 */
public class ListDirectory {
    public static int count=0;
    public static void showDirectory(File file){
        File[] files = file.listFiles();
        if(files==null){
            return ;
        }
        for(File a:files){
            System.out.println(a.getAbsolutePath());

            if(a.isDirectory()){
                showDirectory(a);
            }else{
                count++;
            }
        }
    }

    public static void main(String[] args) {
        File file = new File("C:\\ProgramData\\Application Data");
        showDirectory(file);
        System.out.println(count);
    }
}
