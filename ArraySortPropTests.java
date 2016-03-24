import org.junit.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class ArraySortPropTests {

    private static final int numTestArrays = 150;

    private int[][] generateRandomArrays(int maxSize){
        int[][] randArrs = new int[numTestArrays][];
        Random rand = new Random();
        for(int i = 0; i < numTestArrays; i++){
            int arrSize = rand.nextInt(maxSize); // limit length of test arrays to 1000000. Ensures arrays are positive.
            randArrs[i] = new int[arrSize];
            for(int j = 0; j < arrSize; j++){
                randArrs[i][j] = rand.nextInt();
            }
        }
        return randArrs;
    }

    /*

    Properties to test for:
        - Output array same size as passed-in array
        - Values in output array always increasing or the same
        - Value in output array never decreasing
        - Every element in input array is in output array
        - idempotent
        - Pure

    Inputs are controlled by generateRandomArrays(), which ensures that:
        - Values stored in the arrays are integers
        - The length of the arrays is zero or greater

     */

    // Tests for input and output arrays being the same size

    @Test
    public void sizeTest(){
        int[][] testArrs = generateRandomArrays(1000000);

        for(int i = 0; i < numTestArrays; i++){
            int inputArrLength = testArrs[i].length;

            Arrays.sort(testArrs[i]);

            int outputArrLength = testArrs[i].length;

            assertEquals(inputArrLength, outputArrLength);
        }
    }

    // Tests for values in output array always increasing or the same

    @Test
    public void sameOrIncreasingTest(){
        int[][] testArrs = generateRandomArrays(1000000);

        for(int i = 0; i < numTestArrays; i++){

            Arrays.sort(testArrs[i]);

            for(int j = 0; j < testArrs[i].length - 1; j++){
                assertTrue( (testArrs[i][j] <= testArrs[i][j+1]) );
            }
        }
    }

    // Tests for values in output array never decreasing

    @Test
    public void neverDecreasingTest(){
        int[][] testArrs = generateRandomArrays(1000000);

        for(int i = 0; i < numTestArrays; i++) {

            Arrays.sort(testArrs[i]);

            for (int j = 0; j < testArrs[i].length - 1; j++) {
                assertFalse((testArrs[i][j] > testArrs[i][j + 1]));
            }
        }
    }


    // Tests every element in input array is in output array

    @Test
    public void containsAllElementsTest(){
        int[][] testArrs = generateRandomArrays(10000); // smaller max size due to time constraints.

        for(int i = 0; i < numTestArrays; i++){
            int[] sorted = testArrs[i].clone();

            Arrays.sort(sorted);

            for(int j = 0; j < testArrs[i].length; j++){
                int mustContain = testArrs[i][j];
                boolean doesContain = false;
                for(int k = 0; k < sorted.length; k++){
                    if(sorted[k] == mustContain){
                        doesContain = true;
                    }
                }
                assertTrue(doesContain);
            }
        }


    }

    // Tests arrays are idempotent

    @Test
    public void idempotentTest(){
        int[][] testArrs = generateRandomArrays(1000000);

        for(int i = 0; i < numTestArrays; i++){
            Arrays.sort(testArrs[i]);
            int[] initialSortedArray = testArrs[i].clone();

            Arrays.sort(testArrs[i]);
            int[] secondarySortedArray = testArrs[i].clone();

            assertArrayEquals(initialSortedArray,secondarySortedArray);
        }
    }

    // Tests purity
    @Test
    public void purityTest(){
        int[][] testArrs = generateRandomArrays(1000000);

        for(int i = 0; i < numTestArrays; i++){
            int[] a = testArrs[i].clone();
            int[] b = testArrs[i].clone();

            assertArrayEquals(a,b); // Not exactly necessary, but shows a and b and equal arrays.

            Arrays.sort(a);
            Arrays.sort(b);

            assertArrayEquals(a,b);

        }
    }
}
