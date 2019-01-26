"""
Measures time execution for 2 variants of a Brute Force algorithm and 2 variants of Euclids.  Saves results/stats
to .csv files and generates a Conclusions.txt file.
"""

import os
import random
import statistics
import timeit

ARRAY_SIZE = 200  # 100 sets of pairs
RAND_MIN = 1  # Min value of randomly generated number
RAND_MAX = 1000000  # Max value of randomly generated number


# Outputs stats of algorithm time execution tests.  Option to save stats and full results to .csv file.
def main():
    # Array of size 200, with values ranging from 1 to the MAX
    # Values are used in pairs, so an array of size 200 will produce 100 test runs
    print("\nStarting Euclid Tests\n")

    print("Creating random inputs from " + str(RAND_MIN) + " to " + str(RAND_MAX) + " ... ", end="")
    numbers = create_random_array(ARRAY_SIZE, RAND_MIN, RAND_MAX)

    # Brute Force starting from 1
    print("Done\nRunning Brute Force (v1) ... ", end="")
    brute_v1_results = Results("Brute_Force_v1", run_test(brute_force_v1, numbers))

    # Brute Force starting from 'b'
    print("Done\nRunning Brute Force (v2) ... ", end="")
    brute_v2_results = Results("Brute_Force_v2", run_test(brute_force_v2, numbers))

    # Euclid no Subtraction
    print("Done\nRunning Original Euclid ... ", end="")
    euclid_original_results = Results("Original_Euclid", run_test(original_euclid, numbers))
    
    # Euclid with Subtraction
    print("Done\nRunning Second Euclid ... ", end="")
    euclid_second_results = Results("Second_Euclid", run_test(second_euclid, numbers))
    
    print("Done\n\nResults:\n")
    output_to_console(brute_v1_results, brute_v2_results, euclid_original_results, euclid_second_results)

    # Prompts user to save results
    if save_results():
        # Prompts user to save results, overwriting the files in the current directory.  The conclusion.txt is created
        # by the program from the stats/results .csv files
        print("\nSaving Results to .csv files ... ", end="")
        output_to_csv(brute_v1_results, brute_v2_results, euclid_original_results, euclid_second_results)
        output_conclusion(
            brute_v1_results.datapoints, brute_v2_results.datapoints, euclid_original_results.datapoints, euclid_second_results.datapoints)
        print("Done\nGenerating Conclusion.txt ... ", end="")
        print("Done\n\nFiles saved to " + os.getcwd() + "\n")

    # Added so the console that appears when the executable is run doesn't dissappear automatically
    input("Press Enter to exit ")


########################################################################################################################
#  Classes
########################################################################################################################
class DataPoint(object):
    """
    Contains the information about each test run to use in results
    """

    def __init__(self, value1, value2, gcd, time):
        """
        :param value1: First value from random array pair
        :type: integer
        :param value2: Second value from random array pair
        :type: integer
        :param gcd: Calculated greatest comman divisor
        :type: integer
        :param time: Time elapsed during GCD calculation
        :type: float
        """
        self.value1 = value1
        self.value2 = value2
        self.gcd = gcd
        self.time = time

class Results(object):
    """
    Contains all datapoints for a given algorithm, as well as there calculated stats
    """
    def __init__(self, algorithm, datapoints):
        """
        :param algorithm: Name of the algorithm being used
        :type: string
        :param datapoints: complete list of test results
        :type: array of DataPoints
        """
        self.algorithm = algorithm
        self.datapoints = datapoints
        self.max = max(data_point.time for data_point in datapoints)  # max time
        self.min = min(data_point.time for data_point in datapoints)  # min time
        self.avg = sum(data_point.time for data_point in datapoints) / len(datapoints)  # avg time
        self.median = statistics.median(data_point.time for data_point in datapoints)  # median time


########################################################################################################################
#  Tested Algorithms
########################################################################################################################
# Starts iterating from 1 to 'b'
def brute_force_v1(a, b):
    gcd = 1
    for n in range(1, b + 1):
        if a % n == 0 and b % n == 0:
            gcd = n
    return gcd

# Iterate through all values of 'b', starting with 'b' and working down to 1
# If a number is a divisor of b, check if its also a divisor of 'a'
# Note 'b' is always <= a
def brute_force_v2(a, b):
    for n in range(b+1, 1, -1):
        if a % n == 0 and b % n == 0:
            return n
    return 1

#  Calculate the quotient using division until the remainder is 0
def original_euclid(a, b):
    remainder = None
    while 0 != remainder:
        quotient = a // b
        remainder = a - quotient * b
        a = b
        b = remainder
    return a

