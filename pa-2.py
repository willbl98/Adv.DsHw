# Robert Allen, William Blackwell, Bradley Sutton
# Implement the merge-sort algorithm on 9 random arrays and output to an excel sheet

import random
import timeit
import math

ARRAY_SIZE = 1000  # 100 sets of pairs
RAND_MIN = 1  # Min value of randomly generated number
RAND_MAX = 1000000  # Max value of randomly generated number

# Outputs stats of algorithm time execution tests
def main():
    test_results = run_test(ARRAY_SIZE)
    output_to_csv(test_results)


########################################################################################################################
#  Classes
########################################################################################################################
class DataPoint(object):
    """
    Contains the information about each test run to use in results
    """
    def __init__(self, unsorted_arr, sorted_arr, size, time):
        self.unsorted_arr = unsorted_arr
        self.sorted_arr = sorted_arr
        self.size = size
        self.time = time
        self.nlogn = math.log(size, 2)
        self.logn_over_time = self.nlogn / self.time


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
        left = array[:len(array)//2]
        right = array[len(array)//2:]

        # sort
        mergesort(left)
        mergesort(right)
        return merge(array, left, right)


########################################################################################################################
#  Time Execution Test
########################################################################################################################
def run_test(num_elements):
    test_results = []  # Array of Datapoint objects
    for i in range(1, 10):
        rand_array = create_random_array(num_elements * i, RAND_MIN, RAND_MAX)
        start_time = timeit.default_timer()
        sorted_array = mergesort(rand_array)
        stop_time = timeit.default_timer()
        # Add results to dataset.  Time is saved in milliseconds
        test_results.append(DataPoint(rand_array, sorted_array, num_elements * i, (stop_time - start_time) * 1000))
    return test_results


########################################################################################################################
#  Output Results
########################################################################################################################
def output_to_csv(*argv):
    for arg in argv:
        with open("Results.csv", 'w') as file:
            file.write("Size, nlogn,Time Spent (Milliseconds), nlogn/time\n")
            for a in arg:
                file.write(",".join((str(a.size), str(a.nlogn), str(a.time), str(a.logn_over_time))))
                file.write("\n")


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
