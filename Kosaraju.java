import java.util.*;
class Node {
    int index;
    Node(int i) {index=i;}
}
class Graph<T> {
  int V, E;
  protected T[] nodeList;
  protected LinkedList<T>[] g;
  private boolean isDirected;

  Graph(T[] nodeList, int vertexCount, int edgeCount, boolean isDirected) {
    V = vertexCount;
    E = edgeCount;

    g = new LinkedList[V];
    for(int i=0;i<V;i++) g[i] = new LinkedList<T>();

    this.nodeList = nodeList;
    this.isDirected = isDirected;
  }

  void connect(int indexSource, int indexDestination) {
    g[indexSource].add(nodeList[indexDestination]);
    if(!isDirected)
      g[indexDestination].add(nodeList[indexSource]);
  }

  LinkedList<T> getChildren(int index) {
    return g[index];
  }

  Iterator childrenIterator(int index) {
      return g[index].iterator();
  }

  void dfs(int root, boolean[] v) {
      v[root] = true;
      Iterator it = g[root].iterator();
      Node n;
      while(it.hasNext()) {
          n = (Node)it.next();
          if(!v[n.index])
            dfs(n.index, v);
      }
  }

  public String toString() {
      String res = "";
    
      for(int i=0;i<V;i++) {
          res = res + i+": ";
          Iterator it = childrenIterator(i);
          while(it.hasNext())
            res = res + ((Node)(it.next())).index+" ";
          res = res + "\n";
      }
      
      return res;
  }
}

class Kosaraju<T> extends Graph {

    private Graph reverseGraph;

    Kosaraju(T[] nodeList, int vertexCount, int edgeCount) {
        super(nodeList, vertexCount, edgeCount, true);
        reverseGraph = new Graph<T>(nodeList, vertexCount, edgeCount, true);
    }

    void connect(int indexSource, int indexDistination) {
        g[indexSource].add(nodeList[indexDistination]);
        reverseGraph.connect(indexDistination, indexSource);
    }

    int sccCount() {
        Stack stack = new Stack<T>();
        boolean[] v = new boolean[V];
        for(int i=0;i<V;i++)
            if(!v[i])
                dfs2(i, v, stack);
        
        v = new boolean[V];
        int res = 0;
        while(!stack.empty()) {
            Node cur = (Node) stack.pop();
            if(!v[cur.index]) {
                res++;
                reverseGraph.dfs(cur.index, v);
            }
        }

        return res;
    }


    private void dfs2(int root, boolean[] v, Stack stack) {
        v[root] = true;
        Iterator it = childrenIterator(root);
        while(it.hasNext()) {
            Node n = (Node)it.next();
            if(!v[n.index]) 
                dfs2(n.index, v, stack);
        }
        stack.push(nodeList[root]);        
    }    

    public void display() {
        System.out.println("GRAPH:\n" + this);
        System.out.println("REVERSE GRAPH:\n"+reverseGraph);
    }

    public static void main(String[] args) {
        Node[] node = new Node[5];
        for(int i=0;i<5;i++) node[i] = new Node(i);

        Kosaraju<Node> k = new Kosaraju(node, 5, 5);
        k.connect(0,2);
        k.connect(0,3);
        k.connect(1,0);
        k.connect(2,1);
        k.connect(3,4);

        // k.display();
        System.out.println(k.sccCount());

    }



}