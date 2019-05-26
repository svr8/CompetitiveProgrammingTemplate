import java.util.*;
class STR {

  static ArrayList<Integer> patSearch_KMP(char[] str, char[] pat) {
    int strLen = str.length,
        patLen = pat.length;
    ArrayList<Integer> res = new ArrayList<>();
    if(strLen<patLen)
      return res;

    int[] lps = new int[patLen];
    int prefixLen = 0;
    for(int i=1;i<patLen; ) {
      if(pat[i]==pat[prefixLen]) {
        prefixLen++;
        lps[i] = prefixLen;
        i++;
      } else {
        if(prefixLen!=0) {
          prefixLen = lps[prefixLen-1];
        } else {
          lps[i] = prefixLen;
          i++;
        }
      }
    }

    for(int i=0,j=0; i<strLen; ) {
      if(pat[j]==str[i]) {
        i++;
        j++;
      }
      if(j==patLen) {
        res.add(i-j);
        j = lps[j-1];
      } else if(i<strLen && pat[j]!=str[i]) {
        if(j!=0)
          j = lps[j-1];
        else
          i++;
      }
    }

    return res;
  }

  static ArrayList<Integer> patSearch_RabinKarp(char[] str, char[] pat, int prime, int distinctChar) {
    int patLen = pat.length,
        strLen = str.length;
    
    int i, j, p=0, t=0, h=1;
    ArrayList<Integer> res = new ArrayList<>();

    for(i=0;i<patLen-1;i++)
      h = (h*distinctChar)%prime;
    
    for(i=0;i<patLen;i++) {
      p = (p*distinctChar + pat[i])%prime;
      t = (t*distinctChar + str[i])%prime;
    }

    for(i=0;i<=strLen-patLen;i++) {
      if(p==t) {
        for(j=0;j<patLen;j++)
          if(str[i+j]!=pat[j]) break;
        if(j==patLen)
          res.add(i);
      }

      if(i<strLen-patLen) {
        t = (distinctChar*(t-str[i]*h) + str[i+patLen])%prime;
        if(t<0) t+=prime;
      }
    }

    return res;
  }

  public static void main(String[] args) {
    char[] str = "aaabaabab".toCharArray(),
           pat = "aaba".toCharArray();

    ArrayList<Integer> list1 = patSearch_KMP(str, pat),
                       list2 = patSearch_RabinKarp(str, pat, 101, 26);
    System.out.println(list1);
    System.out.println(list2);
  }
}
/* PALINDROME QUERIES -------------------------------
   dependency: Meth
*/
class Palindrome {

  char[] str;
  int n;

  long[] power;
  long[] prefix;
  long[] suffix;
  long MOD;

  Palindrome(char[] str) {
    this.str = str;
    n = str.length;
    prefix = new long[n+1];
    suffix = new long[n+1];
    power = new long[n+1];
    MOD = (long)1e9 + 7;
  }

  // inclusive range: l,r
  boolean isPalindrome(int l, int r) {
    while(r>l)
      if(str[l++] != str[r--])
        return false;
    return true;
  }

  // use class Meth for modPow1m, mmi1

  void computePrefixHash() {
    prefix[0] = 0;
    prefix[1] = str[1];

    for(int i=2;i<=n;i++)
      prefix[i] = (prefix[i-1]%MOD + (str[i-1]%MOD * power[i-1]%MOD)%MOD)%MOD; 
  }

  void computeSuffixHash() { 
    suffix[0] = 0; 
    suffix[1] = str[n-1]; 

    for (int i=n-2, j=2; i>=0 && j<=n; i--,j++) 
      suffix[j] = (suffix[j-1]%MOD + (str[i]%MOD * power[j-1]%MOD)%MOD)%MOD; 
  } 

  boolean hashCheckPalindrome(int L, int R) {
    long hash_LR = ((prefix[R+1]-prefix[L]+MOD)%MOD * Meth.mmi1(power[L])%MOD)%MOD; 
    long reverse_hash_LR = ((suffix[n-L]-suffix[n-R-1]+MOD)%MOD * Meth.mmi1(power[n-R-1])%MOD)%MOD; 

    if(hash_LR == reverse_hash_LR) {
      return isPalindrome(L, R);
    }

    return false;
  }

}
