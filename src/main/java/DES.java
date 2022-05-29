import java.util.HashMap;
import java.util.Scanner;

public class DES {
    final static Scanner scanner = new Scanner(System.in); // Do not change this line

    static String hexToBinary(String hex) {
        String binary = "";
        hex = hex.toUpperCase();
        HashMap<Character, String> hashMap = new HashMap<Character, String>();

        // storing the key value pairs
        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;

        // loop to iterate through the length
        // of the Hexadecimal String
        for (i = 0; i < hex.length(); i++) {
            // extracting each character
            ch = hex.charAt(i);

            // checking if the character is
            // present in the keys
            if (hashMap.containsKey(ch))
                binary += hashMap.get(ch);
            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }
        return binary;
    }

    static String binaryToHex(String binary) {
        String result = "";

        for (int i = 0; i < binary.length(); i += 4) {
            int sum = 0;
            String hexChar = "";

            String substring = binary.substring(i, i + 4);

            if (substring.charAt(0) == '1') {
                sum += 8;
            }
            if (substring.charAt(1) == '1') {
                sum += 4;
            }
            if (substring.charAt(2) == '1') {
                sum += 2;
            }
            if (substring.charAt(3) == '1') {
                sum += 1;
            }

            if (sum >= 10) {
                if (sum == 10) {
                    hexChar = "A";
                }
                if (sum == 11) {
                    hexChar = "B";
                }
                if (sum == 12) {
                    hexChar = "C";
                }
                if (sum == 13) {
                    hexChar = "D";
                }
                if (sum == 14) {
                    hexChar = "E";
                }
                if (sum == 15) {
                    hexChar = "F";
                }
            } else {
                hexChar = String.valueOf(sum);
            }
            result += hexChar;
        }
        return result;
    }

    static String xor(String input1, String input2) {
        String result = "";

        for (int i = 0; i < input1.length(); i++) {
            result += (input1.charAt(i) == input2.charAt(i)) ? "0" : "1";
        }

        return result;
    }

    //Keys
    static String[] keySchedules(String hexKey) {
        String[] keys = new String[17];
        int[] keyShift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

        String c = pc1(hexKey, true);
        String d = pc1(hexKey, false);

        c = hexToBinary(c);
        d = hexToBinary(d);

        keys[0] = c + d;

        for (int i = 0; i < keyShift.length; i++) {
            if (keyShift[i] == 1) {
                c = c.substring(1) + c.charAt(0);
                d = d.substring(1) + d.charAt(0);
            } else {
                c = c.substring(2) + c.charAt(0) + c.charAt(1);
                d = d.substring(2) + d.charAt(0) + d.charAt(1);
            }
            keys[i + 1] = pc2(c, d);
        }

        return keys;
    }

    static String pc1(String hexKey, boolean isC0) {
        String keyBinary = hexToBinary(hexKey);
        String resultBinary = "";

        int[] c0 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 40, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36};
        int[] d0 = {63, 55, 47, 39, 31, 33, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};

        if (isC0) {
            for (int position : c0) {
                resultBinary += keyBinary.charAt(position - 1);
            }
        } else {
            for (int position : d0) {
                resultBinary += keyBinary.charAt(position - 1);
            }
        }

