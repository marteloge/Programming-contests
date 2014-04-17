import java.io.*;
import java.util.*;

public class willy_tab{
  static int n;
  
  public static String rperm(String s, String p){
    char[] r = new char[n];
    for(int i=0;i<n;i++) r[p.charAt(i)-'a'] = s.charAt(i);
    return new String(r);
  }
  
  public static String perm(String s, String p){
    char[] r = new char[n];
    for(int i=0;i<n;i++) r[i] = s.charAt(p.charAt(i)-'a');
    return new String(r);
  }
  
  public static void main(String[] args) throws Exception{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(in.readLine());
    outer: while(t-->0){
      String[] inp = in.readLine().split(" ");
      n = Integer.parseInt(inp[0]);
      int p = Integer.parseInt(inp[1]);
      int l = Integer.parseInt(inp[2]);
      inp = in.readLine().split(" ");
      String[] ps = new String[p];
      for(int i=0;i<p;i++) ps[i] = in.readLine();
      if(inp[0].equals(inp[1])){ System.out.println(0); continue;}
      HashMap<String,Integer> set1 = new HashMap<String,Integer>();
      HashMap<String,Integer> set2 = new HashMap<String,Integer>();
      ArrayList<LinkedList<String>> qs1 = new ArrayList<LinkedList<String>>();
      ArrayList<LinkedList<String>> qs2 = new ArrayList<LinkedList<String>>();
      set1.put(inp[0],0);
      set2.put(inp[1],0);
      for(int i=0;i<2;i++){ 
        qs1.add(new LinkedList<String>()); 
        qs2.add(new LinkedList<String>());
      }
      qs1.get(1).addLast(inp[0]);
      qs2.get(1).addLast(inp[1]);
      for(int i=1;i<=(l+1)/2;i++){
        while(qs1.get(i%2).size()>0){
          String curr = qs1.get(i%2).poll();
          for(int j=0;j<p;j++){
            String cand = perm(curr,ps[j]);
            if(!set1.containsKey(cand)){
              set1.put(cand,i);
              qs1.get((i+1)%2).addLast(cand);
              if(set2.containsKey(cand)){
                System.out.println((i*2-1));
                continue outer;
              }
            }
          }
        }
        if(i*2>l) break;
        while(qs2.get(i%2).size()>0){
          String curr = qs2.get(i%2).poll();
          for(int j=0;j<p;j++){
            String cand = rperm(curr,ps[j]);
            if(!set2.containsKey(cand)){
              set2.put(cand,i);
              qs2.get((i+1)%2).addLast(cand);
              if(set1.containsKey(cand)){
                System.out.println((i*2));
                continue outer;
              }
            }
          }
        }
      }
      System.out.println("whalemeat");
    }
  }
}
