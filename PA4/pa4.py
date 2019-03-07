# Robert Allen, William Blackwell, Bradley Sutton
# Implements dynamic programming to all possible paths to the top of a staircase

def total_paths(possible_steps, total_steps, dp_map):
    """
    Finds all possible paths to the top of a staircase using the given
    possible steps
    :param possible_steps: List of possible step lengths. [1,3,4] means a step
    traversing 1,3, or 4 steps can be taken
    :param total_steps: total steps in the staircase
    :param dp_map: array store already solved subproblems of the solution
    :type: Dictionary of type <int, list of integers>
    :return: Populated mapping of all possible routes to the top
    """
    # A a route has not been previously solved, solve it
    if total_steps not in dp_map:
        dp_map[total_steps] = []  # init list containing the path
        for step_length in possible_steps:
            # evaluate remaining steps to reach the top
            steps_to_top = total_steps - step_length
            if steps_to_top >= 0:  # more steps to go
                for path in total_paths(possible_steps, steps_to_top, dp_map):
                    dp_map[total_steps].append(path + [step_length])
    return dp_map[total_steps]

def show_all_paths(possible_steps, total_steps, dp_map):
    """
    Outputs a map of all possible routes to the top of a staircase.
    :param possible_steps: List of possible step lengths. [1,3,4] means a step
    traversing 1,3, or 4 steps can be taken
    :param total_steps: total steps in the staircase
    :param dp_map: array store already solved subproblems of the solution
    :type: Dictionary of type <int, list of integers>
    """

    paths = total_paths(possible_steps, total_steps, dp_map)

    # Display total number of paths
    print("There are", len(paths), "ways")

    # Loop through and 'pretty print' the results
    if not paths:
        print("No paths to the top")
    for s in paths:
        for step in s[:-1]:
            print(step, end=" -> ")
        print(s[-1])

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
        if int(total_steps) <= 0:
            print("Can't climb 0 steps ... ")
            continue
        show_all_paths(possible_steps, total_steps, {0: [[]]})


def main():
    console_interface()

if __name__ == "__main__":
    main()
