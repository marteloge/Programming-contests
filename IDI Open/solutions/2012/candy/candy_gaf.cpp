#include <iostream>

#define MOD 65537

using namespace std;

int N;
int cost[200];
int dp[10001][200];

int numPos(int needU, int numLeft)
{
	if(needU < 0) needU = 0;
	if(numLeft == (N-1))
	{
		if(needU <= 0) return 2;
		else if((needU-cost[numLeft]) <= 0) return 1;
		else return 0;
	}

	if(dp[needU][numLeft] != -1)
	{
		return dp[needU][numLeft];
	}

	int tot = (numPos(needU,numLeft+1)+numPos(needU-cost[numLeft],numLeft+1))%MOD;
	dp[needU][numLeft] = tot;
	return tot;
}

int main()
{
	int T;
	cin >> T;
	while(T--)
	{
		int C;
		cin >> N >> C;
		for(int i = 0; i < N; ++i)
		{
			cin >> cost[i];
		}
		for(int i = 0; i <= C; ++i)
		{
			for(int j = 0; j < N; ++j)
			{
				dp[i][j] = -1;
			}
		}
		cout << numPos(C, 0) << endl;
	}
}

