package project;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.graphstream.ui.view.camera.Camera;




public class project {
	private static OnMyWay omg;
	private static DFS g;
	
	private static Graph graph;
	private static String[] v;
	private static int[][] allIntArr;
	private static String[] arrOfStr;
	private static int max = 0; // file index of the last vertex
	private static int i1, i2, x = 0, y = 0, a = 0;
	private static Integer[] c;
	private static int size;
	private static JFrame frame = new JFrame();
	private static JPanel buttonJPanel;
	
	public static void main(String args[]) throws IOException {
		
        prepare();
        
        console();
        
        
        //graph
	}
	
	public static void console() {
		
        JButton showButton = new JButton("Bai 1");
        JButton AllPAthButton = new JButton("Bai 2");
        JButton QuestionsPathButton = new JButton("Bai 3");
        
        buttonJPanel = new JPanel();
        buttonJPanel.add(showButton);
        buttonJPanel.add(AllPAthButton);
        buttonJPanel.add(QuestionsPathButton);
        buttonJPanel.setBackground(Color.orange);
        
        setLabel(frame);
        
        frame.setPreferredSize(new Dimension(500, 500));
        frame.getContentPane().add(buttonJPanel, BorderLayout.SOUTH);
        frame.setTitle("Project OOPT");
        frame.setForeground(Color.YELLOW);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getView(frame);
        showButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					g.graphString("Bai1");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
        
		
        AllPAthButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				g.runDFS(1, max, "Bai2");
				AllPathButton();
				
			}
		});
        
        QuestionsPathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        frame.pack();
        frame.setVisible(true);
	}
	
	public static void setLabel(JFrame frame) {
		JLabel showGraphLabel = new JLabel("Welcome to our graph");
        showGraphLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        showGraphLabel.setForeground(Color.RED);
        
        showGraphLabel.setHorizontalAlignment(SwingConstants.CENTER);
      
        frame.add(showGraphLabel, BorderLayout.NORTH);
	}
	public static void AllPathButton() {
		// TODO Auto-generated method stub
		JFrame AllPathFrame = new JFrame();
		JPanel vPanel = new JPanel();
		JButton[] vButtons = new JButton[max];
		for(int i = 0; i < max; ++i) {
			vButtons[i] = new JButton(Integer.toString(i+1));
			vPanel.add(vButtons[i]);
		}
		
		vPanel.setForeground(Color.GREEN);
		
		AllPathFrame.getContentPane().add(vPanel, BorderLayout.SOUTH);
		AllPathFrame.setPreferredSize(new Dimension(500, 500));
		AllPathFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		AllPathFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				AllPathFrame.dispose();
			}
		});
		setLabel(AllPathFrame);
		AllPathFrame.pack();
		AllPathFrame.setVisible(true);
		getView(AllPathFrame);
		c = new Integer[max + 1];
		
		for(int i = 0; i < max; ++i) {
			c[i + 1] = 0;
			vButtons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i = 1; i <= max; ++i) {
						if(e.getActionCommand().equals(Integer.toString(i))) {
							
							if(a == 0) {
									c[i] = 1;
									i1 = i;
									y = 0;
									a++;
									a = a%2;
								
							}
							else {
								if(c[i] == 0) {
									c[i] = 1;
									i2 = i;
									x = 0;
									a++;
									a = a%2;
								}
							}
						}
						
					}
					if(i2 != 0) {
					for(int i = 1; i <= max; ++i) {
						for(int j = 1; j <= max; ++j) {
							if(c[i1] == 1 && c[i2] == 1) {
								g.runDFS(i1 , i2, "path between vertex " + i1 + " and vertex " +i2 );
								c[i1] = 0;
								c[i2] = 0;
							}
						}
					}
					}
				}
			});
		}
	}
	
	
	public static void prepare() throws IOException {
		System.setProperty("org.graphstream.ui", "swing");
		graph = new SingleGraph("Project");
		graph.setStrict(false);
		graph.setAutoCreate( true );
        String data = "1 2 3\n"
        		+ "2 5 4\n"
        		+ "4 3 5 6";

        try {
            // Creates a Writer using FileWriter
            Writer output = new FileWriter("output.txt");


            // Writes string to the file
            output.write(data);

            // Closes the writer
            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }
        
     // Creates an array of character
        char[] array = new char[100];
        // Read file into an arraylist
    	ArrayList<String> listOfLines = new ArrayList<>(); 
        try {
        	BufferedReader bufReader = new BufferedReader(new FileReader("output.txt")); 
        	String line = bufReader.readLine(); 
        	while (line != null) {
        		listOfLines.add(line);
        		line = bufReader.readLine();
        	} 
        	bufReader.close();

        }

        catch(Exception e) {
            e.getStackTrace();
        }
        size = listOfLines.size();
        
        
        allIntArr = new int[size][];
        
        //Read to list of integers for each line
        for (int i = 0; i < size; i++) {
        	arrOfStr = listOfLines.get(i).split(" ");
        	int arrOfStrlength = arrOfStr.length;
        	allIntArr[i] = new int[arrOfStrlength];
        	
        	for (int j = 0; j < arrOfStrlength; j++) {
        		allIntArr[i][j] = Integer.parseInt(arrOfStr[j]);
        		
        	}
        	for (int j = 1; j < arrOfStrlength; j++) {
        			graph.addEdge(arrOfStr[0] + arrOfStr[j], arrOfStr[0], arrOfStr[j], true);
        	        		
        	}
        	
        	//find max
        	if (max < allIntArr[i][arrOfStrlength - 1])
        		max = allIntArr[i][arrOfStrlength - 1];
        	
        }
        
        
        
        
        //Max is the numbers of node of graph
        /////////////////////////////////////
        /////////////////////////////////////
        //g: save data of graph
        g = new DFS(max);
        for (int i = 0; i < size; i++) {

        	for (int j = 0; j < allIntArr[i].length; j++) {
        		g.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
        Node[] e = new Node[max];
        for(int i = 0; i < max; ++i) {

        	graph.addNode(Integer.toString(i+1));
        	e[i] = graph.getNode(Integer.toString(i+1));
        	e[i].setAttribute("ui.style", "shape:circle;fill-color: yellow;size: 30px;");
    		e[i].setAttribute("ui.label", Integer.toString(i+1)); 
        }
        omg = new OnMyWay(max);
        for (int i = 0; i < size; i++) {
        	for (int j = 0; j < allIntArr[i].length; j++) {
        		omg.addEdge(allIntArr[i][0], allIntArr[i][j]);
        	}
        }
		try {
			omg.runner();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static void getView(JFrame frame) {
    	SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        JPanel view = (JPanel) viewer.addDefaultView(false);
        frame.getContentPane().add(view);
    }
	
	

}
