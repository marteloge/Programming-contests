// Solution by Ruben Spaans

#include <math.h>
#include <stdio.h>
#include <string.h>

#define MAX 128

double pr[26];
char p[MAX],q[MAX];

double madskillz(char *a,char *b) {
	
  double r=0,z=1;
  int i,al=strlen(a),bl=strlen(b),l=al<bl?al:bl;
  
  for(i=0;i<l;i++) {
    z/=pr[a[i]-'a'];
    if(!strncmp(a+al-i-1,b,i+1)) r+=z;
  }
  
  return r;
  
}

void magicVoodooTrick() {
    for(int i=0;i<26;i++) scanf("%lf",&pr[i]);
    scanf("%s %s",p,q);
    
    /* is p a substring of q minus the last char? */
    if(strlen(p)<strlen(q)) for(int i=0;i<strlen(q);i++) if(!strncmp(p,q+i,strlen(p))) { printf("%.14f\n",1.); return; }
    /* the other way around? */
    if(strlen(p)>strlen(q)) for(int i=0;i<strlen(p);i++) if(!strncmp(q,p+i,strlen(q))) { printf("%.14f\n",0.); return; }
    
    double A = madskillz(q,q) - madskillz(q,p);
    double B = madskillz(p,p) - madskillz(p,q);
    
    printf("%.14f\n",((double)A)/(A+B));
}

int main() {
  
  int cases;
  scanf("%d",&cases);
  while(cases--)magicVoodooTrick();
  return 0;
}
