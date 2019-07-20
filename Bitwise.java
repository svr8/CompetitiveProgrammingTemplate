class Bitwise {
  static int getSetBitCount(long n) {
		int count = 0;
		while(n!=0) {
			n &= n-1;
			count++;
		}
		return count;
	}

	// starting from LSB, first bit index is 0
	static long setBit(long n, int bitIndex) {
		n = n | (1<<bitIndex);
		return n;
	}

	// starting from LSB, first bit index is 0
	static long getBit(long n, int bitIndex) {
		while(bitIndex-->0) n = n>>1;
		return n&1;
	}
}