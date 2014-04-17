#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <cstring>

// Square root of max input is sufficient
#define MAX 32000

using namespace std;

struct Div{
	int val;
	int num;
	Div(){}
	Div(int a, int b){val=a;num=b;}
};

vector<long long> getPrimes(long long max)
{
	vector<long long> prime;
	char* sieve;
	sieve = new char[max/8+1];
	memset(sieve, 0xFF, (max/8+1)*sizeof(char));
	for(long long x = 2; x <= max; ++x)
	{
		if(sieve[x/8] & (0x01 << (x%8)))
		{
			prime.push_back(x);
		}
		for(long long j = 2*x; j <= max; j += x)
		{
			sieve[j/8] &= ~(0x01 << (j%8));
		}
	}
	delete[] sieve;
	return prime;
}

bool isPrime(long long num, vector<long long>& prime)
{
	if(num == 1) return false;

	for(int i = 0; i < prime.size() && (num >= prime[i]*prime[i]); ++i)
	{
		if(num%prime[i] == 0) 
		{
			return false;
		}
	}
	return true;
}

void doInsaneRecursion(vector<long long>& prime, int pNum, long long currN, long long rem, vector<long long>& nums, vector<Div> preCalc[])
{
	if(rem == 1)
	{
		nums.push_back(currN);
		return;
	}
	if((rem-1) > MAX && isPrime(rem-1, prime))
	{
		nums.push_back((rem-1)*currN);
	}
	if(pNum == prime.size()){
		return;
	}

	for(int j = pNum; j < prime.size(); ++j)
	{
		for(int i = 0; i < preCalc[j].size(); ++i)
		{
			if(rem%preCalc[j][i].val == 0)
			{
				doInsaneRecursion(prime, j+1, currN*preCalc[j][i].num, rem/preCalc[j][i].val, nums, preCalc);
			}
			if(rem < preCalc[j][i].val) break;
		}
		if(rem < prime[j]) break;
	}
}

int main()
{
	int T;
	cin >> T;
	vector<long long> prime = getPrimes(MAX);
	while(T--)
	{
		long long N;
		cin >> N;
		vector<long long> nums;
		vector<Div> preCalc[MAX];
		for(int j = 0; j < prime.size(); ++j){
			long long tmpNum = prime[j];
			for(int i=1; i < 10000; ++i)
			{
				tmpNum*= prime[j];
				long long numerator = tmpNum-1;
				long long num = numerator/(prime[j]-1);
				if(N%num == 0)
				{
					preCalc[j].push_back(Div(num,tmpNum/prime[j]));
				}
				if(num > N) break;
			}
			if(prime[j] > N) break;
		}
		doInsaneRecursion(prime, 0, 1, N, nums, preCalc);
		if(nums.size() == 0) cout << "none!\n";
		else
		{
			sort(nums.begin(), nums.end());
			for(int i = 0; i < nums.size(); ++i)
			{
				cout << nums[i];
				if(i != nums.size()-1) cout << " ";
			}
			cout << endl;		
		}
	}
}

