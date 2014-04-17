import java.io.*;
import java.text.*;

public class scorch_tab{
  public static void main(String[] args) throws Exception{
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(5);
    nf.setMinimumFractionDigits(5);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    while(t-->0){
      String[] inp = in.readLine().split(" ");
      double[] vals = new double[6];
      for(int i=0;i<6;i++) vals[i]=Double.parseDouble(inp[i]);
      vals[5]=vals[5]*Math.PI/180.0;
      double ti = ((2*(vals[2]-vals[0]+(vals[1]-vals[3])/Math.tan(vals[5]))))/
        ((9.8/Math.tan(vals[5]))+vals[4]);
      if(ti<0) System.out.println("impossible");
      else{
        ti = Math.sqrt(ti);
        double ans = (vals[3]-vals[1]+4.9*ti*ti)/(ti*Math.sin(vals[5]));
        if(ans>300.0 || ans<0) System.out.println("impossible");
        else System.out.println(nf.format(ans));
      }
    }
  }
}
