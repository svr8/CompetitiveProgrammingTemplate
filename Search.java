class Search {

  // returns index of first element greater than or equal to key
  static int bsearch_ceil(int[] a, int key, int low, int high) {
    int mid;
    while(high-low>1) {
      mid = low+(high-low)/2;
      if(a[mid]>=key) high=mid;
      else low = mid+1;
    }

    if(a[low]>=key) return low;
    if(a[high]>=key) return high;
    return -1;
  }
  
  // returns index of first element greater than key
  static int bsearch_strictCeil(int[] a, int key, int low, int high) {
    int mid;
    while(high-low>1) {
      mid = low+(high-low)/2;
      if(a[mid]>key) high=mid;
      else low = mid+1;
    }

    if(a[low]>key) return low;
    if(a[high]>key) return high;
    return -1;
  }

  // returns index of last element smaller than or equal to key
  static int bsearch_floor(int[] a, int key, int low, int high) {
    int mid;
    while(high-low>1) {
      mid = low+(high-low)/2;
      if(a[mid]<=key) low=mid;
      else high=mid-1;      
    }

    if(a[low]<=key) return low;
    if(a[high]<=key) return high;
    return -1;
  }

  // returns index of last element smaller than key
  static int bsearch_strictFloor(int[] a, int key, int low, int high) {
    int mid;
    while(high-low>1) {
      mid = low+(high-low)/2;
      if(a[mid]<key) low=mid;
      else high=mid-1;      
    }

    if(a[low]<key) return low;
    if(a[high]<key) return high;
    return -1;
  }
}