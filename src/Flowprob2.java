import java.util.Arrays;
import java.util.LinkedList;

class Flowprob2 {
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

    graphDTO getNewGraph(){
        int tot = 0;
        for(int i = 0; i < countEdges.length; i++){
            tot += countEdges[i];
        }

        int count = 0;
        int[][] edges = new int[tot][3];
        for(int i = 0; i < graph.length; i++){
            for(int j = 0; j < countEdges[i]; j++) {
                if(this.graph[i][j].reverse && this.graph[i][j].cap != 0){
                    edges[count++] = new int[]{this.graph[i][j].to + 1, i + 1, this.graph[i][j].cap};
                }
            }
        }

        return new graphDTO(numNodes, s, t, tot, edges, maxFlow);
    }

    void getGraph(graphDTO dto){
        this.numNodes= dto.v;
        this.s = dto.s - 1;
        this.t = dto.t - 1;
        this.numEdges = dto.numEdges;

        this.graph = new Edge[numNodes][numNodes];
        this.countEdges = new int[numNodes];
        Arrays.fill(countEdges, 0);

        Edge normEdge;
        Edge revEdge;


        for(int i = 0; i < numEdges; i++){
            int from = dto.edges[i][0] - 1;
            int to = dto.edges[i][1] - 1;
            int cap = dto.edges[i][2];

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

    graphDTO getFlow(graphDTO dto){
        getGraph(dto);
        fordFulkerson();
        return getNewGraph();
    }

    Flowprob2() {

    }

    public static void main(String[] args) throws Exception {
        new Flowprob2();
    }


}