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