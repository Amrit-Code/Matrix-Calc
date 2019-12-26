import java.util.*;
class MatrixCalc{
    public static Scanner scanner = new Scanner(System.in);
    //main method runs the program until user wants to quit
    public static void main(String[] args){
        while(true){
            String option = userOptions();
            processOption(option);
        }
    }
    // method to get users input
    public static String userOptions(){
        print("Please select a option:");
        print("(1) echelon form");
        print("(2) reduce echelon form");
        print("(3) inverse Matrix");
        print("(e) exit");
        return input();
    }

    //method used to process users input
    public static void processOption(String option){
        if(option.equals("1")){
            echelon();
        }else if(option.equals("2")){
            Matrix matrix = echelon();
            reduceEchelon(matrix);
        }else if(option.equalsIgnoreCase("e")){
            System.exit(0);
        }else if(option.equalsIgnoreCase("3")){
            inverseMatrix();
        }else{
            print("Could not understand input, please try again");
        }
    }

    //method that performs echlon process
    public static Matrix echelon(){
        Matrix matrix = new Matrix(getRow(),getColoums());
        populateMatrix(matrix);
        printMatrix(matrix);
        for(int c = 0; c < matrix.getColoums(); c ++){
            if(c + 1== matrix.getRows() && matrix.getColoums() == matrix.getRows()){
                break;
            }
            fixColoum(c, matrix);
        }

        return matrix;
    }

    public static Matrix echelon(Matrix matrix){
        printInverseMatrix(matrix);
        for(int c = 0; c < matrix.getColoums(); c ++){
            if(c + 1== matrix.getRows() && matrix.getColoums() == matrix.getRows()){
                break;
            }
            fixColoum(c, matrix);
        }

        return matrix;
    }

