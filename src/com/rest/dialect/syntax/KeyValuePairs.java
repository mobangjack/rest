/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rest.dialect.syntax;

import java.util.Map;

/**
 * KeyValuePairs that helps construct SQL sentences.
 * @author 帮杰
 *
 */
public class KeyValuePairs {

	private String keyQuote;
	private String valQuote;
	private String equalSymbol;
	private String separator;
	private StringBuilder sb;
	
	public KeyValuePairs() {
		keyQuote = "";
		valQuote = "";
		equalSymbol = "=";
		separator = ",";
		sb = new StringBuilder();
	}
	
	public KeyValuePairs(String keyQuote,String valQuote,String separator) {
		this.keyQuote = keyQuote;
		this.valQuote = valQuote;
		this.equalSymbol = "=";
		this.separator = separator;
		sb = new StringBuilder();
	}
	
	public KeyValuePairs(String keyQuote,String valQuote,String equalSymbol,String separator) {
		this.keyQuote = keyQuote;
		this.valQuote = valQuote;
		this.equalSymbol = equalSymbol;
		this.separator = separator;
		sb = new StringBuilder();
	}
	
	public KeyValuePairs(Map<String, Object> map) {
		this();
		add(map);
	}
	
	public KeyValuePairs add(String key,Object val) {
		if(val==null)
			return this;
		if(sb.length()>0)
			sb.append(separator).append(keyQuote).append(key).append(keyQuote).append(equalSymbol).append(valQuote).append(val).append(valQuote);
		else
			sb.append(keyQuote).append(key).append(keyQuote).append(equalSymbol).append(valQuote).append(val).append(valQuote);
		return this;
	}

	public KeyValuePairs add(Map<String, Object> map) {
		for(Map.Entry<String, Object> e:map.entrySet()){
			if(e.getValue()!=null)
				add(e.getKey(), e.getValue());
		}
		return this;
	}
	
	public String getKeyQuote() {
		return keyQuote;
	}

	public KeyValuePairs setKeyQuote(String keyQuote) {
		this.keyQuote = keyQuote;
		return this;
	}

	public String getValQuote() {
		return valQuote;
	}

	public KeyValuePairs setValQuote(String valQuote) {
		this.valQuote = valQuote;
		return this;
	}

	public String getEqualSymbol() {
		return equalSymbol;
	}

	public KeyValuePairs setEqualSymbol(String equalSymbol) {
		this.equalSymbol = equalSymbol;
		return this;
	}

	public String getSeparator() {
		return separator;
	}

	public KeyValuePairs setSeparator(String separator) {
		this.separator = separator;
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
	
	public boolean isEmpty() {
		return sb.length()<1;
	}
}
