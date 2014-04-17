import java.io.*;

public class virus_tab{
  static int l;
  static int n;  
  public static long[][] mult(long[][] a, long[][] b){
    long[][] c = new long[n][n];
    for(int i=0;i<n;i++) for(int j=0;j<n;j++){
      for(int k=0;k<n;k++){
        c[i][j]+=(a[i][k]*b[k][j]);
        if(c[i][j]>l){c[i][j]=l; break;}
      }
    }
    return c;
  }

  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    while(t-->0){
      String[] inp = in.readLine().split(" ");
      n = Integer.parseInt(inp[0])+1;
      l = Integer.parseInt(inp[1]);
      long[][][] as = new long[35][n][n];
      for(int i=0;i<n-1;i++){
	inp=in.readLine().split(" ");
	for(int j=0;j<n;j++) as[0][i][j]=Long.parseLong(inp[j]);
      }
      as[0][n-1][n-1]=1;
      for(int i=1;i<35;i++) as[i]=mult(as[i-1],as[i-1]);
      long[][] curra = new long[n][n];
      long le=0;
      long max=(long)l*(long)n+(long)1;
      long ri = max;
      while(le<ri){
        long m=(le+ri)/2;
        long mt=m;
        for(int i=0;i<n;i++) for(int j=0;j<n;j++) curra[i][j]=i==j?1:0;
        for(int i=0;mt>0;i++,mt/=2)if((mt&1)==1) curra=mult(curra,as[i]);
        if(curra[0][n-1]<l) le=m+1;
        else ri=m;
      }
      if(le==max) System.out.println("lucky");
      else System.out.println(le);
    }
  }
}
