# Place in folder with the spreadsheet files

import csv

def main():
    brute1 = readFile("Brute_Force_v1_Results.csv")
    brute2 = readFile("Brute_Force_v2_Results.csv")
    e1 = readFile("Original_Euclid_Results.csv")
    e2 = readFile("Second_Euclid_Results.csv")

    with open("Conclusions.txt", "w") as file:
        pairs, time_saved = compare(brute2, brute1)
        file.write("Out of 100 pairs of integers, brute-force (v2) outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare(e1, brute1)
        file.write("Out of 100 pairs of integers, the original version of Euclid outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare(e2, brute1)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed brute-force (v1) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare(e1, brute2)
        file.write("Out of 100 pairs of integers, the original version of Euclid outperformed brute-force (v2) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare(e2, brute2)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed brute-force (v2) in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

        pairs, time_saved = compare(e2, e1)
        file.write("Out of 100 pairs of integers, the second version of Euclid outperformed the original one in " +
                   str(pairs) + " pairs; and the average time saved for these " + str(pairs) +
                   " pairs of integers was " + str(time_saved) + " milliseconds \n")

def readFile(filename):
    array = []
    with open(filename, "r") as file:
        next(file)
        read = csv.reader(file, delimiter=",")
        for line in read:
            array.append(DataPoint(line[0], line[1], line[2], line[3]))
    return array

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

def compare(alg1, alg2):
    ctr = 0
    time1 = 0
    time2 = 0
    for n in range(0, 100):
        if float(alg1[n].time) < float(alg2[n].time):
            ctr += 1
            time1 = float(alg1[n].time)
            time2 = float(alg2[n].time)
    avg_time_saved = float((time2/ctr) - (time1/ctr))
    return ctr, avg_time_saved

if __name__ == "__main__":
    main()
