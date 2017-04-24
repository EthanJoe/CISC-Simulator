package Logic;

/**
 * Created by Lulu and Han on 4/14/17.
 */
public class FloatingRepresentation  {
    private String s;
    private String exponent;
    private String mantissa;

    public FloatingRepresentation(String number) {
        this.s = number.substring(0, 1);
        this.exponent = number.substring(1,8);
        this.mantissa = number.substring(8);
    }

    public FloatingRepresentation(Float number) {
        Integer binaryInt = Integer.valueOf(Float.floatToIntBits(number.floatValue()));
        String binary = Integer.toBinaryString(binaryInt.intValue());
        binary = binStrParam(binary, 32);

        this.s = binary.charAt(0) + "";
        this.exponent = expEvaluation(binary.substring(1, 9));
        //need verification(9, 17)???????????????
        this.mantissa = binary.substring(9,17);
    }

    public String floatingToString() {
        return this.s + this.exponent + this.mantissa;
    }

    public Float calDecimalNum() {
        Integer sign = (s.charAt(0) == '0' ? 1 : -1);
        Double valExponent = Math.pow(2d, calExponent().doubleValue());

        return sign * (calMantissa() * valExponent.floatValue());
    }

    private Integer calExponent() {
        Integer sign = (s.charAt(0) == '0' ? 1 : -1);
        Integer number = Integer.parseInt(exponent.substring(1), 2);

        if (sign > 0) {
            number ++;
        }

        return sign * number;
    }

    private Float calMantissa() {
        Float[] mantissaVector = {1/2f, 1/4f, 1/8f, 1/16f, 1/32f, 1/64f, 1/128f, 1/256f};
        Float mantissaFraction = 0f;
        for(int i = 0; i < 8; i++){
            char bit = mantissa.charAt(i);
            if(bit == '1'){
                mantissaFraction += mantissaVector[i];
            }
        }
        return 1 + mantissaFraction;
    }



    /*
     * Utility methods : Binary
     */
    private String binStrParam(String binary, int length) {
        if (binary.length() == length) {
            return binary;
        } else if (binary.length() > length) {
            return "";
        } else  {
            int zeros = length - binary.length();
            String zeroStr = "";

            for (int i = 0; i < zeros; i++) {
                zeroStr = zeroStr + 0;
            }

            return zeroStr + binary;
        }
    }

    /*
     * Utility methods : exponent evaluation: positive/negative
     */
    private String expEvaluation(String exp) {
        String sign;
        String expVal;

        Integer number = Integer.parseInt(exp, 2);
        number = number - 127;

        if (number <= 64 && number >= -63) {
            if (number < 0) {
                sign = "1";
                number = number * (-1);

            } else if (number > 0) {
                sign = "0";
                number = number - 1;
            } else {
                sign = "1";
            }

            expVal = Integer.toBinaryString(number);

            return sign + binStrParam(expVal, 6);
        } else {
            return null;
        }
    }

    /*
     * Setters and Getters
     */
    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    public String getMantissa() {
        return mantissa;
    }

    public void setMantissa(String mantissa) {
        this.mantissa = mantissa;
    }


    public static void main(String[] args) {

        String s = "0";
        String exp = "1000000";
        String mantissa = "00000000";
        FloatingRepresentation f = new FloatingRepresentation(s+exp+mantissa);
        System.out.println(f.calDecimalNum());

        FloatingRepresentation f2 = new FloatingRepresentation(2f);
        System.out.println(f2.toString());

    }
}