    //set the leading 1 for a given coloum
    public static void fixColoum(int c, Matrix matrix){
        int rowNO = -1;
        //check if any row starts with 1
        for(int r = c; r < matrix.getRows(); r++){
            if(matrix.get(r, c).get() == 1){
                rowNO = r;
                break;
            }
        }

        if(rowNO != -1){
            // if a row starts with 1, swap to make it the leading 1
            swapRow(c,rowNO, matrix);
            print("Row" + Integer.toString(c+1) + " swaped with Row" + Integer.toString(rowNO+1));
            printMatrix(matrix);
        }else{
            // check if a row starts with -1
            for(int r = c; r < matrix.getRows(); r++){
                if(matrix.get(r, c).get() == -1){
                    rowNO = r;
                    break;
                }
            }

            if(rowNO != -1){
                // make the -1 row the leading 1
                swapRow(c,rowNO, matrix);
                print("Row" + Integer.toString(c+1) + " swaped with Row" + Integer.toString(rowNO+1));
                printMatrix(matrix);
                print("Multiply Row" + Integer.toString(c+1) + " With -1");
                multiplyRow(c,-1, matrix);
                printMatrix(matrix);
            }else{
                //play around with the other values to see if you can make 1

                for(int r = c; r < matrix.getRows(); r ++){
                    for(int r2 = r+1; r2 < matrix.getRows(); r2++){

                        int v1 = matrix.get(r, c).get();
                        int v2 = matrix.get(r2, c).get();

                        if(v1 < 0 && v2 < 0){
                            if(v1 < v2){
                                int val = (v1 + v2) + 1;
                                if((-1*(val)) % (-1 * v2) == 0 ){
                                    makeLeadingOne(r,r2,-1*(val+1), matrix, c);
                                    r = matrix.getRows();
                                    break;
                                }
                            }else{
                                int val = (v2 + v1) + 1;
                                if((-1*(val)) % (-1 * v1) == 0 ){
                                    makeLeadingOne(r2,r,-1*(val+1), matrix,c);
                                    r = matrix.getRows();
                                    break;
                                }
                            }
                        }else if(v1 < 0 && v2 > 0){
                            int val = (v2 + v1) -1;
                            if (val + 1 == -1){
                                makeLeadingOne(r2,r,1, matrix,c);
                                print("Multiply Row" + Integer.toString(c +1) + " With -1");
                                multiplyRow(c,-1, matrix);
                                printMatrix(matrix);
                                r = matrix.getRows();
                                break;
                            }else if(val % (-1 * v1) == 0){
                                makeLeadingOne(r2,r,(val+1), matrix,c);
                                r = matrix.getRows();
                                break;
                            }else if(val % v2 == 0){
                                makeLeadingOne(r2,r,(val+1), matrix,c);
                                print("Multiply Row" + Integer.toString(c+1) + " With -1");
                                multiplyRow(c,-1, matrix);
                                printMatrix(matrix);
                                r = matrix.getRows();
                                break;
                            }
                        }else if(v2<0 && v1 > 0){
                            int val = (v1 + v2) -1;
                            if (val + 1 == -1){
                                makeLeadingOne(r,r2,1, matrix,c);
                                print("Multiply Row" + Integer.toString(c+1) + " With -1");
                                multiplyRow(c,-1, matrix);
                                printMatrix(matrix);
                                r = matrix.getRows();
                                break;
                            }else if(val % (-1 * v2) == 0){
                                r = matrix.getRows();
                                break;
                            }else if(val % v1 == 0){
                                makeLeadingOne(r,r2,val+1, matrix,c);
                                print("Multiply Row" + Integer.toString(c+1) + " With -1");
                                multiplyRow(c,-1, matrix);
                                printMatrix(matrix);
                                r = matrix.getRows();
                                break;
                            }
                        }else if (v1 > 0 && v2 > 0){
                            if(v1 > v2){
                                int val = v1 - v2 -1;
                                if(val % v2 == 0){
                                    makeLeadingOne(r,r2,-1*(val+1), matrix,c);
                                    r = matrix.getRows();
                                    break;
                                }
                            }else{
                                int val = v2 - v1 -1;
                                if(val % v1 == 0){
                                    makeLeadingOne(r2,r,-1*(val+1), matrix,c);
                                    r = matrix.getRows();
                                    break;
                                }
                            }
                            boolean devide = true;
                            for(int i = 0; i < matrix.getColoums(); i ++){
                                if (matrix.get(r, i).mod(v1) != 0){
                                    devide = false;
                                }
                            }
                            if(devide){
                                devideRow(r, v1,1, matrix);
                                // for(int i = 0; i < matrix.getColoums(); i ++){
                                //     matrix.set(r, i, matrix.get(r, i) / v1 );
                                // }
                                print("Devide Row" + Integer.toString(r) + " by " + Integer.toString(v1));
                                printMatrix(matrix);
                                r = matrix.getRows();
                                break;
                            }
                        }
                    }
                    if(r + 1 == matrix.getRows() ){
                        print("Devide Row" + Integer.toString(r+1)+ " by " + matrix.get(r, c).str());
                        devideRow(r, matrix.get(r, c).getNumerator(),matrix.get(r, c).getDenominator(),  matrix);
                        printMatrix(matrix);
                        swapRow(r, c, matrix);
                        print("Row" + Integer.toString(r+1) + " swaped with Row" + Integer.toString(c+1));
                        printMatrix(matrix);
                    }
                }
            }
        }
        for(int i = c + 1; i < matrix.getRows(); i ++){
            print("Row" + Integer.toString(i+1) + " = Row"+Integer.toString(i+1)+" + "+(new Fraction(matrix.get(i, c).getNumerator() * -1, matrix.get(i, c).getDenominator() ).str())+ "x Row" + Integer.toString(c+1));
            addRows(i, c, -1 * matrix.get(i, c).get(), matrix);
            printMatrix(matrix);
        }
    }

    public static Matrix reduceEchelon(Matrix matrix){
        for(int i = matrix.getColoums()-1; i > -1; i--){
            if(i > matrix.getRows()-1){
                i = matrix.getRows()-1;
            }
            for(int j = i-1; j > -1; j--){
                print("Row" + Integer.toString(j+1) + " = Row" + Integer.toString(j+1) + " + " + (new Fraction(matrix.get(j, i).getNumerator() * -1, matrix.get(j, i).getDenominator() ).str()) + "x Row"+ Integer.toString(i+1));
                addRows(j, i, new Fraction(matrix.get(j, i).getNumerator() * -1, matrix.get(j, i).getDenominator()), matrix);
                printMatrix(matrix);
            }
        }

        return matrix;
    }

    public static void makeLeadingOne(int r, int r2, int val, Matrix matrix, int c){

        addRows(r,r2,val, matrix);
        print("Row" + Integer.toString(r+1) + " = Row"+Integer.toString(r+1)+" + "+Integer.toString(-1 *(val+1)) + "x Row" + Integer.toString(r2+1));
        printMatrix(matrix);
        if(c != r){
            print("Row" + Integer.toString(c+1) + " swaped with Row" + Integer.toString(r+1));
            swapRow(c, r, matrix);
            printMatrix(matrix);
        }
        
    }

