public class MyBigIntegers {
    public static String Values;
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
        // of X is larger.
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

        // Add remaining digits of X[]
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
            // multiplication of a digit in num2
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

        // Calculate lengths of both strings
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

        // subtract remaining digits of values[]
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
        // Calculate lengths of both strings
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
