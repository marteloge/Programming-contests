import java.io.*;
import java.text.*;
import java.util.*;

public class computer_tab{
  static double[] lambda;
  static double[][] p;
  static int[][] number;
  static int[] prices;
  static double[] s;
  static int[][] num;
  static int n;
  static int c;
  static double[] facs;
  
  public static double fac(int i){
    if(facs[i]==0) facs[i]=((double)i)*fac(i-1);
    return facs[i];
  }
  
  public static double pcum(int i, int k){
    if(p[i][k]<0) p[i][k]=p[i][k-1]+pr(i,k);
    return p[i][k];
  }
  
  public static double pr(int i, int k){
    return Math.exp(-lambda[i])*(Math.pow(lambda[i],(double)k)/fac(k));
  }

  public static double solve(){
    s = new double[c+1];
    s[0]=1.0;
    for(int i=0;i<n;i++){
      p[i][0]=pr(i,0);
      s[0]*=p[i][0];
    }
    num = new int[c+1][n];
    for(int i=1;i<=c;i++){
      double b = s[i-1];
      int nu = -1;
      for(int j=0;j<n;j++){
        int l = i-prices[j];
        if(l>=0){
          double t = s[l]*pcum(j,num[l][j]+1)/pcum(j,num[l][j]);
          if(t>b){ b=t; nu=j;}
        }
      }
      s[i]=b;
      if(nu!=-1) 
        for(int j=0;j<n;j++) num[i][j]=num[i-prices[nu]][j] + (j==nu?1:0);
      else num[i]=num[i-1];
    }
    return s[c];
  }
  
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    facs = new double[1003];
    facs[0]=1.0;
    DecimalFormat df = new DecimalFormat("0.00000",
        new DecimalFormatSymbols(Locale.ENGLISH));
    for(int k=0;k<t;k++,System.out.println(df.format(solve()))){
      String[] inp = in.readLine().split(" ");
      n = Integer.parseInt(inp[0]);
      c = Integer.parseInt(inp[1]);
      lambda = new double[n];
      prices = new int[n];
      p = new double[n][];
      inp = in.readLine().split(" ");
      for(int i=0;i<n;i++) lambda[i] = Double.parseDouble(inp[i]);
      inp = in.readLine().split(" ");
      for(int i=0;i<n;i++) prices[i] = Integer.parseInt(inp[i]);
      for(int i=0;i<n;i++){
        int lim = c/prices[i]+2;
        p[i] = new double[lim];
        for(int j=1;j<lim;j++) p[i][j]=-1.0;
      }
    }
  }
}
