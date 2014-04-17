/* set up a transition matrix P between states, calculate P^\inf (take P
   to a huge power), then read off the desired values */
#include <math.h>
#include <stdio.h>
#include <string.h>
#define LEN 32
#define MAX 64
#define EPS 1e-10
double pr[26];
char p[LEN],q[LEN];
int pl,ql;                   /* cached string lengths of p,q */
int statestoid[LEN][LEN];
int idtop[MAX],idtoq[MAX];
int nextstatep[LEN][LEN][26],nextstateq[LEN][LEN][26];
int nid;
/* find the lowest index in a such that a+i is a prefix of b. */
int bestprefix(char *a,char *b) {
  int l=strlen(a),i;
  for(i=0;a[i];i++) if(!strncmp(a+i,b,l-i)) return i;
  return i;
}
/* pp,qq: have matched the first pp letters from p, and the first qq letters
   from q. */
void recurse(int pp,int qq) {
  int next,i,newp,newq;
  char ourp[LEN],ourq[LEN];
  if(statestoid[pp][qq]>-1) return;
  idtop[nid]=pp;
  idtoq[nid]=qq;
  statestoid[pp][qq]=nid++;
  if(pp==pl || qq==ql) return;
  for(next=0;next<26;next++) if(pr[next]>EPS) {
    /* state pp,qq means that the last stream of letters the monkey has
       typed are p_0 p_1 p_2 ... p_pp-1 and q_0 q_1 q_2 ... q_qq-1.
       find out which state we end up when the next letter typed is "next".
       in other words:
       we have strings sp = p_0 p_1 ... p_pp-1 next
                       sq = q_0 q_1 ... q_qq-1 next
       find least index i in the strings above such that sp.substring(i,pp) is
       a prefix of p. likewise for q.
       example: p="abcd" q=defg" pp=2 qq=0 next="d"
       ourp="abd" ourq="d"
       least indices ip,iq such that ourp+ip and ourq+iq are prefixes of p,q:
       3 and 0 ("" is a prefix of "abcd", "d" is a prefix of "defg",
       hence nextstate((2,0),"d") is (0,1).
    */
    for(i=0;i<pp;i++) ourp[i]=p[i]; ourp[i]=next+'a'; ourp[i+1]=0;
    for(i=0;i<qq;i++) ourq[i]=q[i]; ourq[i]=next+'a'; ourq[i+1]=0;
    newp=pp+1-bestprefix(ourp,p);
    newq=qq+1-bestprefix(ourq,q);
    nextstatep[pp][qq][next]=newp;
    nextstateq[pp][qq][next]=newq;
    recurse(newp,newq);
  }
}
double m[MAX][MAX];
void findstates() {
  memset(statestoid,-1,sizeof(statestoid));
  nid=0;
  recurse(0,0);
}

void creatematrix() {
  int i,j,id,atp,atq,nextid,nextp,nextq;
  memset(m,0,sizeof(m));
  /* absorbing states */
  for(i=0;i<=pl;i++) for(j=0;j<=ql;j++) if((id=statestoid[i][j])>-1) {
    if(i==pl || j==ql) id=statestoid[i][j],m[id][id]=1;
  }
  for(i=0;i<nid;i++) {
    atp=idtop[i]; atq=idtoq[i];
    if(atp==pl || atq==ql) continue;
    for(j=0;j<26;j++) if(pr[j]>EPS) {
      nextp=nextstatep[atp][atq][j];
      nextq=nextstateq[atp][atq][j];
      nextid=statestoid[nextp][nextq];
      m[i][nextid]+=pr[j];
    }
  }
}
void printmatrix() {
  double a;
  int i,j;
  for(i=0;i<nid;i++) {
    for(a=j=0;j<nid;j++) printf("%f ",m[i][j]),a+=m[i][j];
    printf("=> %f\n",a);
  }
  printf("\n");
}
double temp[MAX][MAX];
void mulmatrix() {
  int i,j,k;
  for(i=0;i<nid;i++) for(j=0;j<nid;j++) {
    temp[i][j]=0;
    for(k=0;k<nid;k++) temp[i][j]+=m[i][k]*m[k][j];
  }
  for(i=0;i<nid;i++) for(j=0;j<nid;j++) m[i][j]=temp[i][j];
}
void normalize() {
  double sum;
  int i,j;
  for(i=0;i<nid;i++) {
    for(sum=j=0;j<nid;j++) sum+=m[i][j];
    for(j=0;j<nid;j++) m[i][j]/=sum;
  }
}
void expmatrix() {
  int iter=100,i,j,k;
  for(i=0;i<iter;i++) {
    mulmatrix();
    for(j=0;j<nid;j++) for(k=0;k<nid;k++) if(m[j][k]!=0 && 
      (fabs(m[j][k])<1e-100 || fabs(m[j][k])>1e100)) {
        /* avoid underflow/overflow */
        normalize();
        goto proceed;
    }
  proceed:;
  }
}
int main() {
  double a,b;
  int cases,i,j,id;
  scanf("%d",&cases);
  while(cases--) {
    for(i=0;i<26;i++) scanf("%lf",&pr[i]);
    scanf("%s %s",p,q);
    pl=strlen(p); ql=strlen(q);
    findstates();
    creatematrix();
    expmatrix();
    for(a=b=i=0;i<=pl;i++) for(j=0;j<=ql;j++) if((id=statestoid[i][j])>-1) {
      if(i==pl) id=statestoid[i][j],a+=m[0][id];
      if(j==ql) id=statestoid[i][j],b+=m[0][id];
    }
    printf("%.14f\n",a/(a+b));
  }
  return 0;
}
