package com.yzy;

/**
 * @user szx
 * @date 2020/11/8 18:13
 */
public class App {
    public static String sep="$$$$";
    public static void main(String[] args){
            String content="asdfasdfasdfasdff$$$$";
        String[] split = content.split(sep);
        for(String s:split){
            System.out.println(s);
        }
    }
}
