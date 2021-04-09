package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class OnMyWay extends GraphLinkedList{

	private int place = 1;
	private int prePlace = 0;
	private String command;
	private Scanner scanner;
	private Scanner sc;
	private Iterator<Integer> ite;
	private Edge edge;
	private String a;
	private String b;
	OnMyWay(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	
	void runner() throws NoSuchElementException, IOException {
		graph = new SingleGraph("Use");
    	graphDraw();
	}
	
	void clear() {
		for(int i = 1; i <= vertices; ++i) {
			visited[i] = false;
		}
		stack.clear();
		graph = new SingleGraph("Use");
    	graphDraw();
    	graph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
	}
	void addOption(int i, int pl) throws IOException {
		if(stack.size() > 0) {
			prePlace = place;
		}
		
		if(i ==1 ) {
			place = pl;
			stepForward();
			
		}
		else {
			if(stack.size() == 0) {
				return;
			}
			place =stack.get(stack.size() -1);
			stepBack();
			graph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
		}
	}
	
	private void stepForward() {
		
		if (stack.size() == 0) {
			stack.add(place);
			System.out.println(place);
			visited[place] = true;
			v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			graph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
		}
		else {
			ite = adjLists[prePlace].listIterator();
			int countemp = 0;
		    while (ite.hasNext()) {
		        int adj = ite.next();
		        if (!visited[adj])
		        	countemp = 1;
		    }
		    if (countemp == 0)
		    	JOptionPane.showMessageDialog(null, "Sorry, there is not no way to choose!", null, JOptionPane.INFORMATION_MESSAGE);
		    else {
					if ((visited[place]) || !isAdjacent(stack.get(stack.size()-1), place)) {
						return;
					}
				stack.add(place);
				visited[place] = true;
				v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
				v[place].setAttribute("ui.label", Integer.toString(place));
				String a = Integer.toString(stack.get(stack.size() - 2));
	    		String b = Integer.toString(stack.get(stack.size() - 1));
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
	    		graph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
		    }
		}
	}
	
	private void stepBack() {
		
		if (stack.size() > 1) {
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			a = Integer.toString(stack.get(stack.size() - 2));
			b = Integer.toString(stack.get(stack.size() - 1));
			edge=graph.getEdge(a + " " + b);
			edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
			visited[place] = false;
			stack.remove(stack.size() - 1);
			System.out.println(place);
			place = stack.get(stack.size() - 1);
		}
		else {
			visited[place] = false;
			stack.clear();
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
		}
	}
	private boolean isAdjacent(int a, int b) {
		for (int i: adjLists[a]) {
			if (i == b) {
				return true;
			}
		}
		return false;
	}
	
}













