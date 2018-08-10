package algoritmes;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class BubbleSort {

	public void sort(int arr[]) {
		int n = arr.length;

		//	Iterate over array
		for (int i = 0; i < n - 1; i++)
			//	Iterate from i and further
			for (int j = 0; j < n - i - 1; j++)
				//	Swap left with right, if necessary
				if (arr[j] > arr[j + 1]) {
					// swap temp and arr[i]
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
	}
}
