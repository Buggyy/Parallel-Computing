package java.algoritmes;

/**
 * Parallel-Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class BubbleSort {

	void bubbleSort(int arr[]) {
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

	/**
	 * Print the array
	 *
	 * @param arr Array to print ou
	 */
	void printArray(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	/**
	 * Driver method to test printArray()
	 *
	 * @param args Method arguments
	 */
	public static void main(String args[]) {
		BubbleSort ob = new BubbleSort();
		int arr[] = {64, 34, 25, 12, 22, 11, 90};
		ob.bubbleSort(arr);
		System.out.println("Sorted array");
		ob.printArray(arr);
	}
}