    public static void addRows(int r,int r2,int val,Matrix matrix){
        Fraction[] row = matrix.getRow(r);
        Fraction[] row2 = matrix.getRow(r2);
        for(int i = 0; i < row.length; i++){
            Fraction temp = new Fraction(row2[i].getNumerator(),row2[i].getDenominator());
            temp.multiply(val);
            row[i].add(temp);
        }
        matrix.setRow(r, row);
    }

    public static void addRows(int r,int r2,Fraction val,Matrix matrix){
        Fraction[] row = matrix.getRow(r);
        Fraction[] row2 = matrix.getRow(r2);
        for(int i = 0; i < row.length; i++){
            Fraction temp = new Fraction(row2[i].getNumerator(),row2[i].getDenominator());
            temp.multiply(val);
            row[i].add(temp);
        }
        matrix.setRow(r, row);
    }

    //multiply a row with a value
    public static void multiplyRow(int r, int val, Matrix matrix){
        Fraction[] row = matrix.getRow(r);
        for(int i = 0; i < row.length; i++){
            row[i].multiply(val);;
        }
        matrix.setRow(r, row);
    }

    //devide a row with a value
    public static void devideRow(int r, int val,int d, Matrix matrix){
        Fraction[] row = matrix.getRow(r);
        for(int i = 0; i < row.length; i++){
            row[i].dvide(new Fraction(val, d));
        }
        matrix.setRow(r, row);
    }

    //method that swaps row1 with row2
    public static void swapRow(int r1, int r2, Matrix matrix){
        Fraction[] row1 = matrix.getRow(r1);
        matrix.setRow(r1, matrix.getRow(r2));
        matrix.setRow(r2, row1);
    }

    //method to populate a matrix
    public static void populateMatrix(Matrix matrix){
        for(int r = 0; r < matrix.getRows(); r++){
            for(int c = 0; c < matrix.getColoums(); c ++){
                print("please enter the " + Integer.toString(r+1) +" "+ Integer.toString(c+1)+" value: (Row)(Coloum)");
                Fraction value = new Fraction(getIntValue());
                matrix.set(r, c, value);
            }
        }
    }

    //Method to calculate the inverse of a matrix
    public static void inverseMatrix(){
        print("Please enter the size of the square matrix:");
        int size = getIntValue();

        Matrix matrix = new Matrix(size, size );
        Matrix calcMatrix = new Matrix(size, size * 2);
        populateMatrix(matrix);
        populateInverse(matrix, calcMatrix);
        echelon(calcMatrix);
        reduceEchelon(calcMatrix);
        printInverseMatrix(calcMatrix);
        inverse(matrix, calcMatrix);
        print("inverse: ");
        printMatrix(matrix);

    }

    public static void inverse(Matrix matrix, Matrix calcMatrix){
        for(int i = 0; i < matrix.getRows(); i ++){
            for(int j = 0; j < matrix.getColoums(); j ++){
                matrix.set(i, j, calcMatrix.get(i, j+3));
            }
        }
    }

    //populate the matrix which will be used for the inverse
    public static void populateInverse(Matrix matrix, Matrix calcMatrix){
        int size = matrix.getColoums();
        for(int i = 0; i < calcMatrix.getRows(); i ++){
            for(int j = 0; j < calcMatrix.getColoums(); j ++){
                if(j < size){
                    calcMatrix.set(i, j, matrix.get(i, j));
                }else{
                    if(j-i == size){
                        calcMatrix.set(i, j, new Fraction(1));
                    }else{
                        calcMatrix.set(i, j, new Fraction (0));
                    }
                }
            }
        }
    }

    // get the number of rows from a user
    public static int getRow(){
        print("Please enter the number of Rows");
        return getIntValue();
    }
    // get the numbers of coloums form the user
    public static int getColoums(){
        print("Please enter the number of Coloums:");
        return getIntValue();
    }
    // method that returns an int input form the user
    public static int getIntValue(){
        boolean done = false;
        int value = 0;
        while(! done){
            try{
                value = Integer.parseInt(input());
                done = true;
            }catch(Exception e){
                print("something whent wrong, please enter a number");
            }
        }
        return value;
    }

    // function to get users input
    public static String input(){
        String s =scanner.nextLine();
        print("");
        return s;
    }

