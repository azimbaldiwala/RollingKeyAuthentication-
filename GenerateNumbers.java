import java.util.*;

class GenerateNumbers {

	public int[] lcm(int seed, int mod, int multiplier,
					int inc, int[] randomNums,
					int noOfRandomNum)
	{
		randomNums[0] = seed;

		for (int i = 1; i < noOfRandomNum; i++) {

			// Follow the linear congruential method
			randomNums[i] = ((randomNums[i - 1] * multiplier) + inc) % mod ;
		}
		return randomNums;
	}

}
