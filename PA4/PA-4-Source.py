# Robert Allen, William Blackwell, Bradley Sutton
# Implements dynamic programming to all possible paths to the top of a staircase
import os


def main():
    # set screen buffer to allow displaying the larger arrays
    os.system('mode con: cols=80 lines=12000')
    console_interface()


########################################################################################################################
#  Classes
########################################################################################################################
class StairClimber(object):
    """
    Holders information regarding how to climb a set of stairs and if a set can be climbed under
    the given conditions
    :param possible_steps: List of possible step lengths. [1,3,4] means a step
    traversing 1,3, or 4 steps can be taken
    :param total_steps: total steps in the staircase
    :param dp_map: array store already solved subproblems of the solution
    :type: Dictionary of type <int, list of integers>
    """

    def __init__(self, possible_steps, total_steps, dp_map):
        self.total_steps = total_steps
        self.possible_steps = possible_steps
        self.dp_map = dp_map

    def total_paths(self, remaining_steps):
        """
        Finds all possible paths to the top of a staircase using the given
        possible steps
        :param remaining_steps: steps remaining in to reach the top for the current path
        :return: Populated mapping of all possible routes to the top
        """
        # A route has not been previously solved, solve it
        if remaining_steps not in self.dp_map:
            self.dp_map[remaining_steps] = []  # init list containing the path
            for step_length in self.possible_steps:
                # evaluate remaining steps to reach the top
                steps_to_top = remaining_steps - step_length
                if steps_to_top >= 0:  # more steps to go
                    for path in self.total_paths(steps_to_top):
                        self.dp_map[remaining_steps].append(path + [step_length])
        return self.dp_map[remaining_steps]

    # Outputs a map of all possible routes to the top of a staircase.
    def show_all_paths(self):
        paths = self.total_paths(self.total_steps)

        # Display total number of paths
        print("There are", len(paths), "ways")

        # Loop through and 'pretty print' the results
        if not paths:
            print("No paths to the top")
            return
        with open("tmp.txt", 'w') as file:
            for s in paths:
                string = []
                for step in s:
                    string.append(str(step))
                line = ' -> '.join(string)
                file.write(line)
                file.write('\n')
        with open("tmp.txt", 'r') as fin:
            less = Scroll(num_lines=10000)
            fin.read() | less
        os.remove("tmp.txt")


class Scroll(object):
    """
    Helper class to restrict the console to only displaying the specified number of lines at a time.
    Especially useful since there could be thousands of paths
    """

    def __init__(self, num_lines):
        self.num_lines = num_lines

    def __ror__(self, data):
        lines = str(data).split("\n")
        for i in range(0, len(lines), self.num_lines):
            print(*lines[i: i + self.num_lines], sep="\n")
            input("Press 'Enter' to scroll down")


########################################################################################################################
#  Output Results
########################################################################################################################
# Asks the user for total steps and possible step lengths example input:
# Enter the number of stairs in the staircase: 6
# Enter the possible steps in csv form, e.g. 4,5,6: 1,2,4,5
def console_interface():
    while True:
        print("\nEnter the number of stairs in the staircase: ", end="")
        total_steps = input("")
        total_steps = int(total_steps)
        print("Enter the possible steps in csv form, e.g. 4,5,6: ", end="")
        possible_steps = input("")
        possible_steps = list(map(int, possible_steps.split(',')))
        if contains_invalid_values(possible_steps):
            print("Step lengths must be whole numbers greater than 0 ... ")
            continue
        if int(total_steps) <= 0:
            print("Can't climb 0 steps ... ")
            continue
        StairClimber(possible_steps, total_steps, {0: [[]]}).show_all_paths()


def contains_invalid_values(list):
    for n in list:
        if n <= 0:
            return True
    return False


########################################################################################################################
#  Run Script
########################################################################################################################
if __name__ == "__main__":
    main()
