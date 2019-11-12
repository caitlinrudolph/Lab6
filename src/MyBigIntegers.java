import java.io.FileWriter;
import java.io.PrintWriter;

public class MyBigIntegers {
    public static String Values;
    static int numberOfTrials = 50;
    static int MININPUTSIZE = 0;
    static int MAXINPUTSIZE = 128;
    static String ResultsFolderPath = "/home/caitlin/Documents/Lab6/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        //direct the verification test results to file
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        System.out.println("Running first full experiment...");
        runFullExperiment("fibFormulaBig-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment...");
        runFullExperiment("fibFormulaBig-Exp2.txt");
        System.out.println("Running third full experiment...");
        runFullExperiment("fibFormulaBig-Exp3.txt");

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
              //  fibFormulaBig(inputSize);

            }

            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            /* print data for this size of input */
            resultsWriter.printf("%12d  %12d  %15.2f \n", inputSize, Long.toBinaryString(inputSize).length(), averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }


    //CONSTRUCTORS

    //NO ARGUMENT INITIALIZE TO ZERO
    public MyBigIntegers()
    {
        Values = "0";
    }

    //A STRING ARGUMENT OF A DECIMAL INTEGER
    public MyBigIntegers(String decimal){
        Values = decimal;
    }


    //return the string value property
    public String ToString(String decimal) {
        return decimal;
    }

    public MyBigIntegers Plus(MyBigIntegers X){
        //returns a new biginteger with value of this.value + x.value
        //C = A.plus(B);
        // Before proceeding further, make sure length
        // of str2 is larger.
        if (Values.length() > X.toString().length()){
            String t = Values;
            Values = X.toString();
            X = new MyBigIntegers(t);
        }

        // Take an empty String for storing result
        String str = "";

        // Calculate length of both String
        int n1 = Values.length(), n2 = X.toString().length();
        int diff = n2 - n1;

        // Initially take carry zero
        int carry = 0;

        // Traverse from end of both Strings
        for (int i = n1 - 1; i>=0; i--)
        {
            // Do school mathematics, compute sum of
            // current digits and carry
            int sum = ((int)(Values.charAt(i)-'0') +
                    (int)(X.toString().charAt(i+diff)-'0') + carry);
            str += (char)(sum % 10 + '0');
            carry = sum / 10;
        }

        // Add remaining digits of str2[]
        for (int i = n2 - n1 - 1; i >= 0; i--)
        {
            int sum = ((int)(X.toString().charAt(i) - '0') + carry);
            str += (char)(sum % 10 + '0');
            carry = sum / 10;
        }

        // Add remaining carry
        if (carry > 0)
            str += (char)(carry + '0');

        // reverse resultant String
        String newVal = new StringBuilder(str).reverse().toString();

        return new MyBigIntegers(newVal);

    }

    public MyBigIntegers Times(MyBigIntegers X){
        //returns a new biginteger with value of this.value * x.value

        //C = A.Times(B);
        int len1 = Values.length();
        int len2 = X.toString().length();
        if (len1 == 0 || len2 == 0) {
            return new MyBigIntegers("0");
        }
        // will keep the result number in vector
        // in reverse order
        int result[] = new int[len1 + len2];

        // Below two indexes are used to
        // find positions in result.
        int i_n1 = 0;
        int i_n2 = 0;

        // Go from right to left in num1
        for (int i = len1 - 1; i >= 0; i--)
        {
            int carry = 0;
            int n1 = Values.charAt(i) - '0';

            // To shift position to left after every
            // multipliccharAtion of a digit in num2
            i_n2 = 0;

            // Go from right to left in num2
            for (int j = len2 - 1; j >= 0; j--)
            {
                // Take current digit of second number
                int n2 = X.toString().charAt(j) - '0';

                // Multiply with current digit of first number
                // and add result to previously stored result
                // charAt current position.
                int sum = n1 * n2 + result[i_n1 + i_n2] + carry;

                // Carry for next
                carry = sum / 10;

                // Store result
                result[i_n1 + i_n2] = sum % 10;

                i_n2++;
            }

            // store carry in next cell
            if (carry > 0)
                result[i_n1 + i_n2] += carry;

            // To shift position to left after every
            i_n1++;
        }

        // ignore '0's from the right
        int i = result.length - 1;
        while (i >= 0 && result[i] == 0)
            i--;

        // If all were '0's - means either both
        // or one of num1 or num2 were '0'
        if (i == -1)
            return new MyBigIntegers("0");
        String newVal = "";

        while (i >= 0)
            newVal += (result[i--]);

        return new MyBigIntegers(newVal);
    }


    public MyBigIntegers Minus(MyBigIntegers X){
        //returns a new biginteger with value of this.value - x.value

        //C = A.Minus(B);
        if (isSmaller(Values, X.toString()))
        {
            String t = Values;
            Values = X.toString();
            X =  new MyBigIntegers(t);
        }

        // Take an empty string for storing result
        String str = "";

        // Calculate lengths of both string
        int n1 = Values.length(), n2 = X.toString().length();
        int diff = n1 - n2;

        // Initially take carry zero
        int carry = 0;

        // Traverse from end of both strings
        for (int i = n2 - 1; i >= 0; i--)
        {
            // Do school mathematics, compute difference of
            // current digits and carry
            int sub = (((int)Values.charAt(i + diff) - (int)'0') -
                    ((int)X.toString().charAt(i) - (int)'0') - carry);
            if (sub < 0)
            {
                sub = sub+10;
                carry = 1;
            }
            else
                carry = 0;

            str += String.valueOf(sub);
        }

        // subtract remaining digits of str1[]
        for (int i = n1 - n2 - 1; i >= 0; i--)
        {
            if (Values.charAt(i) == '0' && carry > 0)
            {
                str += "9";
                continue;
            }
            int sub = (((int)Values.charAt(i) - (int)'0') - carry);
            if (i > 0 || sub > 0) // remove preceding 0's
                str += String.valueOf(sub);
            carry = 0;

        }

        // reverse resultant string
        String newVal = new StringBuilder(str).reverse().toString();
        return new MyBigIntegers(newVal);
    }
    // Returns true if str1 is smaller than str2,
// else false.
    static boolean isSmaller(String str1, String str2)
    {
        // Calculate lengths of both string
        int n1 = str1.length(), n2 = str2.length();

        if (n1 < n2)
            return true;
        if (n2 > n1)
            return false;

        for (int i = 0; i < n1; i++)
        {
            if (str1.charAt(i) < str2.charAt(i))
                return true;
            else if (str1.charAt(i) > str2.charAt(i))
                return false;
        }
        return false;
    }

}
