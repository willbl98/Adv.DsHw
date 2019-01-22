import os
import datetime
import random
import timeit
import statistics

ARRAY_SIZE = 200  # 100 sets of pairs
RAND_MIN = 1  # Min value of randomly generated number
RAND_MAX = 999  # Max value of randomly generated number


# Outputs stats of algorithm time execution tests.  Option to save stats and full results to .csv file.
def main():
    # Array of size 200, with values ranging from 1 to 999
    # Values are used in pairs, so an array of size 200 will produce 100 test runs
    print("\nStarting Euclid Tests\n")

    print("Creating random inputs from " + str(RAND_MIN) + " to " + str(RAND_MAX) + " ... ", end="")
    numbers = create_random_array(ARRAY_SIZE, RAND_MIN, RAND_MAX)

    # Brute Force starting from 'b'
    print("Done\nRunning Brute Force ... ", end="")
    brute_v1_results = Results("Brute_Force_v1", run_test(brute_force, numbers))
    
    # Brute Force starting from 1
    print("Done\nRunning Brute Force ... ", end="")
    brute_v2_results = Results("Brute_Force_v2", run_test(brute_force_second, numbers))
    
    # Euclid no Subtraction
    print("Done\nRunning Original Euclid ... ", end="")
    euclid_v1_results = Results("Original_Euclid", run_test(original_euclid, numbers))
    
    # Euclid with Subtraction
    print("Done\nRunning Second Euclid ... ", end="")
    euclid_v2_results = Results("Second_Euclid", run_test(second_euclid, numbers))
    
    print("Done\n\nResults:\n")
    output_to_console(brute_v1_results, brute_v2_results, euclid_v1_results, euclid_v2_results)

    # Prompts user to save results
    if save_results():
        # Creates a directory on the host in the current path called PA-1-Results, if it did not previously
        # exist.  The output files will be written here.  Any prexisting output files will be overwritten
        if not os.path.exists("PA-1-Results"):
            print("Creating directory PA-1-Results ... ", end="")
            os.mkdir("PA-1-Results")
            print("Done\n")
        os.chdir("PA-1-Results")

        trial_dir = os.path.join(os.getcwd(), datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S'))
        os.mkdir(trial_dir)
        os.chdir(trial_dir)

        output_to_csv(brute_v1_results, brute_v2_results, euclid_v1_results, euclid_v2_results)
        print("Done\n\nResults saved to " + os.getcwd() + "\n")

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
        self.max = max(data_point.time for data_point in datapoints)  #  max time
        self.min = min(data_point.time for data_point in datapoints)  # min time
        self.avg = sum(data_point.time for data_point in datapoints) / len(datapoints)  # avg time
        self.median = statistics.median(data_point.time for data_point in datapoints)  # median time


########################################################################################################################
#  Tested Algorithms
########################################################################################################################
# Iterate through all values of 'b', starting with 'b' and working down to 1
# If a number is a divisor of b, check if its also a divisor of 'a'
# Note 'b' is always <= a
def brute_force(a, b):
    for n in reversed(range(1, b + 1)):
        if a % n == 0 and b % n == 0:
            return n
    return 1

# Starts iterating from 1 to 'b'
def brute_force_second(a, b):
    gcd = 1
    for n in range(1, b + 1):
        if a % n == 0 and b % n == 0:
            gcd = n
    return gcd

def original_euclid(a, b):
    remainder = None
    while 0 != remainder:
        quotient = a // b
        remainder = a - quotient * b
        a = b
        b = remainder
    return a

def second_euclid(a, b):
    remainder = None
    while 0 != remainder:
        remainder = a - b
        if remainder >= b:
            remainder = remainder - b
            if remainder >= b:
                remainder = remainder - b
                if remainder >= b:
                    remainder = a - b * (a // b)
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


########################################################################################################################
#  Run Script
########################################################################################################################
if __name__ == "__main__":
    main()
