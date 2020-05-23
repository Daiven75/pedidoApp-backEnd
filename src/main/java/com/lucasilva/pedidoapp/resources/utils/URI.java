package com.lucasilva.pedidoapp.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URI {

	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static List<Long> decodeLongList(String nome) {
		List<Long> listLong = new ArrayList<>();
		List<String> listString = new ArrayList<>();
		
		for(int i = 0; i < nome.length(); i++) {
			listString.add(nome.substring(i, i + 1));
			++i;
		}
		for(String x : listString) {
			long i = Long.parseLong(x);
			listLong.add(i);
		}
		return listLong;
	}
}
