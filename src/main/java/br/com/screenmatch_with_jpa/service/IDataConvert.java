package br.com.screenmatch_with_jpa.service;

public interface IDataConvert {
    <T> T  getData(String json, Class<T> tClass);
}