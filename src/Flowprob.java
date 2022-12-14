import java.util.Arrays;
import java.util.LinkedList;

class Flowprob {
    Kattio io;
    int numNodes;
    int s;
    int t;
    int numEdges;
    Edge[] path;
    Edge[][] graph;
    int[] countEdges;
    int maxFlow = 0;

    class Edge{
        int from;
        int to;
        int cap;
        boolean reverse;
        Edge reversedEdge;

        Edge(int from, int to, int cap, boolean reverse ){
            this.from = from;
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
        countEdges = new int[numNodes];
        Arrays.fill(countEdges, 0);

        Edge normEdge;
        Edge revEdge;

        for(int i  = 0; i < numEdges; i++){
            int from = io.getInt() - 1;
            int to = io.getInt() - 1;
            int cap = io.getInt();

            normEdge = new Edge(from, to, cap, false);
            revEdge = new Edge(to, from, 0, true);

            // Sätt både cap och restflöde
            this.graph[from][countEdges[from]++] = normEdge;
            this.graph[to][countEdges[to]++] = revEdge;

            normEdge.reversedEdge = revEdge;
            revEdge.reversedEdge = normEdge;

        }
        this.path = new Edge[numNodes];
    }

    public boolean bfs(){
        boolean[] visited = new boolean[this.numNodes];

        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;

        path[s] = null;   // markera början med -1

        while (!q.isEmpty()){
            int currentNode = q.poll();
            for(int i = 0; i < this.countEdges[currentNode]; i++){
                if(!visited[this.graph[currentNode][i].to] && this.graph[currentNode][i].cap > 0) {
                    if (this.graph[currentNode][i].to == this.t) {
                        path[this.graph[currentNode][i].to] = this.graph[currentNode][i];
                        return true;
                    }
                    q.add(this.graph[currentNode][i].to);
                    path[this.graph[currentNode][i].to] = this.graph[currentNode][i];
                    visited[this.graph[currentNode][i].to] = true;
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
            for(int node = t; node != s; node = path[node].from){
                Edge parent = path[node];
                int cap = parent.cap;

                pathFlow = Math.min(pathFlow, cap);
            }

            for(int node = t; node != s; node = path[node].from){
                Edge parent = path[node];

                parent.cap -= pathFlow;
                parent.reversedEdge.cap += pathFlow;
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
            for(int j = 0; j < countEdges[i]; j++) {
                if(this.graph[i][j].reverse && this.graph[i][j].cap != 0){
                    sb.append((this.graph[i][j].to + 1) + " " + (i + 1) + " " + this.graph[i][j].cap + "\n");
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