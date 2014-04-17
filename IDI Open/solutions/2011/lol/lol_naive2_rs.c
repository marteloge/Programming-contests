/*  solution by ruben spaans.
    more efficient implementation of naïve algorithm which
    doesn't copy strings around.
    time complexity O(N^3)
*/
#include <stdio.h>
char s[1000000],t[4000000];
int len,best;
void btr(int depth) {
  int i;
  char c;
  for(i=0;i<len;i++) if(t[i]=='l') {
    i++;
    while(i<len && !t[i]) i++;
    if(t[i]!='o') { i--; continue; }
    i++;
    while(i<len && !t[i]) i++;
    if(t[i]!='l') continue;
    if(best>depth) best=depth;
    return;
  }
  if(depth>=best-1) return;
  /*  insert */
  for(i=0;i<len;i++) if(!t[i]) {
    t[i]='l';
    btr(depth+1);
    t[i]='o';
    btr(depth+1);
    t[i]=0;
  }
  /*  delete */
  for(i=0;i<len;i++) if(t[i]) {
    c=t[i];
    t[i]=0;
    btr(depth+1);
    t[i]=c;
  }
  /*  replace */
  for(i=0;i<len;i++) if(t[i]) {
    c=t[i];
    t[i]='l';
    btr(depth+1);
    t[i]='o';
    btr(depth+1);
    t[i]=c;
  }
}
int main() {
  int cases,i,j;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%s",s);
    for(j=i=0;s[i];i++) {
      t[j++]=0; t[j++]=0; t[j++]=s[i];
    }
    t[j++]=0; t[j++]=0; t[j]=0;
    len=j;
    best=3;
    btr(0);
    printf("%d\n",best);
  }
  return 0;
}
