// range sum, update
class SegmentTree {
	int segStart, segEnd;
	long val;
	long lazyDelta;
	boolean propogateStatus;
	SegmentTree left, right;

	SegmentTree(long[] a, int start, int end) {
		segStart = start;
		segEnd = end;		
		if(start==end) {
			val = a[start];
		} else {
			int mid = Meth.getMid(start, end);
			left = new SegmentTree(a, start, mid);
			right = new SegmentTree(a, mid+1, end);
			val = left.val + right.val;
		}
	}

	long rangeSum(int start, int end) {
		if(propogateStatus)
			propogate();
		if(totalOverlap(start, end)) {
			return val;
		} else if(noOverlap(start, end)) {
		    return 0;
		}
        else {
			long x = left.rangeSum(start, end);
			long y = right.rangeSum(start, end);
			return x+y;
		}
	}

	void rangeUpdate(int start, int end, long delta) {
		if(propogateStatus)
			propogate();
		if(totalOverlap(start, end)) {
			if(left!=null) left.lazyUpdate(delta);
			if(right!=null) right.lazyUpdate(delta);
			val += delta*(segEnd-segStart+1);
		} else if(noOverlap(start, end)) {
		    
		}
		else {
			left.rangeUpdate(start, end, delta);
			right.rangeUpdate(start, end, delta);
			val = left.val + right.val;
		}
	}

	void lazyUpdate(long delta) {
		lazyDelta += delta;
		propogateStatus = true;
	}

	void propogate() {
		if(left!=null) 
			left.lazyUpdate(lazyDelta);
		if(right!=null)
			right.lazyUpdate(lazyDelta);
		val += lazyDelta*(segEnd-segStart+1);
		lazyDelta = 0;
		propogateStatus = false;
	}


	boolean totalOverlap(int queryStart, int queryEnd) {
		return queryStart<=segStart && segEnd<=queryEnd;
	}
	
	boolean noOverlap(int queryStart, int queryEnd) {
	    return queryEnd<segStart || queryStart>segEnd;
	}
}