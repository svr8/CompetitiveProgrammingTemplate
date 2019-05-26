import java.util.*;

/*
  T : data type of weight
  n : number of nodes
*/
class AdjacencyMatrix<T> {
  int n;
  T[][] graph;
  boolean isDirected;
  
  AdjacencyMatrix(int n, boolean isDirected, T defaultWeight) {
    graph = (T[][]) (new Object[n][n]);
    for(int i=0;i<n;i++)
      for(int j=0;j<n;j++)
        graph[i][j] = defaultWeight;

    this.isDirected = isDirected;
  }

  void connect(int v1, int v2, T dist) {
    graph[v1][v2] = dist;
    if(!isDirected)
      graph[v2][v1] = dist;
  }

  T getWeight(int v1, int v2) {
    return graph[v1][v2];
  }
}

/*
  T: data type of vertex
  U: data type of weight
*/
class AdjacencyGraph<T, U> {
  HashMap<T, HashMap<T, U>> graph;

  AdjacencyGraph() {
    graph = new HashMap<>();
  }

  void connect(T v1, T v2, U weight) {
    HashMap<T, U> edgeList1 = graph.get(v1);
    
    if(edgeList1 == null) {
      HashMap<T, U> edgeList = new HashMap<>();
      edgeList.put(v2, weight);
      graph.put(v1, edgeList);
    } else {
      edgeList1.put(v2, weight);
    }
  }

  U getWeight(T v1, T v2) {
    HashMap<T, U> edgeList1 = graph.get(v1);
    
    if(edgeList1 == null)
      return null;
    return edgeList1.get(v2);     
  }
}