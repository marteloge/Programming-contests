#include <iostream>
#include <string>

using namespace std;

int main(){
	int T;
	cin >> T >> ws;
	while(T--)
	{
		string tmpIn;
		getline(cin, tmpIn);
		int charVal = 0;
		for(int i = 0; i < tmpIn.length(); ++i)
		{
			if(tmpIn[i] != ' ') charVal += (tmpIn[i]-'a');
			if(tmpIn[i] == ' ' || (i+1)==tmpIn.length())
			{
				charVal %= 27;
				if(charVal == 26) cout << ' ';
				else cout << (char)('a'+charVal);
				charVal = 0;
			}
		}
		cout << endl;
	}
}

