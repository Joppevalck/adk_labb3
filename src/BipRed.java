import java.util.*;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 * <p>
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {

    Kattio io;
    int xVertices;
    int yVertices;
    int numEdges;
    int[] xEdges;
    int[] yEdges;


    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        this.xVertices = io.getInt();
        this.yVertices = io.getInt();
        this.numEdges = io.getInt();

        this.xEdges = new int[numEdges];
        this.yEdges = new int[numEdges];

        // Läs in kanterna

        int xNode;
        int yNode;
        for (int i = 0; i < this.numEdges; ++i) {
            xNode = io.getInt();
            yNode = io.getInt();
            this.xEdges[i] = xNode;
            this.yEdges[i] = yNode;
        }
    }

    void writeFlowGraph() {
        int v = xVertices + yVertices + 2;
        int e = numEdges + xVertices + yVertices;
        int s = v - 1, t = v;
        int c = 1;

        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(v);
        io.println(s + " " + t);
        io.println(e);

        // Skriv ut alla görn från X till Y noderna
        for(int i = 0; i < numEdges; i++){
            io.println(xEdges[i] + " " + yEdges[i] + " " + c);
        }


        // Skriv ut alla hörn från s till X noderna
        for(int i = 1; i <= xVertices + yVertices; i++){
            if(i <= xVertices){
                io.println(s + " " + i + " " + c);
            } else {
                io.println(i + " " + t + " " + c);
            }
        }

        // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
        io.flush();

        // Debugutskrift
//        System.err.println("Skickade iväg flödesgrafen");
    }

    void readMaxFlowSolution() {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int totflow = io.getInt();
        int e = io.getInt();

        // Skriv ut antal hörn och storleken på matchningen
        io.println(xVertices + " " + yVertices);
        io.println(totflow);


//        boolean[] nodes = new boolean[v];
//        Arrays.fill(nodes, false);
        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();

            if(a != s && b != t){
                io.println(a + " " + b);
            }



//            if(!nodes[a-1] && !nodes[b-1]){
//                    io.println(a + " " + b);
//                    nodes[a-1] = true;
//                    nodes[b-1] = true;
//                }

        }
    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();

        readMaxFlowSolution();

//        writeBipMatchSolution();

        // debugutskrift
//        System.err.println("Bipred avslutar\n");

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipRed();
    }
}

