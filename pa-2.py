# Robert Allen, William Blackwell, Bradley Sutton
# Implement the merge-sort algorithm on 9 random arrays and output to an excel sheet

import os
import random
import timeit
import math
from decimal import Decimal  # used for scientific notation

ARRAY_SIZE = 1000  # 100 sets of pairs
RAND_MIN = 1  # Min value of randomly generated number
RAND_MAX = 1000000  # Max value of randomly generated number
NUM_RUNS = 9  # number of test arrays created.  size determined by ARRAY_SIZE * the run interval


# Outputs stats of algorithm time execution tests
def main():
    print("Creating Arrays ... ", end="")
    test_results = run_tests(ARRAY_SIZE, NUM_RUNS)
    print("Done.\n\nSaving results to file ... ", end="")
    output_to_csv(test_results)
    print("Done.\nFile saved to " + os.getcwd() + "\Mergesort_Time.csv")
    console_interface(test_results)


########################################################################################################################
#  Classes
########################################################################################################################
class MergeSortResults(object):
    """
    Contains the information about each sort test to use in file/console output
    :param unsorted_arr: Original random array
    :type: array of ints
    :param sorted_arr: Sorted array
    :type: array if ints
    :param size: size fo array
    :type: integer
    :param time: Time elapsed during GCD calculation
    :type: float
    """
    def __init__(self, unsorted_arr, sorted_arr, size, time):
        self.unsorted_arr = unsorted_arr
        self.sorted_arr = sorted_arr
        self.size = size
        self.time = time
        self.nlogn = math.log(size, 2) * size  # n*log(n) for the array
        # scientific notation rounded to nearest int of n*log(n)/time
        self.nlogn_over_time = '%0.E' % Decimal(self.nlogn / self.time)


########################################################################################################################
#  MergeSort Functions
########################################################################################################################
# Merge elements from split arrays (left and right)
def merge(merged_array, left, right):
    merged_ctr = 0
    left_ctr = 0
    right_ctr = 0

    # compare the elements from each list.
    # the elements will be sorted in ascending order
    # the smaller element is added to the merged_array
    while left_ctr < len(left) and right_ctr < len(right):
        if left[left_ctr] < right[right_ctr]:
            merged_array[merged_ctr] = left[left_ctr]
            left_ctr += 1
        else:
            merged_array[merged_ctr] = right[right_ctr]
            right_ctr += 1
        merged_ctr += 1

    # copy the remaining elements from each array, left & right, to the merged list
    while left_ctr < len(left):
        merged_array[merged_ctr] = left[left_ctr]
        left_ctr += 1
        merged_ctr += 1
    while right_ctr < len(right):
        merged_array[merged_ctr] = right[right_ctr]
        right_ctr += 1
        merged_ctr += 1

    return merged_array


# Split the array in half and sort recursively
def mergesort(array):
    if len(array) == 1:
        return array
    else:
        # split
        left = array[:len(array) // 2]
        right = array[len(array) // 2:]

        # sort
        mergesort(left)
        mergesort(right)
        return merge(array, left, right)


########################################################################################################################
#  Time Execution Test
########################################################################################################################
# Measures execution time of each merge sort and saves results to an array of MergeSortResult objects
def run_tests(num_elements, num_runs):
    test_results = []  # Array of Datapoint objects
    for i in range(1, num_runs + 1):
        print(i, end=" ")  # output array_i num to console, e.g. 2 for array 2
        rand_array = create_random_array(num_elements * i, RAND_MIN, RAND_MAX)
        unsorted = rand_array[:]

        start_time = timeit.default_timer()  # start time measure
        sorted_array = mergesort(rand_array)
        stop_time = timeit.default_timer()  # end time measure

        # Add results to dataset.  Time is saved in milliseconds
        test_results.append(MergeSortResults(unsorted, sorted_array, num_elements * i, (stop_time - start_time) * 1000))
    return test_results


########################################################################################################################
#  Output Results
########################################################################################################################
# output results of 9 merge sorts to .csv file called 'Mergesort_Time.csv'
def output_to_csv(*argv):
    for arg in argv:
        with open("Mergesort_Time.csv", 'w') as file:
            file.write("Input size n for Array_i,Value of n*logn,Time Spent(ms),Value of (n*logn)/time\n")
            for a in arg:
                file.write(",".join((str(a.size), str(a.nlogn), str(a.time), str(a.nlogn_over_time))))
                file.write("\n")

# interface for user to request sorted and unsorted arrays of the test runs
def console_interface(test_results):
    print("\n\nEnter an array to display(1 - 9) or q to quit\n")
    while True:
        user_in = input("->  ")
        try:
            if user_in == 'q':
                print("Exiting ... \n")
                break
            print("%-12s %-22s %-22s" % ("Index", "Unsorted", "Sorted"))
            ctr = 0
            index = int(user_in) - 1
            # Display unsorted and sorted arrays side by side along with their index
            # e.g. -> INDEX   UNSORTED   SORTED
            for unsorted_arr, sorted_arr in zip(test_results[index].unsorted_arr, test_results[index].sorted_arr):
                print("%-12s %-22s %-22s" % (ctr, unsorted_arr, sorted_arr))
                ctr += 1
            print()
        except:
            print("Invalid input: ", user_in, "\n")


########################################################################################################################
#  Helpers
########################################################################################################################
# Help function which creates the random array of test inputs
def create_random_array(array_length, rand_min, rand_max):
    rand = []
    for num in range(array_length):
        rand.append(random.randint(rand_min, rand_max))
    return rand


########################################################################################################################
#  Run Script
########################################################################################################################
if __name__ == "__main__":
    main()
