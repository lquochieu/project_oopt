package project;

import java.util.ArrayList;
import java.util.LinkedList;

public class GraphLinkedList {
	protected LinkedList<Integer> adjLists[];
	protected boolean visited[];
	protected int vertices;
	protected ArrayList<Integer> stack;
	protected int count = 0;
	// Graph creation
	GraphLinkedList(int vertices) {
	this.vertices = vertices;
	stack = new ArrayList<Integer>();
    adjLists = new LinkedList[vertices + 1];
    visited = new boolean[vertices + 1];

    for (int i = 0; i < vertices + 1; i++)
    	adjLists[i] = new LinkedList<Integer>();
	}

	// Add edges
	void addEdge(int src, int dest) {
		adjLists[src].add(dest);
	}
}
