package com.yzy.util;

import java.util.Collection;

/**
 * 集合相关的工具
 * @user szx
 * @date 2021/6/6 15:26
 */
public class CollectionUtil {

    /**
     * 判断集合是否为空
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/6/6 15:33
     */
    public static boolean isEmpty(Collection collection){
        if(collection==null){
            return true;
        }
        return collection.isEmpty();
    }
}
