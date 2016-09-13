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

import java.util.Set;

/**
 * Values that helps construct SQL sentences.
 * @author 帮杰
 *
 */
public class Values {

	private String quote;
	private String seperator;
	private StringBuilder sb;
	
	public Values() {
		quote = "";
		seperator = ",";
		sb = new StringBuilder();
	}
	
	public Values(String quote,String seperator) {
		this.quote = quote;
		this.seperator = seperator;
		sb = new StringBuilder();
	}

	public <T> Values(Set<T> set) {
		this();
		add(set);
	}
	
	public String getQuote() {
		return quote;
	}

	public Values setQuote(String quote) {
		this.quote = quote;
		return this;
	}

	public String getSeperator() {
		return seperator;
	}

	public Values setSeperator(String seperator) {
		this.seperator = seperator;
		return this;
	}

	public Values add(Object val) {
		if(val==null)
			return this;
		if(sb.length()>0)
			sb.append(seperator).append(quote).append(val).append(quote);
		else
			sb.append(quote).append(val).append(quote);
		return this;
	}
	
	public <T> Values add(Set<T> set) {
		for(Object e:set){
			if(e!=null)
				add(e);
		}
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
