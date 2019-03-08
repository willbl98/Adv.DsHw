package edu.advalg;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class StairCase {


    private static StairHelper[] countOptions(int totalSteps, int[] stepSizes) {
        //Default Values are 0!!!! so you need to add 1 to it every time
        int[] steps = new int[totalSteps + 1];

        //Create the stair objects,
        StairHelper[] stairArray = new StairHelper[totalSteps + 1];
        for (int i = 0; i < totalSteps + 1; i++) {
            stairArray[i] = new StairHelper(i);
        }

        steps[0] = 0;
        for (int stepSizeIndex = 0; stepSizeIndex < stepSizes.length; stepSizeIndex++) {
            steps[stepSizes[stepSizeIndex]] = 1;

            //Add the possiblity
            ArrayList<Integer> newCombination = new ArrayList<Integer>();
            newCombination.add(new Integer(stepSizes[stepSizeIndex]));
            stairArray[stepSizes[stepSizeIndex]].addPossibility(newCombination);
        }


        int newIndex = 0;
        for (int stepIndex = 0; stepIndex < steps.length; stepIndex++) {

            //Check if Item is 0.
            //If so skip over it.
            if (steps[stepIndex] == 0) {
                continue;
            }

            //Add all combinations and increment those
            for (int stepSizeIndex = 0; stepSizeIndex < stepSizes.length; stepSizeIndex++) {
                newIndex = stepIndex + stepSizes[stepSizeIndex];

                if (newIndex <= totalSteps) {
                    steps[newIndex] += steps[stepIndex];

                    //Grab the Previous Versions using returnAll from our StairHelper function

                    ArrayList<ArrayList<Integer>> prevPossibilities = stairArray[stepIndex].returnAll();

                    //Go ThroughPrevious and update
                    for (int prevPoss = 0; prevPoss < prevPossibilities.size(); prevPoss++) {
                        ArrayList<Integer> updatedCombinations = new ArrayList<Integer>();

                        //Get add items to update Possibility
                        for (int item = 0; item < prevPossibilities.get(prevPoss).size(); item++) {
                            updatedCombinations.add(prevPossibilities.get(prevPoss).get(item));
                        }

                        //Add the new step
                        updatedCombinations.add(new Integer(stepSizes[stepSizeIndex]));

                        //Add to the stair array
                        stairArray[newIndex].addPossibility(updatedCombinations);
                    }

                }
            }
        }
        return stairArray;
    }

    private static void printStair(StairHelper stair, String message) {

        //Copying Elements over to String for Display
        String arrayString = "" + message + ": \n";
        String line;

        //Copy All possibilities to arrayString to display
        ArrayList<ArrayList<Integer>> allCombinations = stair.returnAll();
        // If there are no combinations for the entered values then this is returned
        if (allCombinations.size() == 0) {
            arrayString += "No paths possible! \n";
        }
        //Iterate through all possible combinations and format how it was assigned
        for (int i = 0; i < allCombinations.size(); i++) {
            ArrayList<Integer> possibility = allCombinations.get(i);
            line = "Path " + (i + 1) + ": ";
            for (int subIndex = 0; subIndex < possibility.size(); subIndex++) {
                if (subIndex == possibility.size() - 1) {
                    line += possibility.get(subIndex);
                } else {
                    line += possibility.get(subIndex) + " -> ";
                }
            }
            line += "\n";
            arrayString += line;
        }
        // Was just testing normal output into console, looks good but not the problem
        //System.out.print(arrayString);
        //Set to scrollable so you can see all combinations of larger values
        JTextArea textArea = new JTextArea(arrayString);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane.setPreferredSize(new Dimension(750, 1000));

        //Pass Info to JOptionPane

        JOptionPane.showMessageDialog(null, scrollPane, message, JOptionPane.INFORMATION_MESSAGE);

    }

    //Prompts the use for a value for number of stairs using a J Option Pane
    private static int getTotalStairs() {
        int numSteps;
        String desiredSteps = JOptionPane.showInputDialog("Enter the number of steps for your staircase (n>0)");
        if (desiredSteps == null) {
            return 0;
        }
        numSteps = Integer.valueOf(desiredSteps);
        return numSteps;
    }

    // This is where the user enters the step sizes allowed
    private static int[] getStepSizes() {
        String desiredSizes = JOptionPane.showInputDialog("Enter the step sizes separated by a ',' and spaces. For example: 1,2,3");
        if (desiredSizes == null) {
            int[] stepSizes = new int[1];
            return stepSizes;
        }

        String[] splitString = desiredSizes.split(",", 0);

        int[] stepSizes = new int[splitString.length];
        String step;
        for (int i = 0; i < splitString.length; i++) {

            // REMOVE WHITESPACE
            step = splitString[i];
            step = step.replaceAll(" ", "");
            stepSizes[i] = Integer.valueOf(step);
        }

        return stepSizes;
    }

    //Simply displays a string passed in using a JOption pane
    private static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    public static void main(String[] args) {

        //get user values by calling these 2 functions
        int numberOfSteps = getTotalStairs();
        int[] stepSizes = getStepSizes();


        displayMessage("Calculating total number of paths! (This could take a long time for high numbers of steps and step sizes)");
        //Stair paths are calculated first.
        //Rather than waiting for userInput then calculating
        StairHelper[] stairs = countOptions(numberOfSteps, stepSizes);

        //Loop for particular stair
        int keyStair = 0;
        String message = "";
        while (true) {
            String desiredKeyStair = JOptionPane.showInputDialog("Enter the stair you wish to find the possible paths for\n" +
                    "(Note there are " + numberOfSteps +" total stairs in the staircase): ");
            if (desiredKeyStair == null) {
                break;
            }

            keyStair = Integer.valueOf(desiredKeyStair);

            if (keyStair >= stairs.length) {
                displayMessage("Please enter a key stair that is <= to the total number of stairs!");
                continue;
            }

            message = "Displaying Paths for the key stair: " + keyStair;
            StairHelper indStair = stairs[keyStair];
            printStair(indStair, message);

        }
        displayMessage("Thank you for using my program!");
    }

}


