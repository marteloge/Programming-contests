/*  naïve solution in c++ by ruben spaans
    try to insert/replace 'l','o' or delete a character at each position.
    do this recursive until we have done two changes.
    (it is always possible to do this with 3 changes by adding "lol" anywhere,
     so we don't search deeper).
    time complexity: O(n^5) (?)
*/

#include <iostream>
#include <string>
using namespace std;

int best;

void solve(string s,int depth) {
  int l=s.size();
  if(s.find("lol")!=string::npos) {
    if(best>depth) best=depth;
    return;
  }
  if(depth>=best-1) return;
  for(int i=0;i<=l;i++) {
    //  insert
    solve(s.substr(0,i)+"l"+s.substr(i),depth+1);
    solve(s.substr(0,i)+"o"+s.substr(i),depth+1);
    if(i==l) break;
    //  replace char at i
    solve(s.substr(0,i)+"l"+s.substr(i+1),depth+1);
    solve(s.substr(0,i)+"o"+s.substr(i+1),depth+1);
    //  delete char at i
    solve(s.substr(0,i)+s.substr(i+1),depth+1);
  }
}

int main() {
  int cases;
  cin >> cases;
  while(cases--) {
    string s;
    cin >> s;
    best=3;
    solve(s,0);
    cout << best << endl;
  }
}
