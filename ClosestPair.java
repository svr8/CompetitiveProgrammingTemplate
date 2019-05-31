import java.util.*;
    
class Point {
    double x, y;
    Point(double xx, double yy) { x=xx; y=yy; }
    
    boolean isEqual(Point o) {
        return x==o.x && y==o.y;
    }
    
    double distance(Point o) {
        return Math.sqrt(
                (x-o.x)*(x-o.x) + (y-o.y)*(y-o.y)
            );
    }
    
    boolean isLess(Point o) {
        if(x!=o.x)
            return x<=o.x;
        return y<=o.y;
    }
    
    public String toString() {
        return "("+x+","+y+")";
    }
}

public class ClosestPair {
    Point best1, best2;
    double bestDistance = Double.MAX_VALUE;
    
    ClosestPair(Point[] points) {
        int n = points.length;
        
        Point[] xSorted = new Point[n];
        for(int i=0;i<n;i++) 
            xSorted[i] = points[i];
        Arrays.sort(xSorted, new Comparator<Point>(){
            @Override
            public int compare(Point a, Point b) {
                if(a.x!=b.x)
                    return (int)(a.x-b.x);
                return (int)(a.y-b.y);
            }
        });
        
        for(int i=0;i<n-1;i++) {
            if(xSorted[i].isEqual(xSorted[i+1])) {
                bestDistance = 0;
                best1 = xSorted[i];
                best2 = xSorted[i+1];
                return;
            }
        }
        
        Point[] ySorted = new Point[n];
        for(int i=0;i<n;i++)
            ySorted[i] = xSorted[i];
        
        Point[] aux = new Point[n];
        
        closest(xSorted, ySorted, aux, 0, n-1);
    }
    
    double closest(Point[] xSorted, Point[] ySorted, Point[] aux, int low, int high) {
        if(high<=low) return Double.MAX_VALUE;
        
        int mid = low + (high-low)/2;
        Point median = xSorted[mid];
        
        double d1 = closest(xSorted, ySorted, aux, low, mid),
               d2 = closest(xSorted, ySorted, aux, mid+1, high),
               minD = Math.min(d1, d2);
        
        merge(ySorted, aux, low, mid, high);
        
        int m = 0;
        for(int i=low;i<=high;i++) {
            if(Math.abs(ySorted[i].x - median.x) < minD)
                aux[m++] = ySorted[i];
        }
        
        for(int i=0;i<m;i++) {
            for(int j=i+1;j<m && aux[j].y-aux[i].y<minD;j++) {
                double d = aux[i].distance(aux[j]);
                if(d<minD) {
                    minD = d;
                    if(d<bestDistance) {
                        bestDistance = d;
                        best1 = aux[i];
                        best2 = aux[j];
                    }
                }
            }
        }
        
        return minD;
    }
    
    static void merge(Point[] a, Point[] aux, int low, int mid, int high) {
        for(int k=low;k<=high;k++)
            aux[k] = a[k];
        
        for(int i=low, j=mid+1, k=low;k<=high;k++) {
            if(i>mid) a[k]=aux[j++];
            else if(j>high) a[k]=aux[i++];  
            else if(aux[j].isLess(aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }
    
    public static void main(String[] args) {
        Point[] points = new Point[] {new Point(2, 3), new Point(12, 30), new Point(40, 50), new Point(5, 1), new Point(12, 10), new Point(3, 4)};
        ClosestPair closest = new ClosestPair(points);
        System.out.println(closest.best1 + " " + closest.best2 + " --> " + closest.bestDistance);
    }
}