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
package com.rest;

/**
 * Node makes a uri structured.
 * @author 帮杰
 */
public class Node {

	private String table;
	private String id;
	private Node parentNode;
	
	public String getTable() {
		return table;
	}

	public String getId() {
		return id;
	}

	public Node getParentNode() {
		return parentNode;
	}

	@Override
	public String toString() {
		return table+"/"+id;
	}
	
	public static Node parse(String uri) {
		String[] paths = uri.replaceAll("\\s", "").split("/");
		Node node = new Node();
		int endPoint = paths.length-2;
		if (paths.length%2 == 0) {
			node.id = paths[paths.length-1];
			node.table = paths[paths.length-2];
			endPoint = paths.length-3;
		}else {
			node.table = paths[paths.length-1];
			endPoint = paths.length-2;
		}
		buildNodeChain(node, endPoint, paths);
		return node;
	}

	private static void buildNodeChain(Node node, int endPoint, String[] paths) {
		Node childNode = node;
		Node parentNode = null;
		for (int i = endPoint; i > -1; i = i - 2) {
			parentNode = new Node();
			parentNode.id = paths[i];
			parentNode.table = paths[i - 1];
			childNode.parentNode = parentNode;
			childNode = parentNode;
		}
	}
	
}
