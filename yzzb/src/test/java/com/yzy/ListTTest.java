package com.yzy;

import java.util.LinkedList;
import java.util.List;

/**
 * @user szx
 * @date 2021/9/27 20:07
 */
public class ListTTest {

    public static void test(List<A> list){

    }
    public static void main(String[] args){
        List<B> list=new LinkedList<>();
        list.add(new B());
    }
}
class A{
    public int a=0;
}
class B extends A{

}
