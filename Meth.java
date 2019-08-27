class Meth {

	static long P = (long)1e9 + 7;
	
  // Number Theory ---------------------------
    
  // nPr
  static long nPr(long n, long k) { 
    long Fn = 1, Fk = 1; 
      
    for (long i = 1; i <= n; i++) { 
      Fn *= i; 
      if (i == n - k) 
        Fk = Fn; 
    } 
    long coeff = Fn / Fk; 
    return coeff; 
  }
	
	// nCr
	static long nCr(long n, long r) { 
    long res = 1; 
    if (r>n-r)  r = n-r; 
    for (int i = 0; i < r; ++i) { 
        res *= (n - i); 
        res /= (i + 1); 
    } 

    return res; 
  }

  // nCr[]
  static long[] nCrList(int n, int k) { 
    long C[] = new long[k + 1]; 
    C[0] = 1;   
    for (int i = 1; i <= n; i++) { 
        for (int j = Meth.min(i, k); j > 0; j--) 
            C[j] = (C[j] + C[j-1])%P; 
    } 
    return C; 
  }
    
    // nCr % p
    static int nCrModP(int n, int r, int p) { 
      int[] C=new int[r+1]; 
      C[0] = 1; 
      for (int i = 1; i <= n; i++) { 
          for (int j = Math.min(i, r); j > 0; j--) 
              C[j] = (C[j] + C[j-1])%p; 
      } 
      return C[r]; 
    } 
  
  // nCr % p (USING LUCAS THEOREM)
  static int nCrModpLucas(int n, int r, int p) { 
    if (r==0) 
        return 1; 
    int ni = n%p; 
    int ri = r%p; 
    return (nCrModpLucas(n/p, r/p, p) * 
            nCrModP(ni, ri, p)) % p; 
  }

	// MATRIX ----------------------------------
	
	// nth term of fibonacci, n=0 indicates first term
    static long fibonacci(long a0, long a1, long n) {
        n--;
        long[][] m0 = new long[][] {
            {a0, a1, a0+a1}
        };
        if(n<3) return m0[0][(int)n];
        
        long[][] m = new long[][] {
            {0, 0, 0},
            {1, 0, 1},
            {0, 1, 1}
        };
        
        long[][] mk = matrix_pow(m, n-2);
        long[][] res = matrix_mul(m0, mk);
        
        return res[0][2];
    }
	
	// GP sum of n+1 terms:
  // a*r^0 + a*r^1 + ... a*r^n
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

	// -------------------------------------
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
	//------------------------------------------

	// when p is Prime
	static long mmi1(long n) {
		return modPow1(n, P-2);
	}

	
	// when n and p are co-primes: mmi2(n)
	static long mmi2(long a, long m) 
    { 
        long m0 = m; 
        long y = 0, x = 1; 
        if (m == 1) return 0; 

        while (a > 1) 
        { 
            long q = a / m; 
            long t = m; 
            m = a % m; 
            a = t; 
            t = y; 
            y = x - q * y; 
            x = t; 
        } 
        if (x < 0) x += m0; 
        
        return x; 
    } 

	static long modDivide(long a, long b) 
	{ 
		a = a % P;
		long inv;

		// if P is prime
		inv = mmi1(b);

		// else if b, P are co-prime
		// inv = mmi2(b, P);
		
		if(inv == -1) return -1; 
		else return modProduct(a, inv);
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
			if(x>0)
				sum = ( (sum%P) + (x%P) )%P;
			else
				sum = ( (sum%P) +(x%P) + P)%P;
		}
		
		return sum;
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