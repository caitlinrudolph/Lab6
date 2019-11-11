import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;

public class fibMatrixBig {

    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    /* define constants */
    static int numberOfTrials = 50;
    static int MININPUTSIZE = 0;
    static int MAXINPUTSIZE = 128;
    static String ResultsFolderPath = "/home/caitlin/Documents/Lab5/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        //direct the verification test results to file
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        System.out.println("Running first full experiment...");
        runFullExperiment("fibMatrixBig-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment...");
        runFullExperiment("fibMatrixBig-Exp2.txt");
        System.out.println("Running third full experiment...");
        runFullExperiment("fibMatrixBig-Exp3.txt");

    }

    static void runFullExperiment(String resultsFileName) {
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        resultsWriter.println("#X(value)    N(size)    T(time)"); // # marks a comment in gnuplot data
        resultsWriter.flush();
        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for (int inputSize = MININPUTSIZE; inputSize <= MAXINPUTSIZE; inputSize++) {
            // progress message...
            System.out.println("Running test for input size " + inputSize + " ... ");

            /* repeat for desired number of trials (for a specific size of input)... */
            long batchElapsedTime = 0;
            // generate a list of randomly spaced integers in ascending sorted order to use as test input
            // In this case we're generating one list to use for the entire set of trials (of a given input size)
            // but we will randomly generate the search key for each trial

            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();

            TrialStopwatch.start(); // *** uncomment this line if timing trials individually
            // run the trials
            for (long trial = 0; trial < numberOfTrials; trial++) {
                FibMatrix(inputSize);

            }

            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            /* print data for this size of input */
            resultsWriter.printf("%12d  %12d  %15.2f \n", inputSize, Long.toBinaryString(inputSize).length(), averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    public static BigInteger FibMatrix(long X){
        BigInteger F[][] = new BigInteger[][]{{BigInteger.valueOf(1),BigInteger.valueOf(1)},{BigInteger.valueOf(1),BigInteger.valueOf(0)}};
        if (X== 0)
            return BigInteger.valueOf(0);
        MatrixPower(F, X-1);

        return F[0][0];
        //take a single 8-byte unsigned integer and return a single 8-byte result

        //reminder: towards end of video, y would be the variable we want the fibonacci of  (we want fib(y) there)

        //Calculating MX should take O(log2(X)) matrix multiplications instead of the naïve O(X).
        //Note that because the size (number of bits) of N ~ log2(X), calculating MX should take O(N) multiplications.

    }

    public static void MatrixPower(BigInteger[][] F, long X){
        //Write a helper function called by MatrixPower that efficiently calculates powers of a matrix
        int i;
        BigInteger M[][] = new BigInteger[][]{{BigInteger.valueOf(1), BigInteger.valueOf(1)}, {BigInteger.valueOf(1), BigInteger.valueOf(0)}};

        // n - 1 times multiply the matrix to {{1,0},{0,1}}
        for (i = 2; i <= X; i++)
            MatrixMultiplication(F, M);
    }

    public static void MatrixMultiplication(BigInteger F[][], BigInteger M[][]){
        //which, in trun needs a helper function to do simple matrix multiplication
        BigInteger x =  (F[0][0].multiply(M[0][0])).add(F[0][1].multiply(M[1][0]));
        BigInteger y =  (F[0][0].multiply(M[0][1])).add(F[0][1].multiply(M[1][1]));
        BigInteger z =  (F[1][0].multiply(M[0][0])).add(F[1][1].multiply(M[1][0]));
        BigInteger w =  (F[1][0].multiply(M[0][1])).add(F[1][1].multiply(M[1][1]));

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    //•	this function uses the fibonacci formula to calculate fib(x) directly -- use double precision (8 byte) floating point type
    //•	find the higheset fib(x) you can calculate exactly with this function (discuss in writeup)
    //•	note: floating point numbers have fewer significant digits than the same size integer type, so while you can calculate larger numbers, the largest number you can calculate accurately will be smaller that with integer of same size
    public static BigInteger fibFormula{


    }

    //•	calculate exact fibonacci numbers using java BigDecimal
    //•	find the largest fibonacci number you can handle (either time or space or both)
    public static BigInteger fibFormulaBig{

    }
}
