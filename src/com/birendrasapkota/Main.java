package com.birendrasapkota;

import java.util.Arrays;
import java.util.Scanner;


// This assignment is based on Edit Distance.
public class Main {
    public static void main(String[] args) {
        //Getting inputs from the user.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first Word:: ");
        String word1 = scanner.nextLine().toLowerCase();
        System.out.println("Enter second Word:: ");
        String word2 = scanner.nextLine().toLowerCase();

        /* passing words to the function as a parameters and storing the
        returned output (total number of operations) for displaying to the user.
         */
        int minOpr = operationPerform(word1, word2);
        System.out.println("Total operation needed to convert " + word1 + " to " + word2 + " == " + minOpr);
    }

    //Function for calculating minimum operations needed to perform the conversion.
    public static int operationPerform(String word1, String word2) {
        int length01 = word1.length();
        int length02 = word2.length();

        int[][] mda = new int[length01][length02];

        // initializing  -1 in all row and column of 2D Array so that we will know whether the user input any word or left blank.
        for (int[] arr : mda) {
            Arrays.fill(arr, -1);
        }
        return totalOperation(word1, word2, mda, length01 - 1, length02 - 1);
    }

    private static int totalOperation(String word1, String word2, int[][] mda, int i, int j) {
        /* If the 1st word is empty, then total number of operation
           will be as much as of the character in 2nd word */
        if (i < 0) {
            return j + 1;
        }
        /* If the 2nd word is empty, then total number of operation
           will be as much as of the character in 1st word */
        else if (j < 0) {
            return i + 1;
        }
        // if the both words are empty.it will return 0
        if (mda[i][j] != -1) {
            return mda[i][j];
        }
        //For match cases
        // if the last character of word 1 matches the last character of word 2. No operation is needed.
        // And Recurring method is performed with decreased index of i and j by 1 for the next character check.
        if (word1.charAt(i) == word2.charAt(j)) {
            mda[i][j] = totalOperation(word1, word2, mda, i - 1, j - 1);
        }

        // For mismatch case.
        else {
            int opr = Math.min(totalOperation(word1, word2, mda, i, j - 1), totalOperation(word1, word2, mda, i - 1, j));
            opr = Math.min(opr, totalOperation(word1, word2, mda, i - 1, j - 1));
            mda[i][j] = 1 + opr;
        }
        return mda[i][j];
    }
}
/*
EXPLANATION FOR MISMATCH CASES
taking following as an example.  converting, "ABCD" into "Ball"
	 ‘’‘’	A	    B	    C	    D
““	1,1 	1,2	    1,3	    1,4	    1,5
B	2,1	    2,2	    2,3	    2,4	    2,5
A	3,1 	3,2	    3,3     3,4     3,5
L	4,1     4,2     4,3     4,4     4,5
l	5,1     5,2     5,3     5,4     5,5

converting case will be like.
	 ‘’‘’	A	    B	    C	    D
““	0       1       2       3       4
B	1       1       1       2       3
A	2       1       2       2       3
L	3       2       2       3       3
l	4       3       3       3       4

for the operation in index (3,3), it requires either insertion or deletion. For this we have 3 path of performing operation.
Either through index  (2,3) or (2,2) or (3,2). We will chose minimum operation path by comparing all those three.

After finding the minimum operation, the value is returned to the main function and store in the variable "minOpr" to print out.
 */