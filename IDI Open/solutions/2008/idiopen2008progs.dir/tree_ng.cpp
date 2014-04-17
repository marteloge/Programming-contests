////////////////////////////////////////////////////////////////////////////////
/* Sample solution for Tree of Pain
 * IDI Open 2008
 * Author: Nils Grimsmo
 */

// #define NODEBUG
const int MAX_PATTERN_NODES = 20;
typedef unsigned int sub_t; 

#include <algorithm>
#include <cassert>
#include <cctype>
#include <iostream>
#include <set>
#include <string>
#include <vector>

using namespace std;
using namespace __gnu_cxx;

#define FORI(i,n) for (int i = 0; i < n; ++i) 
#define ITER(i,A) for (typeof((A).begin()) i = (A).begin(); i != (A).end(); ++i)

class SetDenseSet {
  public:

  static bool covers(int x, int y) {
    return !(~x & y); 
  }

  int n; 
  vector<sub_t> V; 

  void init(int _n) {
    V.clear();
    n = _n; 
  }
  
  bool empty() const {
    return V.size() == 0; 
  } 

  bool isFull() const {
    return V.size() == 1 && V[0] == ((sub_t)1 << n) - 1;
  }

  void addSetOfOne(int x) {
    assert(x <= n);
    int s = (sub_t)1 << x; 
    ITER(i, V) if (covers(*i, s)) return;
    V.push_back(s);
  }

  void addCross(const SetDenseSet& other) {
    if (empty()) {
      V.insert(V.begin(), other.V.begin(), other.V.end());
    } else {
      assert(!other.empty());
      vector<sub_t> V2; 
      ITER(x, V) {
        ITER(y, other.V) {
          V2.push_back(*x | *y);
        } 
      }
      V.swap(V2);
      sort(V.begin(), V.end());
      typeof(V.begin()) i, j, k;
      for (i = j = V.begin(); j != V.end(); ++j) {
        bool c = false;
        for (k = j + 1; k != V.end(); ++k) {
          if (covers(*k, *j)) {
            c = true;
            break;
          }
        }
        if (!c) *(i++) = *j; 
      }
      V.erase(i, V.end());
    } 
  } 
};

class Node {
  public:
  string label;
  int post_num, post_num_int, child_num; 
  Node* parent; 
  vector<Node*> children;
  SetDenseSet S[MAX_PATTERN_NODES]; 
  int nS;

  Node(string labe) {
    label = labe; 
    post_num =  -1;
    post_num_int =  -1;
    child_num = 0; 
    parent = NULL; 
    nS = 0; 
  }

  virtual ~Node() {
    ITER(i, children) delete *i;
  }

  void addChild(Node* u) {
    u->child_num = children.size(); 
    children.push_back(u);
    u->parent = this; 
  }

  void enumerate(int& pn, int& pni) {
    ITER(c, children) (*c)->enumerate(pn, pni);
    post_num = pn++; 
    if (children.size()) post_num_int = pni++;
  } 

  bool computeMatches(const Node* p) {
    ITER(c, children) {
      if ((*c)->computeMatches(p)) return true; 
    } 
    prepare(p->parent); 
    nS = p->parent->post_num_int + 1;
    assert(nS <= MAX_PATTERN_NODES);
    ITER(c, children) {
      FORI(i, nS) if (!(*c)->S[i].empty()) {
        S[i].addCross((*c)->S[i]);
      }
    }
    return match(p);
  }

  bool match(const Node* p) {
    if (p->label == label && containsChildren(p)) {
      addMatch(p); 
      return true;  
    }
    ITER(c, p->children) {
      match(*c); 
    }
    return false; 
  }

  bool containsChildren(const Node* p) {
    assert(p);
    if (!p->children.size()) return true; 
    return S[p->post_num_int].isFull();
  } 

  void addMatch(const Node* p) {
    S[p->parent->post_num_int].addSetOfOne(p->child_num);
  }

  void prepare(const Node* p) {
    if (p->post_num_int == -1) return; 
    S[p->post_num_int].init(p->children.size());
    ITER(c, p->children) {
      prepare(*c);
    } 
  }
};

string buffer; 
size_t pos = 0; 

string next_token(bool advance) {
  if (pos == buffer.size()) return "";
  size_t q = pos; ++q; 
  if (isalpha(buffer[pos])) 
    while (q < buffer.size() && isalpha(buffer[q])) ++q; 
  string token = buffer.substr(pos, q - pos);
  if (advance) {
    pos = q; 
  }
  return token; 
}

Node* parse() {
  Node* u = new Node(next_token(true));
  if (next_token(false) == "(") {
    next_token(true);
    do {
      u->addChild(parse());
    } while (next_token(true) != ")"); 
  }
  return u; 
}

int main() {
  cin >> buffer; pos = 0;
  Node* T = parse();
  int pn = 0, pni = 0; 
  T->enumerate(pn, pni);
  int n; cin >> n;
  for (int i = 0; i < n; ++i) {
    cin >> buffer; pos = 0;
    Node* P = parse();
    Node* srP = new Node("_super_root_");
    srP->addChild(P);
    pn = 0, pni = 0; 
    srP->enumerate(pn, pni); 
    if (T->computeMatches(P)) cout << "disaster\n";
    else                      cout << "great success\n";
    delete P;
  }
  delete T;
}
