import os
import random
import statistics
import timeit

"""
Outputs to .csv file. No need to install modules for this one
"""


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
        :param time: Time ellapsed during GCD calculation
        :type: float
        """
        self.value1 = value1
        self.value2 = value2
        self.gcd = gcd
        self.time = time


# Iterate through all values of 'b', starting with 'b' and working down to 1
# If a number is a divisor of b, check if its also a divisor of 'a'
# Note 'b' is always <= a
def brute_force(a, b):
    i = b
    for n in range(1, b):
        if a % i == 0 and b % i == 0:
            return i
        i -= 1
    return 1


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


def run_test(function, length, number_array):
    datapoints = []  # Array of Datapoint objects
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
        datapoints.append(DataPoint(a, b, gcd, (stop_time - start_time) * 1000))
    return datapoints


"""
Function to Output Results
"""


def output_to_csv(alg_name, data):
    # Results of all GCD Calculations
    with open(alg_name + "_Results.csv", 'w') as file:
        file.write("Number One,Number Two, GCD, Time Spent (Milliseconds)\n")
        for data_point in data:
            file.write(
                str(data_point.value1) + "," + str(data_point.value2) + "," + str(data_point.gcd) + "," + str(
                    data_point.time) + "\n")
    # Final Stats of the data
    with open(alg_name + "_Statistics.csv", 'w') as file:
        file.write("Statistics,Milliseconds\n")
        file.write("Maximum Time," + str(max(data_point.time for data_point in data)) + "\n")
        file.write("Minimum Time," + str(min(data_point.time for data_point in data)) + "\n")
        file.write("Average Time," + str(sum(data_point.time for data_point in data) / len(data)) + "\n")
        file.write("Median Time," + str(statistics.median(data_point.time for data_point in data)) + "\n")


"""
Helpers
"""


def create_random_array(array_length, rand_min, rand_max):
    rand = []
    for num in range(array_length):
        rand.append(random.randint(rand_min, rand_max))
    return rand


"""
Run Program
"""


if __name__ == "__main__":
    # Array of size 200, with values ranging from 1 to 999
    # Values are used in pairs, so an array of size 200 will produce 100 test runs
    array_size = 200
    rand_min = 1
    rand_max = 999

    print("\nStarting Euclid Tests\n")

    # Creates a directory on the host in the current path called PA-1-Results, if it did not previously
    # exist.  The output files will be written here.  Any prexisting output files will be overwritten
    if not os.path.exists("PA-1-Results"):
        print("Creating directory PA-1-Results ... ", end="")
        os.mkdir("PA-1-Results")
        print("Done\n")
    os.chdir("PA-1-Results")

    print("Creating random inputs from " + str(rand_min) + " to " + str(rand_max) + " ... ", end="")
    numbers = create_random_array(array_size, rand_min, rand_max)
    length = len(numbers) // 2

    print("Done\nRunning Brute Force ... ", end="")
    output_to_csv("Brute_Force", run_test(brute_force, length, iter(numbers)))

    print("Done\nRunning Original Euclid ... ", end="")
    output_to_csv("Original_Euclid", run_test(original_euclid, length, iter(numbers)))

    print("Done\nRunning Second Euclid ... ", end="")
    output_to_csv("Second_Euclid", run_test(second_euclid, length, iter(numbers)))

    print("Done\n")
    print("Finished.  Results saved to " + os.getcwd())

    # Added so the console that appears when the executable is run doesn't dissappear automatically
    input("Press Enter to exit ")
