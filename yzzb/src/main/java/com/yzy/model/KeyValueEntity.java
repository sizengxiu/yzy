package com.yzy.model;

/**
 * 返回前端的数据经常要key:value结构，用LIst<Map>有点浪费
 * @user szx
 * @date 2021/6/27 21:33
 */
public class KeyValueEntity<T,E>{
    private T key;
    private E value;

    public KeyValueEntity() {
    }

    public KeyValueEntity(T key, E value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValueEntity{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
