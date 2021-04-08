package project;

import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing.util.SwingFileSinkImages;
import org.graphstream.ui.view.Viewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


class ok {
    public Graph graph = new SingleGraph("NewGraph");
    public Graph graph1 = new SingleGraph("NewGraph1");
    public int count=0;

    //hashmap co key la ten 1 dinh; value la cac dinh ke voi dinh key.
    public HashMap<String,String[]> adjEdge=new HashMap<>();

    //Dinh dich
    String DesNode;

    //luu duong di
    public List<String> path=new ArrayList<>();

    //danh sach cac dinh co it nhat 1 dinh ke
    public List<String> hasNext=new ArrayList<>();


    public void read() {
        graph.setAutoCreate(true);
        graph.setStrict(false);

        //gia tri cua dinh dich
        int max=0;

        BufferedReader reader;
        try {
            //doc file text theo dong
            reader = new BufferedReader(new FileReader(
                    "C:\\Users\\Administrator\\IdeaProjects\\graph.txt"));
            String line = reader.readLine();


            //voi moi dong:
            //add gia tri dau tien vao danh sach cac dinh co it nhat 1 dinh ke hasNext              (1)
            //put vao adjmap (hashmap) ten dinh va danh sach cac dinh ke voi no.                    (2)
            //tim dinh dich                                                                        (3)
            //them canh vao graph tu dong do                                                       (4)
            while (line != null) {
                String s[] = line.split("\\s");
                hasNext.add(s[0]);                                                                //(1)
                adjEdge.put(s[0],s);                                                              //(2)

                if(max< Integer.parseInt(s[0]))                                                   //(3)
                    max=Integer.parseInt(s[0]);
                for(int i=1;i<s.length;i++){
                    if(max< Integer.parseInt(s[i]))                                               //(3)
                        max=Integer.parseInt(s[i]);

                    graph.addEdge(s[0]+s[i], s[0], s[i],true);                         //(4)

                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //gan gia tri dinh dich tim duoc.
        DesNode=Integer.toString(max);

        //danh dau cac dinh co it nhat 1 dinh ke
        for(Node node: graph){
            node.setAttribute("ui.hasNext",0);
        }
        for(int j=0;j<hasNext.size();j++){
            Node t=graph.getNode(hasNext.get(j));
            t.setAttribute("ui.hasNext",1);
        }
    }


    public void SetupGraph(){
        //khoi tao thuoc tinh: check ---- su dung trong dfs
        //0:chua check ---- 1:da check.
        for(Node node: graph){
            node.setAttribute("ui.check",0);
        }
    }

    public void SetPath(){
        //khoi tao duong di
        path.add("1");
        Node t=graph.getNode("1");
        t.setAttribute("ui.check",1);
    }

    //hien thi graph
    public void displayGraph(){



        for (Node node : graph) {
            node.setAttribute("ui.label", "       "+node.getId()+"     ");
        }
        for (Node node : graph) {
            node.getAttribute("ui.label");
        }

        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");


        graph.display();

        FileSinkImages pic = new SwingFileSinkImages(FileSinkImages.OutputType.PNG, Resolutions.VGA);

        pic.setLayoutPolicy(FileSinkImages.LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
        try {
            pic.writeAll(graph, "C:\\Users\\Administrator\\IdeaProjects\\graph\\path" +Integer.toString(count)+".png");
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        /*try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        graph.setAttribute("ui.screenshot", "C:\\Users\\Administrator\\" +
                "IdeaProjects\\graph\\"+Integer.toString(count)+".png");*/
    }

    //dfs tu dinh p
    public void dfs(String p){
        int mutex=1;
        //neu dinh p co it nhat 1 canh ke:
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        Node t=graph.getNode(p);
        if(t.getAttribute("ui.hasNext").equals(1))
        {
            for (int i = 1; i < adjEdge.get(p).length; i++) {

                //duyet toi dinh chua check;
                String NextAdjVert = adjEdge.get(p)[i];
                Node nav = graph.getNode(NextAdjVert);
                if (nav.getAttribute("ui.check").equals(0)) {

                    //them vao path(duong di) va danh dau la da check: "ui.check"
                    path.add(NextAdjVert);
                    nav.setAttribute("ui.check", 1);

                    //Neu di toi dinh dich thi xu ly ket qua.
                    if (NextAdjVert.equals(DesNode)) {
                        count++;
                        /*graph.edges().forEach(e -> {
                            e.removeAttribute("ui.style");;
                        });*/

                        for (int j = 0; j < path.size() - 1; j++) {

                            Edge edge = graph.getEdge(path.get(j) + path.get(j + 1));
                            System.out.print(path.get(j) + path.get(j + 1)+" ");
                            edge.setAttribute("ui.style", "fill-color: rgb(255,0,0);");
                        }

                        displayGraph();

                        graph.edges().forEach(e -> {
                            e.removeAttribute("ui.style");
                        });

                        //quay lui
                        nav.setAttribute("ui.check", 0);
                    }

                    //neu chua toi dich thi tiep tuc goi de quy thuat toan dfs
                    else {
                        dfs(NextAdjVert);

                        //quay lui
                        nav.setAttribute("ui.check", 0);
                    }

                    //quay lui
                    path.remove(NextAdjVert);
                }
            }
        }
    }

    public static void main(String args[]) {

        System.setProperty("org.graphstream.ui", "swing");
        MyGraph firstGraph = new MyGraph();

        firstGraph.read();
        firstGraph.SetupGraph();
        firstGraph.SetPath();

        firstGraph.dfs("1");
       // firstGraph.printpath();
      //  firstGraph.displayGraph();

    }


}