#include <iostream>

using namespace std;

int main()
{
	int T;
	cin >> T;
	while(T--)
	{
		int N, k;
		cin >> N >> k;
		double prob = 0.0;
		double tmpProb = 1.0;
		for(int i = 0; i < (N/k); ++i)
		{
			for(int j = 0; j < k; ++j)
			{
				tmpProb *= (N-k*i-j)/static_cast<double>(N-1);
			}
			tmpProb *= 1.0/((i+1)*k);
			prob += (-1+2*(i%2==0))*tmpProb;
		}
		cout.precision(16);
		cout.setf(ios::fixed | ios::showpoint);
		cout << prob << endl;
	}
}

