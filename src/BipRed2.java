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

public class BipRed2 {

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

    graphDTO getFlow(Flowprob2 flow) {
        int v = xVertices + yVertices + 2;
        int e = numEdges + xVertices + yVertices;
        int s = v - 1, t = v;
        int c = 1;

        int totalEdges = numEdges + xVertices + yVertices;

        // Skriv ut alla görn från X till Y noderna
        int[][] edges = new int[totalEdges][3];
        for(int i = 0; i < numEdges; i++){
            edges[i] = new int[] {xEdges[i], yEdges[i], c};
        }

        // Skriv ut alla hörn från s till X noderna
        for(int i = numEdges; i < totalEdges; i++){
            if(i <= xVertices){
                edges[i] = new int[] {s, i, c};
            } else {
                edges[i] = new int[] {i, t, c};
            }
        }

        return flow.getFlow(new graphDTO(v, s, t, totalEdges, edges));
    }

    void printSolution(graphDTO dto) {


        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        int v = dto.v;
        int s = dto.s;
        int t = dto.t;
        int totflow = dto.maxFlow;
        int e = dto.numEdges;

        // Skriv ut antal hörn och storleken på matchningen
        io.println(xVertices + " " + yVertices);
        io.println(totflow);


//        boolean[] nodes = new boolean[v];
//        Arrays.fill(nodes, false);
        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = dto.edges[i][0];
            int b = dto.edges[i][1];
            int f = dto.edges[i][2];

            if(a != s && b != t){
                io.println(a + " " + b);
            }
        }
    }

    BipRed2() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        Flowprob2 flow = new Flowprob2();

        graphDTO dto = getFlow(flow);

        printSolution(dto);

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {
        new BipRed2();
    }
}

