package project;

import java.util.ArrayList;
import java.util.Iterator;
public class DFS extends GraphLinkedList{



	

	DFS (int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	// DFS algorithm
    void runDFS1(int vertex, int end) {
	    visited[vertex] = true;
	    stack.add(vertex);
	    if (vertex == end) {
	    	count++;
	    	for (int i:stack)
		    	System.out.print(i + " ");
	    	System.out.println();
	    	visited[vertex] = false;
		    stack.remove(stack.size() - 1);
	    	return;
	    }
	    Iterator<Integer> ite = adjLists[vertex].listIterator();
	    while (ite.hasNext()) {
	        int adj = ite.next();
	        if (!visited[adj])
	        	runDFS1(adj, end);
	    }
	    visited[vertex] = false;
	    stack.remove(stack.size() - 1);
    }  
    void runDFS(int vertex, int end) {
    	runDFS1(vertex, end);
    	if (count == 0){
    		System.out.println("No path!");
    	}
    	count = 0;
    }
}
