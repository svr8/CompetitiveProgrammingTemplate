import java.io.*;
import java.util.*;

class IO {
	private BufferedReader br;
	private StringTokenizer st;

	IO()throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());
	}

	String next()throws IOException {
		if(st.hasMoreTokens())
			return st.nextToken();

		st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}

	int nextInt()throws IOException { return Integer.parseInt(next()); }
	double nextDouble()throws IOException { return Double.parseDouble(next()); }
	long nextLong()throws IOException { return Long.parseLong(next()); }

	int[] nextIntArray(int n)throws IOException { 
		int[] a = new int[n];
		for(int i=0;i<n;i++) a[i] = nextInt();
		return a;
	}

	long[] nextLongArray(int n)throws IOException {
		long[] a = new long[n];
		for(int i=0;i<n;i++) a[i] = nextLong();
		return a;
	}

	double[] nextDoubleArray(int n)throws IOException {
		double[] a = new double[n];
		for(int i=0;i<n;i++) a[i] = nextDouble();
		return a;
	}
}
