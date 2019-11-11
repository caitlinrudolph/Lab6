public class MyBigIntegers {
    static string Value;


    //CONSTRUCTORS

    //NO ARGUMENT INITIALIZE TO ZERO
    public MyBigInteger ()
    {
        Value = new MyBigIntegers();
    }

    //A STRING ARGUMENT OF A DECIMAL INTEGER
    public MyBigInteger(int value){

    }

    //return the string value property
    public String ToString(int val) {
    return val.toString();
    }

    public MyBigIntegers Plus(MyBigIntegers x){
        //returns a new biginteger with value of this.value + x.value

        //C = A.plus(B);
        return new MyBigIntegers(newVal);
    }

    public MyBigIntegers Times(MyBigIntegers x){
        //returns a new biginteger with value of this.value * x.value

        //C = A.Times(B);
        return new MyBigIntegers(newVal);
    }

    public MyBigIntegers TimesFaster(MyBigIntegers x){
        //faster multiplication alg

        //C = A.TimesFaster(B);
        return new MyBigIntegers(newVal);
    }

    public MyBigIntegers Minus(MyBigIntegers x){
        //returns a new biginteger with value of this.value - x.value

        //C = A.Minus(B);
        return new MyBigIntegers(newVal);
    }


}
