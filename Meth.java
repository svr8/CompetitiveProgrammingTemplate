class Meth {

	static long P = (long)1e9 + 7;

	// MATRIX ----------------------------------
	
	static long gp_sum(long a, long r, long n) {
	    long[][] x = { {a, a}};
	    long[][] m = {
	       {a, 0},
	       {r, r}
	    };
	    
	    long[][] m_pow = matrix_pow(m, n);
	    long[][] res = matrix_mul(x, m_pow);

	    return res[0][0];
	}
	
	static long[][] matrix_pow(long[][] a, long pow) {
    
        long[][] res = new long[a[0].length][a[0].length];
        for(int i=0;i<a.length;i++) res[i][i] = 1;
        
	    while( pow>0 ) {
	        if( (pow&1)==1 )
	            res = matrix_mul(res, a);
	        a = matrix_mul(a, a);
	        pow /= 2;
	    }
	    
	    return res;
	}
	
	static long[][] matrix_mul(long[][] a, long[][] b) {
	    if(a[0].length != b.length)
	        return null;
	        
	    long[][] res = new long[a.length][b[0].length];
	    
	    for(int i=0;i<a.length;i++) 
	        for(int j=0;j<b.length;j++) 
	            for(int k=0;k<b.length;k++) 
	                res[i][j] = modAdd( res[i][j], modProduct( a[i][k], b[k][j] ) );
	    return res;
	}
	
	// PRIME ----------------------------------
	
	static long nextPrime(long n) {
	    if(n==2) n++;
	    else n+=2;
	    while(!isPrime(n)) n+=2;
	    return n;
	}
	
    static long spf(long n) {
	    if( (n&1L)==0 ) return 2;
	    long limit = (long)Math.sqrt(n);
	    for(long i=3;i<=limit;i+=2) if(n%i==0) return i;
	    return n;
	}
	
	static boolean isPrime(long n) {
	    if(n==2) return true;
	    if(n%2==0 || n==1) return false;
	    long limit = (long)Math.sqrt(n);
	    for(long i=3;i<=limit;i+=2) if(n%i==0) return false;
	    return true;
	}
	
	static long gcd(long a, long b) 
    { 
        if (a == 0) return b; 
        return gcd(b%a, a); 
    } 
    
    // OPERATIONS ----------------------------------

	static long modPow1(long x, long y) 
	{ 
			long res = 1;      
			x = x % P;  
			while (y > 0) 
			{ 
					if((y & 1)==1) 
							res = (res * x) % P; 
					y = y >> 1;  
					x = (x * x) % P;  
			} 
			return res; 
	} 

	static long modPow2(long x, long y) 
	{ 
			long res = 1;      
			x = x % P;  
			while (y > 0) 
			{ 
					if((y & 1)==1) 
							res = multiplication(res, x);
					y = y >> 1;  
					x = multiplication(x, x);
			} 
			return res; 
	} 

	static long modProduct(long... a) {
		long sum = 1;
		for(long x : a) 
			sum = ( (sum%P) * (x%P) )%P;
		
		return sum;
	}


	static long modAdd(long... a) {
		long sum = 0;
		for(long x : a) {
			sum = ( (sum%P) + (x%P) )%P;
		}
		
		return sum;
	}

	static long multiplication(long a, long b) {
			long result = 0;
			a = a % P;
			while (b > 0) {
					if((b & 1) == 1) {
							result = (result + a) % P;
					}
					a = (a << 1) % P;
					b >>= 1;
			}
			return result;
	}

	static int min(int a, int b) { return a<b ? a : b; }
	static long min(long a, long b) { return a<b ? a : b; }
	static double min(double a, double b) { return a<b ? a : b; }

	static int max(int a, int b) { return a>b ? a : b; }
	static long max(long a, long b) { return a>b ? a : b; }
	static double max(double a, double b) { return a>b ? a : b; }

	static int abs(int a) { return a<0 ? -a : a; }
	static double abs(double a) { return a<0 ? -a : a; }
	
	static int getMid(int a, int b) { return a + (b-a)/2; }
	static double getMid(double a, double b) { return a + (b-a)/2.0; }
}