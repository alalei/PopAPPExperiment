package com.example.popappexperiment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Solution {
	HashMap<State, Node> map = new HashMap<State, Node>();
	
	enum State {
		Created, Running, Sleeping, Waiting, Terminated
	}
	
	class Node {
		State chartState;
		List<Node> neighbors;
		boolean visited = false;
		
		Node(State state) {
			this.chartState = state;
		}
	}
	
	public void initiate () {
		// TODO: add state nodes to map
		
	}
	
	public boolean isReachable(State src, State dst) {
		if (!map.containsKey(src) || !map.containsKey(dst)) {
			return false;
		}
		
		Node srcNode = map.get(src);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.offer(srcNode);
		srcNode.visited = true;
		while (!queue.isEmpty()) {
			Node current = queue.poll();
			if (current.chartState == dst) {
				return true;
			}
			
			List<Node> list = current.neighbors;
			for (Node newNode : list) {
				if (!newNode.visited){
					queue.offer(newNode);
					newNode.visited = true;
				}
			}
		}
		
		return false;
	}
	
}
