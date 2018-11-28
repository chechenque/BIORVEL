package com.example.zamor.catalagodesuperheroes.MarvelAPI;

public class Constants {
    public static final String VERSION = "/v1/";
    public static final String REST_ENDPOINT = "public/characters";
    public static final String ROOT_URL = " https://gateway.marvel.com"+ VERSION /*+REST_ENDPOINT*/;

    public static final String APIKEY = "27804abf37aca4d8c0abfd71b6fa04ca";
    public static final String TS = "1";
    public static final String HASH = "f36197d9eae61cc70bab5c1e7642d6d6";

}

//https://gateway.marvel.com:443/v1/public/characters?name=Iron%20Man&apikey=27804abf37aca4d8c0abfd71b6fa04ca
//https://gateway.marvel.com:443/v1/public/characters?name=Iron%20Man&apikey=27804abf37aca4d8c0abfd71b6fa04ca&ts=1&hash=f36197d9eae61cc70bab5c1e7642d6d6º
//ROOT_URL + VERSION + REST_ENDPOINT + "?name=" + <Nombre del personaje>+ "&apikey=" APIKEY
//ROOT_URL + VERSION + REST_ENDPOINT + "?name=" + <Nombre del personaje> %20 <Nombre>+ "&apikey=" APIKEY
