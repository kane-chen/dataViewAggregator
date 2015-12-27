package cn.kane.dynamicview.storage;

import java.util.List;

public interface Storager<K,V> {

    boolean save(V res) ;
    
    boolean update(V res) ;
    
    boolean remove(K key) ;
    
    V get(K key) ;
    
    List<V> query(Object... param) ;
}
