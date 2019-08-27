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
class AdjacencyMap<T, U> {
  HashMap<T, HashMap<T, U>> graph;

  AdjacencyMap() {
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

class TreeNode {
  // INSERT Node details

  Vector<TreeNode> children;
  int bfsRank;

  TreeNode() {
    children = new Vector<>();
  }

  void addChild(TreeNode child) {
    children.add(child);
  }

  void bfs(TreeNode root) {

    PriorityQueue<TreeNode> q = new PriorityQueue<>(0, new Comparator<TreeNode>(){
      @Override
      public int compare(TreeNode a, TreeNode b) {
        return a.bfsRank-b.bfsRank;
      }
    });

    TreeNode cur, nextEl;
    Iterator childIterator;
    q.add(root);

    while(q.size()>0) {
      cur = (TreeNode) q.poll();

      //PROCESS NODE
      
      childIterator = cur.children.iterator();
      while(childIterator.hasNext()) {
        nextEl = (TreeNode)childIterator.next();
        nextEl.bfsRank = cur.bfsRank+1;
        q.add(nextEl);
      }      
    }
  }

  void dfs(TreeNode root) {
    Iterator childIterator = children.iterator();
    while(childIterator.hasNext()) {
      dfs((TreeNode)childIterator.next());
      
    }

  }
}

// SHORTEST PATH ALGORITHMS -----------------------------

static long[][] floydWarshall(long[][] graph) {
  int V = graph.length;
  long[][] d = new long[V][V];

  for(int i=0;i<V;i++)
    for(int j=0;j<V;j++)
      d[i][j] = graph[i][j];
  
  for(int k=0;k<V;k++)
    for(int i=0;i<V;i++)
      for(int j=0;j<V;j++)
        if(d[i][k]+d[k][j]<d[i][j])
          d[i][j] = d[i][k]+d[k][j];
  
  return d;
}