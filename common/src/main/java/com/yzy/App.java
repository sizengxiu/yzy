package com.yzy;

/**
 * Hello world!
 *
 */
public class App 
{
    public static String sep="$$$$";
    public static void main(String[] args){
        String content="asdfasdfasdfasdff@@@@";
        String[] split = content.split("@@@@");
        for(String s:split){
            System.out.println(s);
        }
    }
}
