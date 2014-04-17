/*  solution by ruben spaans
    checks necessary cases in order to find the answer
    time complexity O(n)
*/
#include <stdio.h>
#include <string.h>
char s[10000];
int find(char *s,char *t) {
  int i,j;
  for(i=0;s[i];i++) {
    for(j=0;s[i+j] && t[j];j++) if(t[j]!='?' && t[j]!=s[i+j]) break;
    if(!t[j]) return 1;
  }
  return 0;
}
int calc(char *s) {
  if(find(s,"lol")) return 0; /*  no change */
  if(find(s,"lo")) return 1;  /*  insert l at end */
  if(find(s,"ol")) return 1;  /*  insert l at beginning */
  if(find(s,"ll")) return 1;  /*  insert o in middle */
  if(find(s,"l?l")) return 1; /*  replace middle letter with o */
  if(find(s,"l")) return 2;   /*  insert ol at end */
  if(find(s,"o")) return 2;   /*  insert ll around o */
  return 3;                   /*  insert lol anywhere */
}
int main() {
  int cases;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%s",s);
    printf("%d\n",calc(s));
  }
  return 0;
}
