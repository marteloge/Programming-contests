/*  solution by ruben spaans.
    dynamic programming (memoization) with the state space
    [position in original word][position in soundex code][last soundex digit]
    O(L) = L*4*7*25
*/
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#define MAXS 1001
#define MOD 1000000007
int len;
char code[5];
int digit[]={-1,1,2,3,-1,1,2,0,-1,2,2,4,5,5,-1,1,2,6,2,3,-1,1,0,2,-1,2};
int dp[MAXS][4][8];

int calc(int at,int codepos,int lastdigit) {
  int i,newpos,r;
  if(dp[at][codepos-1][lastdigit+1]>-1) return dp[at][codepos-1][lastdigit+1];
  if(at==len) {
    while(codepos<4 && code[codepos]=='0') codepos++;
    return codepos>3;
  }
  /*  assume next letter is h or w */
  r=2*calc(at+1,codepos,lastdigit)%MOD;
  for(i=0;i<26;i++) if(digit[i]) {
    newpos=codepos;
    if(codepos==4) goto doit;
    else {
      if(lastdigit==digit[i]) goto doit;
      if(lastdigit!=digit[i] && digit[i]==code[codepos]-'0') {
        newpos++;
        goto doit;
      } else if(lastdigit!=digit[i] && digit[i]==-1) goto doit;
      continue;
    }
  doit:
    r=(r+calc(at+1,newpos,digit[i]))%MOD;
  }
  return dp[at][codepos-1][lastdigit+1]=r;
}

int main() {
  int cases,i;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%s %d",code,&len);
    for(i=1;i<=len;i++) memset(dp[i],-1,sizeof(dp[i]));
    printf("%d\n",calc(1,1,-1));
  }
  return 0;
}