    // meathod to print a message to the user
    public static void print(String string){
        System.out.println(string);
        System.out.println("");
    }

    // print a matrix
    public static void printMatrix(Matrix matrix){
        for(int i = 0; i < matrix.getRows(); i ++){
            String row = "";
            for(int j = 0; j < matrix.getColoums(); j ++){
                String stringVal = matrix.get(i, j).str();
                if(stringVal.length() == 1){
                    row += "   " +  matrix.get(i, j).str() + "   ";
                }else{
                    row += "  " +  matrix.get(i, j).str() + "  ";
                }
            }
            print(row);
        }
        
    }

    //special method to print the inverse ver of a matrix.
    public static void printInverseMatrix(Matrix matrix){
        for(int i = 0; i < matrix.getRows(); i ++){
            String row = "";
            for(int j = 0; j < matrix.getColoums(); j ++){
                String stringVal = matrix.get(i, j).str();
                if(stringVal.length() == 3){
                    row += "   " +  matrix.get(i, j).str() + "   ";
                }else{
                    row += "  " +  matrix.get(i, j).str() + "   ";
                }
                if(j+1 == matrix.getColoums()/2){
                    row += " | ";
                }
            }
            print(row);
        }
    }
}

class Matrix{
    private int rows;
    private int coloums;
    private Fraction[][] matrix ;


    public Matrix(int rows, int coloums){
        this.rows = rows;
        this.coloums = coloums;
        matrix = new Fraction[rows][coloums];
    }

    public Fraction get(int r, int c){
        return matrix[r][c];
    }
    public void set(int r, int c, Fraction v){
        matrix[r][c] = v;
    }

    public int getRows(){
        return rows;
    }
    public int getColoums(){
        return coloums;
    }
    public Fraction[] getRow(int r){
        return matrix[r];
    }
    public void setRow(int r, Fraction[] row){
        matrix[r] = row;
    }
}

class Fraction{
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        simplyfy();
    }

    public Fraction(int numerator){
        this.numerator = numerator;
        denominator = 1;
    }

    public String str(){
        if(denominator == 1){
            return Integer.toString(numerator);
        }else{
            return Integer.toString(numerator)+"/"+Integer.toString(denominator);
        }
    }

    public int get(){
        return numerator / denominator;
    }

    public int getNumerator(){
        return numerator;
    }

    public int getDenominator(){
        return denominator;
    }

    public void add(int number){
        number = number * denominator;
        numerator += number;
        simplyfy();
    }

    public void add(Fraction f){
        int fnum = f.getNumerator() * denominator;
        denominator = f.getDenominator() * denominator;
        numerator = numerator * f.getDenominator();
        
        numerator = numerator + fnum;
        simplyfy();
    }

    public void multiply(int number){
        numerator = number * numerator;
    }

    public void multiply(Fraction f){
        numerator = numerator * f.getNumerator();
        denominator = denominator * f.getDenominator();
    }

    public void dvide(int n){
        denominator = denominator * n;
        simplyfy();
    }

    public void dvide(Fraction f){
        denominator *= f.getNumerator();
        numerator *= f.getDenominator();
        simplyfy();
    }

    public int mod(int v){
        if(denominator == 1){
            return numerator % v;
        }else{
            return (numerator % v) / denominator;
        }
    }

    public int mod(Fraction f){
        if(denominator == 1 && f.getDenominator() == 1){
            return numerator % f.getNumerator();
        }else{
            return (numerator * f.getDenominator()) % (denominator * f.getNumerator());
        }
    }

    private void simplyfy(){
        
        if(numerator == 0){
            denominator = 1;
        }else if(numerator == denominator){
            numerator = 1;
            denominator = 1;
        }

        if(numerator < 0 && denominator < 0){
            numerator *= -1;
            denominator *= -1;
        }

        if(numerator > 0 && denominator < 0){
            numerator *= -1;
            denominator *= -1;
        }

        List<Integer> factors = new ArrayList<Integer>();
        for(int i = 2; i <= denominator; i++){
            if(denominator % i == 0){
                factors.add(i);
            }
        }
        int biggest = 0;
        for(int i : factors){
            if(numerator % i == 0){
                biggest = i;
            }
        }
        if(biggest == 0){
            return;
        }else{
            numerator = numerator/biggest;
            denominator = denominator/biggest;
            simplyfy();
        }
    }
}