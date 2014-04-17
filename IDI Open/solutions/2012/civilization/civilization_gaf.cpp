#include <iostream>

using namespace std;

int cityRes[18][3];

int main()
{
	int T;
	cin >> T;
	while(T--)
	{
		int N, W, C, F;
		cin >> N >> W >> C >> F;
		for(int i = 0; i < N; ++i)
		{
			cin >> cityRes[i][0] >> cityRes[i][1] >> cityRes[i][2];
		}

		int minNum = 19;
		for(int state = 0; state < (1<<N); ++state)
		{
			int totW = 0, totC = 0, totF = 0;
			int countCity = 0;
			for(int i = 0; i < N; ++i)
			{
				if( (state & (1<<i)) == (1<<i))
				{
					totW += cityRes[i][0];
					totC += cityRes[i][1];
					totF += cityRes[i][2];
					++countCity;
				}
			}
			if(totW >= W && totC >= C && totF >= F)
			{
				minNum = (countCity < minNum)?countCity:minNum;
			}
		}
		if(minNum <19) cout << minNum << endl;
		else cout << "game over\n";
	}
}


