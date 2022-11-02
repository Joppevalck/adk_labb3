public class graphDTO {
    int v;
    int s;
    int t;
    int numEdges;
    int[][] edges;
    int maxFlow;

    graphDTO(int v, int s, int t, int numEdges, int[][] edges){
        this.v = v;
        this.s = s;
        this.t = t;
        this.numEdges = numEdges;
        this.edges = edges;
    }

    graphDTO(int v, int s, int t, int numEdges, int[][] edges, int maxFlow){
        this.v = v;
        this.s = s;
        this.t = t;
        this.numEdges = numEdges;
        this.edges = edges;
        this.maxFlow = maxFlow;
    }
}
