# Robert Allen, William Blackwell, Bradley Sutton
# Implement the merge-sort algorithm on 9 random arrays and output to an excel sheet

import os
import random
import nump as np

size = i

ran_min = 1
ran_max = 1000
array_size = 20

def main():
    print("\n Starting Merge Sort Algorithm\n")
    # User enters input for arbitrary " i "
    i = input("Select a number from 1-9")

    print("\n Creating 9 Random arrays from\n")

    # Array 1 results
    print("\n Array 1 complete")
    Array_1_results = Results(" Array_1_results", run_test(brute_force_v1, numbers))

    #Array 2 Results
    print("\n Array 2 complete")
    Array_2_results = Results(" Array_2_results")

    #Array 3 Results
    print("\n Array 3 complete")
    Array_3_results = Results(" Array_3_results")

    #Array 4 Results
    print("\n Array 4 complete")
    Array_4_results = Results(" Array_4_results")

    # Array 5 Results
    print("\n Array 5 complete")
    Array_5_results = Results(" Array_5_results")

    #Array 6 Results
    print("\n Array 6 complete")
    Array_6_results = Results(" Array_6_results")

    #Array 7 Results
    print("\n Array 7 complete")
    Array_7_results = Results(" Array_7_results")



########################################################################################################################
#           Merge Sort Algorithm                                                                                       #
########################################################################################################################
def msort(x):
    result = []
    if len(x) < 2:
        return x
    mid = int(len(x)/2)
    y = msort(x[:mid])
    z = msort(x[mid:])
    while (len(y) > 0) or (len(z) > 0):
        if len(y) > 0 and len(z) > 0:
            if y[0] > z[0]:
                result.append(z[0])
                z.pop(0)
            else:
                result.append(y[0])
                y.pop(0)
        elif len(z) > 0:
            for i in z:
                result.append(i)
                z.pop(0)
        else:
            for i in y:
                result.append(i)
                y.pop(0)
    return Results