        return binaryToHex(resultBinary);

    }

    static String pc2(String c, String d) {
        int[] kn = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
        String sumOfCD = c + d;
        String result = "";

        for (int position : kn) {

            result += sumOfCD.charAt(position - 1);
        }
        return result;
    }

    //Encryption

    static String ip(String plaintext, boolean isLeft) {

        String plaintextInBinary = hexToBinary(plaintext);
        String result = "";

        int[] ipLeft = {58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8};

        int[] ipRight = {57, 49, 41, 33, 25, 17, 9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7};

        if (isLeft) {
            for (int position : ipLeft) {
                result += plaintextInBinary.charAt(position - 1);
            }
        } else {
            for (int position : ipRight) {
                result += plaintextInBinary.charAt(position - 1);
            }
        }

        return result;
    }

    static String fOutput(String r, String key) {
        //f = P(S(Kn ⊕ E(Rn)))

        //E
        int[][] eFunction = {
                {32, 1, 2, 3, 4, 5},
                {4, 5, 6, 7, 8, 9},
                {8, 9, 10, 11, 12, 13},
                {12, 13, 14, 15, 16, 17},
                {16, 17, 18, 19, 20, 21},
                {20, 21, 22, 23, 24, 25},
                {24, 25, 26, 27, 28, 29},
                {28, 29, 30, 31, 32, 1}
        };
        String[] resultOfE = new String[8];
        String resultStringOfE = "";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                String value = String.valueOf(r.charAt(eFunction[i][j] - 1));
                resultOfE[i] += value;
                resultStringOfE += value;
            }
        }

        //xor with key: Kn ⊕ E(Rn)
        String xorResult = xor(resultStringOfE, key);
        //split into 8 boxes
        String[] xorResults = new String[8];
        for (int i = 0; i < 8; i++) {
            xorResults[i] = xorResult.substring(0, 6);

            if (xorResult.length() > 6) {
                xorResult = xorResult.substring(6);
            }
        }

        //S-Box result, S(Kn ⊕ E(Rn))
        String sBoxResult = "";
        for (int i = 0; i < 8; i++) {
            int rowValue = Integer.parseInt((xorResults[i].substring(0, 1))) * 2;
            rowValue += Integer.parseInt(xorResults[i].substring(5, 6));
//            System.out.println("rowValue: " + rowValue);

            int colValue = Integer.parseInt((xorResults[i].substring(1, 2))) * 8;
            colValue += Integer.parseInt((xorResults[i].substring(2, 3))) * 4;
            colValue += Integer.parseInt((xorResults[i].substring(3, 4))) * 2;
            colValue += Integer.parseInt((xorResults[i].substring(4, 5)));
//            System.out.println("colValue: "+colValue);

            sBoxResult += sBox(i + 1, rowValue, colValue);
        }

        //P function
        String pResult = pFunction(sBoxResult);


        return pResult;

    }

    static String sBox(int sequence, int row, int col) {
        int[][] s1 = {
                {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
        };

        int[][] s2 = {
                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        };

        int[][] s3 = {
                {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
        };

        int[][] s4 = {
                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        };

        int[][] s5 = {
                {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
        };

        int[][] s6 = {
                {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
        };

        int[][] s7 = {
                {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
        };

        int[][] s8 = {
                {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
        };

        String result = "";

        if (sequence == 1) {
            result = String.valueOf(s1[row][col]);
        }
        if (sequence == 2) {
            result = String.valueOf(s2[row][col]);
        }
        if (sequence == 3) {
            result = String.valueOf(s3[row][col]);
        }
        if (sequence == 4) {
            result = String.valueOf(s4[row][col]);
        }
        if (sequence == 5) {
            result = String.valueOf(s5[row][col]);
        }
        if (sequence == 6) {
            result = String.valueOf(s6[row][col]);
        }
        if (sequence == 7) {
            result = String.valueOf(s7[row][col]);
        }
        if (sequence == 8) {
            result = String.valueOf(s8[row][col]);
        }


        if (result.equals("10")) {
            result = "A";
        }
        if (result.equals("11")) {
            result = "B";
        }
        if (result.equals("12")) {
            result = "C";
        }
        if (result.equals("13")) {
            result = "D";
        }
        if (result.equals("14")) {
            result = "E";
        }
        if (result.equals("15")) {
            result = "F";
        }
        return hexToBinary(result);
    }

    static String pFunction(String inputText) {
        String result = "";
        int[] pTable = {16, 7, 20, 21,
                29, 12, 28, 17,
                1, 15, 23, 26,
                5, 18, 31, 10,
                2, 8, 24, 14,
                32, 27, 3, 9,
                19, 13, 30, 6,
                22, 11, 4, 25};

        for (int position : pTable) {
            result += inputText.charAt(position - 1);
        }
        return result;
    }

    static String finalPermutation(String inputText) {
        String result = "";
        int[] fpTable = {40, 8, 48, 16, 56, 24, 64, 32,
                39, 7, 47, 15, 55, 23, 63, 31,
                38, 6, 46, 14, 54, 22, 62, 30,
                37, 5, 45, 13, 53, 21, 61, 29,
                36, 4, 44, 12, 52, 20, 60, 28,
                35, 3, 43, 11, 51, 19, 59, 27,
                34, 2, 42, 10, 50, 18, 58, 26,
                33, 1, 41, 9, 49, 17, 57, 25};

        for (int value : fpTable) {
            result += inputText.charAt(value - 1);
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.print("Enter the plaintext (16-bit hex) (eg: 02468aceeca86420): ");
        String plaintext = scanner.nextLine();
//        String plaintext = "02468aceeca86420";

        System.out.print("Enter the key (16-bit hex) (eg: 0f1571c947d9e859): ");
        String key = scanner.nextLine();
//        String key = "0f1571c947d9e859";

        System.out.println();
        //Get the keys
        String[] keys = keySchedules(key);

        //Start doing with plaintext
        String l = ip(plaintext, true);
        String r = ip(plaintext, false);
        System.out.println("L0: " + binaryToHex(l) + ", R0: " + binaryToHex(r));


        for (int i = 1; i <= 16; i++) {
            String tempL = r;
            String tempR = xor(l, fOutput(r, keys[i]));

            l = tempL;
            r = tempR;

            System.out.println("L" + i + ": " + binaryToHex(l) + ", R" + i + ": " + binaryToHex(r));
        }

        String result = r + l;

        result = binaryToHex(finalPermutation(result));

        System.out.println("Ciphertext: " + (result));


        //Start decryption
        System.out.println();
        System.out.println("Decryption");
        System.out.println();


        l = ip(result, true);
        r = ip(result, false);

        for (int i = 16; i > 0; i--) {
            String tempL = r;
            String tempR = xor(l, fOutput(r, keys[i]));

            l = tempL;
            r = tempR;

            System.out.println("L" + i + ": " + binaryToHex(l) + ", R" + i + ": " + binaryToHex(r));
        }

        result = r + l;

        result = binaryToHex(finalPermutation(result));
        System.out.println("Plaintext: "+result);

    }

}
