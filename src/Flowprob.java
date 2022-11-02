import java.util.Arrays;
import java.util.LinkedList;

class Flowprob {
    Kattio io;
    int numNodes;
    int s;
    int t;
    int numEdges;
    int[] path;
    Edge[][] graph;
    int maxFlow = 0;

    class Edge{
        int to;
        int cap;
        boolean reverse;
        Edge reversedEdge;

        Edge(int to, int cap, boolean reverse ){
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

        this.graph = new Edge[numNodes][numNodes];
        int[] countEdges = new int[numNodes];
        Arrays.fill(countEdges, 0);

        Edge normEdge;
        Edge revEdge;

        for(int i  = 0; i < numEdges; i++){
            int from = io.getInt() - 1;
            int to = io.getInt() - 1;
            int cap = io.getInt();

            normEdge = new Edge(to, cap, false);
            revEdge = new Edge(from, 0, true);

            // Sätt både cap och restflöde
            this.graph[from][countEdges[from]++] = normEdge;
            this.graph[to][countEdges[to]++] = revEdge;

            normEdge.reversedEdge = revEdge;
            revEdge.reversedEdge = normEdge;

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

            for (Edge edge : this.graph[currentNode]) {
                if(edge == null){
                    break;
                }
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


        // For each path
        while (bfs()){
            // Get path flow
            int pathFlow = Integer.MAX_VALUE;
            for(int node = t; node != s; node = path[node]){
                int parent = path[node];

                // Get cap from edge node to parent
                int cap = -1;
                for (Edge edge : graph[parent]) {
                    if(edge == null){
                        break;
                    }
                    if(edge.to == node){
                        cap = edge.cap;
                    }
                }

                pathFlow = Math.min(pathFlow, cap);
            }

            for(int node = t; node != s; node = path[node]){
                int parent = path[node];
                for (Edge edge : graph[parent]) {
                    if(edge == null){
                        break;
                    }
                    if(edge.to == node){
                        edge.cap -= pathFlow;
                        edge.reversedEdge.cap += pathFlow;
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
        for(int i = 0; i < graph.length; i++){
            for (Edge edge : graph[i]) {
                if(edge == null){
                    break;
                }
                if(edge.reverse && edge.cap != 0){
                    sb.append((edge.to + 1) + " " + (i + 1) + " " + edge.cap + "\n");
                    count++;
                }
            }
        }
        io.println(count);
        io.println(sb.toString());
    }

    Flowprob() throws Exception {
        io = new Kattio(System.in, System.out);

        readProb();

        fordFulkerson();

//        throw new Exception("done print ");

        printGraph();

//        throw new Exception("done");

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();


    }

    public static void main(String[] args) throws Exception {
        new Flowprob();
    }


}