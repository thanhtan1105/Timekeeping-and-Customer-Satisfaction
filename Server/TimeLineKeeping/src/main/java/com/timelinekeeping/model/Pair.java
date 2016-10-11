package com.timelinekeeping.model;

import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public class Pair<K, V> implements Map.Entry<K,V>
{
    private K key;
    private V value;

    public Pair() {
    }

    public Pair(K key) {
        this.key = key;
    }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        final V result = value;
        this.value = value;
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

}
