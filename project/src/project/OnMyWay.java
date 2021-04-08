package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;

public class OnMyWay extends GraphLinkedList{

	private int place = 1;
	private Scanner scanner;
	OnMyWay(int vertices) {
		super(vertices);
		// TODO Auto-generated constructor stub
	}
	
	void runner() throws IOException {
    	
    	String command = new String();
    	System.out.println("Order Management Application: ");
		System.out.println("--------------------------------");
		System.out.println("1. Step Forward");
		System.out.println("2. Step Back");
		System.out.println("0. Stop");
		System.out.println("--------------------------------");
		Scanner sc;
    	while (true) {
    		graphDraw();
        	graph.display().getGraphicGraph();
    		sc = new Scanner(System.in);
    		System.out.println("--------------------------------");
    		System.out.println("Please choose a number: 0-1-2");

        	command = sc.next();
    		
    		if (command.equals("0")) {
    			System.out.println("Bye my friend, see you later!");
    			sc.close();
    			break;
    		}
    		
    		else if (command.equals("1")) {
    			stepForward();
    		}
    		
    		else if (command.equals("2")) {
    			stepBack();
    		}
    		else 
    			System.out.println("Illegal command!");
    		
    	}
	}
	
	private void stepForward() {
		scanner = new Scanner(System.in);
		if (stack.size() == 0) {
			System.out.print("Choose your place: ");
			do {
				place = scanner.nextInt();
				if ((place < 1) || (place > vertices)) {
					System.out.print("Warning! Choose a different place: ");
				}
			} while ((place < 1) || (place > vertices));
			stack.add(place);
			visited[place] = true;
			v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
		}
		else {
			Iterator<Integer> ite = adjLists[place].listIterator();
			int countemp = 0;
		    while (ite.hasNext()) {
		        int adj = ite.next();
		        if (!visited[adj])
		        	countemp = 1;
		    }
		    if (countemp == 0)
		    	System.out.println("Sorry, there is not no way to choose!");
		    else {
		    	System.out.print("Choose your destination: ");
				do {
					place = scanner.nextInt();
					if ((visited[place]) || !isAdjacent(stack.get(stack.size()-1), place)) {
						System.out.print("Warning! Choose a different destination: ");
					}
				} while ((visited[place]) || !isAdjacent(stack.get(stack.size()-1), place));
				stack.add(place);
				visited[place] = true;
				v[place].setAttribute("ui.style", "shape:circle;fill-color: green;size: 30px;");
				v[place].setAttribute("ui.label", Integer.toString(place));
				String a = Integer.toString(stack.get(stack.size() - 2));
	    		String b = Integer.toString(stack.get(stack.size() - 1));
	    		Edge edge=graph.getEdge(a + " " + b);
	    		edge.setAttribute("ui.style", "fill-color: purple; size: 3px;");
		    }
		}
	}
	
	private void stepBack() {
		if (stack.size() > 1) {
			v[place].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
			v[place].setAttribute("ui.label", Integer.toString(place));
			String a = Integer.toString(stack.get(stack.size() - 2));
			String b = Integer.toString(stack.get(stack.size() - 1));
			Edge edge=graph.getEdge(a + " " + b);
			edge.setAttribute("ui.style", "fill-color: black; size: 0.8px;");
			visited[place] = false;
			stack.remove(stack.size() - 1);
			place = stack.get(stack.size() - 1);
		}
		else {
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













