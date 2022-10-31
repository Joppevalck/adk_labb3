import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

class Flowprob {
    Kattio io;
    int numNodes;
    int s;
    int t;
    int numEdges;
    int[] path;
    ArrayList<LinkedList<Tuple>> graph;
    int maxFlow = 0;

    class Tuple{
        int to;
        int cap;
        boolean reverse;

        Tuple(int to, int cap, boolean reverse ){
            this.to = to;
            this.cap = cap;
            this.reverse = reverse;
        }
    }


    public void readProb(){
        this.numNodes= io.getInt();
        this.s = io.getInt() - 1;
        this.t = io.getInt() - 1;
        this.numEdges = io.getInt();

        this.graph = new ArrayList<LinkedList<Tuple>>(numNodes);
        for(int i = 0; i < numNodes; i++){
            graph.add(i, new LinkedList<Tuple>());
        }

        for(int i  = 0; i < numEdges; i++){
            int from = io.getInt() - 1;
            int to = io.getInt() - 1;
            int cap = io.getInt();

            // Sätt både cap och restflöde
            this.graph.get(from).add(new Tuple(to, cap, false));
            this.graph.get(to).add(new Tuple(from, 0, true));
        }
        this.path = new int[numNodes];
    }

    public boolean bfs(){
        boolean[] visited = new boolean[this.numNodes];

        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;

        path[s] = -1;   // markera början med -1

        while (!q.isEmpty()){
            int currentNode = q.poll();

            for (Tuple edge : this.graph.get(currentNode)) {
                if(!visited[edge.to] && edge.cap > 0) {
                    if (edge.to == this.t) {
                        path[edge.to] = currentNode;
                        return true;
                    }
                    q.add(edge.to);
                    path[edge.to] = currentNode;
                    visited[edge.to] = true;
                }
            }
        }

        return false;
    }

    void fordFulkerson(){

        while (bfs()){
            int pathFlow = Integer.MAX_VALUE;
            for(int node = t; node != s; node = path[node]){
                int parent = path[node];

                // Get cap from edge node to parent
                int cap = -1;
                for (Tuple edge : graph.get(parent)) {
                    if(edge.to == node){
                        cap = edge.cap;
                    }
                }

                pathFlow = Math.min(pathFlow, cap);
            }

            for(int node = t; node != s; node = path[node]){
                int parent = path[node];
                for (Tuple edge : graph.get(parent)) {
                    if(edge.to == node){
                        edge.cap -= pathFlow;
                    }
                }
                for (Tuple edge : graph.get(node)) {
                    if(edge.to == parent){
                        edge.cap += pathFlow;
                    }
                }
            }
            this.maxFlow += pathFlow;
        }

    }


    void printGraph(){
        io.println(numNodes);
        io.println((s + 1) + " " + (t + 1) + " " + maxFlow);

        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < graph.size(); i++){
            for (Tuple edge : graph.get(i)) {
                if(edge.reverse && edge.cap != 0){
                    sb.append((edge.to + 1) + " " + (i + 1) + " " + edge.cap + "\n");
                    count++;
                }
            }
        }
        io.println(count);
        io.println(sb.toString());

    }

    Flowprob() {
        io = new Kattio(System.in, System.out);

        readProb();

        fordFulkerson();

        printGraph();

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }



    public static void main(String[] args) {
        new Flowprob();
    }


}