package com.example.kimyo_uz.dto;

public interface SimpleCrud<K,V> {
    ResponseDto<V>create(V request);
    ResponseDto<V>get(K id);
    ResponseDto<V>update(V request,K id);
    ResponseDto<V>delete(K id);
}
