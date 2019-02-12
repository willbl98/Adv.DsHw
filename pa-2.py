# Robert Allen, William Blackwell, Bradley Sutton
# Implement the merge-sort algorithm on 9 random arrays and output to an excel sheet

import os
import random
import timeit
import math
from decimal import Decimal

ARRAY_SIZE = 1000  # 100 sets of pairs
RAND_MIN = 1  # Min value of randomly generated number
RAND_MAX = 1000000  # Max value of randomly generated number
NUM_RUNS = 9


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
class DataPoint(object):
    """
    Contains the information about each test run to use in file/console output
    """

    def __init__(self, unsorted_arr, sorted_arr, size, time):
        self.unsorted_arr = unsorted_arr
        self.sorted_arr = sorted_arr
        self.size = size
        self.time = time
        self.nlogn = math.log(size, 2) * size
        self.nlogn_over_time = '%0.E' % Decimal(self.nlogn / self.time)  # scientific notation rounded to nearest int


########################################################################################################################
#  MergeSort
########################################################################################################################
def merge(merged_array, left, right):
    merged_ctr = 0
    left_ctr = 0
    right_ctr = 0

    # place the elements in ascending order to the merged list
    while left_ctr < len(left) and right_ctr < len(right):
        if left[left_ctr] < right[right_ctr]:
            merged_array[merged_ctr] = left[left_ctr]
            left_ctr += 1
        else:
            merged_array[merged_ctr] = right[right_ctr]
            right_ctr += 1
        merged_ctr += 1

    # copy the remaining elements to the merged list
    while left_ctr < len(left):
        merged_array[merged_ctr] = left[left_ctr]
        left_ctr += 1
        merged_ctr += 1
    while right_ctr < len(right):
        merged_array[merged_ctr] = right[right_ctr]
        right_ctr += 1
        merged_ctr += 1

    return merged_array


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
def run_tests(num_elements, num_runs):
    test_results = []  # Array of Datapoint objects
    for i in range(1, num_runs + 1):
        print(i, end=" ")
        rand_array = create_random_array(num_elements * i, RAND_MIN, RAND_MAX)
        unsorted = rand_array[:]
        start_time = timeit.default_timer()
        sorted_array = mergesort(rand_array)
        stop_time = timeit.default_timer()
        # Add results to dataset.  Time is saved in milliseconds
        test_results.append(DataPoint(unsorted, sorted_array, num_elements * i, (stop_time - start_time) * 1000))
    return test_results


########################################################################################################################
#  Output Results
########################################################################################################################
def output_to_csv(*argv):
    for arg in argv:
        with open("Mergesort_Time.csv", 'w') as file:
            file.write("Input size n for Array_i,Value of n*logn,Time Spent(ms),Value of (n*logn)/time\n")
            for a in arg:
                file.write(",".join((str(a.size), str(a.nlogn), str(a.time), str(a.nlogn_over_time))))
                file.write("\n")


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
            for a, b in zip(test_results[index].unsorted_arr, test_results[index].sorted_arr):
                print("%-12s %-22s %-22s" % (ctr, a, b))
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
