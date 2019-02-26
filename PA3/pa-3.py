import os
import random
import timeit
import math
from decimal import Decimal  # used for scientific notation

# FIB_NUMS = [19, 20, 30, 35, 40, 45, 50, 55]
FIB_NUMS = [19, 20, 30]

FILENAME = "Fibonacci_Time.csv"
HEADERS = ["T1: Recursive Algorithm (ms)", "T2: DP Algorithm (ms)", "(2^n)/n", "T1/T2"]


def main():
    output_to_csv(FILENAME, HEADERS, run_tests())
    print("Done.\nFile saved to " + os.getcwd() + "\\" + FILENAME)
    console_interface()


########################################################################################################################
#  Classes
########################################################################################################################
class TestResults(object):
    def __init__(self, time_rec, time_dp, n):
        self.time_rec = time_rec
        self.time_dp = time_dp
        self.val1 = math.pow(2, n) / 2
        self.val2 = '%0.E' % Decimal(self.time_rec / self.time_rec)

    def to_string(self):
        return str(self.time_rec), str(self.time_dp), str(self.val1), str(self.val2)


########################################################################################################################
#  Fibonacci
########################################################################################################################
# Recursive
def recur_fib(num):
    if num <= 1:
        return num
    return recur_fib(num - 1) + recur_fib(num - 2)


# temp place holder
def dp(num):
    if num <= 1:
        return num
    return recur_fib(num - 1) + recur_fib(num - 2)


########################################################################################################################
#  Time Execution Test
########################################################################################################################
# Measures execution time of each merge sort and saves results to an array of MergeSortResult objects
def calc_time(alg, num):
    start_time = timeit.default_timer()  # start time measure
    fib_num = alg(num)
    stop_time = timeit.default_timer()  # end time measure
    return (stop_time - start_time) * 1000


def run_tests():
    results = []
    for i in FIB_NUMS:
        results.append(
            TestResults(
                calc_time(recur_fib, i),
                calc_time(dp, i),
                i
            )
        )
    return results


########################################################################################################################
#  Output Results
########################################################################################################################
# csv
def output_to_csv(filename, headers, data):
    with open(filename, 'w') as file:
        for header in headers:
            file.write(header)
            file.write(",")
        file.write("\n")
        for d in data:
            file.write(",".join(d.to_string()))
            file.write("\n")


# interface for user to request sorted and unsorted arrays of the test runs
def console_interface():
    while True:
        print("\nSelect Recursive(R) or Dynamic(D) Fibonacci Algorithm or q to quit\n")
        user_in = input("->  ")
        try:
            if user_in == 'q':
                print("Exiting ... \n")
                break
            elif user_in == 'R':
                print("\nEnter a Number\n")
                user_in = input("->  ")
                print(recur_fib(int(user_in)))
            elif user_in == 'D':
                print("\nEnter a Number\n")
                user_in = input("->  ")
                print(dp(int(user_in)))
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