#  Calculate the quotient using subtraction and division until the remainder is 0
def second_euclid(a, b):
    remainder = None
    while 0 != remainder:
        remainder = a - b
        if remainder >= b:  # quotient > 1
            remainder = remainder - b
            if remainder >= b:  # quotient > 2
                remainder = remainder - b
                if remainder >= b:  # quotient > 3
                    remainder = a - b * (a // b)  # PUNT
        a = b
        b = remainder
    return a


########################################################################################################################
#  Time Execution Test
########################################################################################################################
# Runs n/2 GCD calc tests  for the given algorithm, where 'n' is the length of the number_array.
# Each of the algorithm's test values, the resulting GCD, and the execution time are saved as a
# DataPoint object and added to the array of test_results.
def run_test(function, number_array):
    test_results = []  # Array of Datapoint objects
    length = len(number_array) // 2
    number_array = iter(number_array)  # Use an iterator to make pulling pairs of values simple
    for i in range(0, length):
        # Gather random pair
        a = next(number_array)
        b = next(number_array)

        # preprocess
        if a <= 0 and b <= 0:
            return
        if a < b:
            tmp = a
            a = b
            b = tmp

        # Time GCD calc
        start_time = timeit.default_timer()
        gcd = function(a, b)
        stop_time = timeit.default_timer()

        # Add results to dataset.  Time is saved in milliseconds
        test_results.append(DataPoint(a, b, gcd, (stop_time - start_time) * 1000))
    return test_results


########################################################################################################################
#  Output Results
########################################################################################################################
# Outputs 2 files for each test.
# First is the complete set of all test results for the given algorithm.
# The second is the overall statistics for that algorithm
def output_to_csv(*argv):
    """
    :param argv:
    :type: array of Results objects
    """
    for arg in argv:
        # Complete Results of all GCD Calculations (val1, val2, GCD, and execution time)
        with open(arg.algorithm + "_Results.csv", 'w') as file:
            file.write("Number One,Number Two, GCD, Time Spent (Milliseconds)\n")
            file.write("\n".join(",".join((str(d.value1), str(d.value2), str(d.gcd), str(d.time))) for d in arg.datapoints))
        # Final Stats of the data (max, min, avg, and median
        with open(arg.algorithm + "_Statistics.csv", 'w') as file:
            file.write("Statistics,Milliseconds")
            file.write(",".join(("\nMaximum Time", str(arg.max))))
            file.write(",".join(("\nMinimum Time", str(arg.min))))
            file.write(",".join(("\nAverage Time", str(arg.avg))))
            file.write(",".join(("\nMedian Time", str(arg.median))))

# Displays the stats of all tests to console
def output_to_console(*argv):
    """
    :param argv:
    :type: array of Results objects
    """
    # Output Col Header
    print("%-12s " % ("Stats (ms)"), end="")
    for arg in argv:
        print("%-22s " % (arg.algorithm), end="")
    
    # Output Row Header
    print("\n%-12s " % ("Max"), end="")
    for arg in argv:
        print("%-22s " % (arg.max), end="")
    
    # Output Row Header
    print("\n%-12s " % ("Min"), end="")
    for arg in argv:
        print("%-22s " % (arg.min), end="")
    
    # Output Row Header
    print("\n%-12s " % ("Avg"), end="")
    for arg in argv:
        print("%-22s " % (arg.avg), end="")
    
    # Output Row Header
    print("\n%-12s " % ("Median"), end="")
    for arg in argv:
        print("%-22s " % (arg.median), end="")
    print("\n")

# Creates Conclusion.txt from the results
def output_conclusion(brute1, brute2, e1, e2):
    with open("Conclusions.txt", "w") as file:
        pairs, time_saved = compare_execution_time(brute2, brute1)
        file.write("Out of 100 pairs of integers, brute-force (v2) outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare_execution_time(e1, brute1)
        file.write("Out of 100 pairs of integers, the original version of Euclid outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare_execution_time(e2, brute1)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare_execution_time(e1, brute2)
        file.write("Out of 100 pairs of integers, the original version of Euclid outperformed brute-force (v2) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare_execution_time(e2, brute2)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed brute-force (v2) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare_execution_time(e2, e1)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed the original one in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")


########################################################################################################################
#  Helpers
########################################################################################################################
# Help function which creates the random array of test inputs
def create_random_array(array_length, rand_min, rand_max):
    rand = []
    for num in range(array_length):
        rand.append(random.randint(rand_min, rand_max))
    return rand

# Prompts user to save results to file
def save_results():
    user_in = input("Save Results? (y/n) ")
    if user_in == 'y':
        return True
    elif user_in == 'n':
        return False
    else:
        print("Incorrect input ... ")
        save_results()

#  Used by output_conclusion.  Returns number of pairs that are faster and the average time saved by the pairs
def compare_execution_time(alg1, alg2):
    ctr = 0
    time1 = 0
    time2 = 0
    for n in range(0, 100):
        if alg1[n].time < alg2[n].time:
            ctr += 1
            time1 = alg1[n].time
            time2 = alg2[n].time
    if ctr != 0:
        avg_time_saved = float((time2/ctr) - (time1/ctr))
        return ctr, avg_time_saved
    return 0, 0


########################################################################################################################
#  Run Script
########################################################################################################################
if __name__ == "__main__":
    main()
