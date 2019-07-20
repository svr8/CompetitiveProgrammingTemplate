class DisjointSet {
  class Set {
    int parent;
    int rank;
    Set(int p, int r) {
      parent=p;
      rank=r;
    }
  }

  Set[] subset;
  DisjointSet(int n) {
    subset = new Set[n];
    for(int i=0;i<n;i++)
      subset[i] = new Set(i, 0);
  }

  int find(int i) {
    if(subset[i].parent != i)
      subset[i].parent = find(subset[i].parent);
    return subset[i].parent;
  }

  void union(int x, int y) {
    int p1 = find(x);
    int p2 = find(y);

    if(subset[p1].rank < subset[p2].rank) 
      subset[p1].parent = p2;
    else if(subset[p2].rank < subset[p1].rank)
      subset[p2].parent = p1;
    else {
      subset[p1].parent = p2;
      subset[p2].rank++;
    }
  }
}