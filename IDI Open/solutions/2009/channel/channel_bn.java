// Author Børge Nordli
// Solution for Communication Channels

public class channel_bn {
  public static void main(String[] args) {
    java.util.Scanner in = new java.util.Scanner(System.in);
    for (int i = in.nextInt(); i --> 0;)
      System.out.println(in.next().equals(in.next()) ? "OK" : "ERROR");
  }
}
