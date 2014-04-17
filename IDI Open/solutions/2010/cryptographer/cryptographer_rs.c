/* solution by ruben spaans
-  first, check if there are no duplicated letters => "unknown"
-  then, for each permutation length from 1 to 26 (or until we know the answer):
   check if the first n letters are a permutation of the first n letters, and
   that the same pattern repeats => "unknown"
-  otherwise, "new".*/
#include <stdio.h>
#include <string.h>
char s[1024];
int f[128];
int main() {
  int cases,i,len,r,l,x;
  scanf("%d",&cases);
  while(cases--) {
    scanf("%s",s);
    r=1;
    l=strlen(s);
    memset(f,0,sizeof(f));
    for(i=0;i<l;i++) f[(int)s[i]]++;
    for(i='A';i<='Z';i++) if(f[i]>1) goto check;
    goto done;
  check:
    for(len=1;len<27 && len<=l;len++) {
      memset(f,0,sizeof(f));
      for(i=0;i<len && i<l;i++) f[(int)s[i]]++;
      for(i=0;i<len;i++) if(f[i+'A']!=1) goto next;
      for(i=len;i<l;i+=len) {
        x=l-i;
        if(x>len) x=len;
        if(strncmp(s,s+i,x)) goto next;
      }
      goto done;
    next:;
    }
    r=0;
  done:
    puts(r?"unknown":"new");
  }
  return 0;
}
