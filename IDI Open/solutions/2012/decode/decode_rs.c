#include <stdio.h>
#include <ctype.h>

char s[1024];
char t[]="abcdefghijklmnopqrstuvwxyz ";

int main() {
	int T,i,v;
	fgets(s,1024,stdin);
	sscanf(s,"%d",&T);
	while(T--) {
		fgets(s,1024,stdin);
		for(v=i=0;s[i];i++) if(isspace(s[i])) putchar(t[v%27]),v=0;
		else v+=s[i]-'a';
		putchar('\n');
	}
	return 0;
}
