import java.util.Random;

public class num_to_word {
  public static String findnum(int num) {
    int length = 0;
    int num11 = num;
    while (num11 != 0) {
      num11 /= 10;
      length++;
    } 
    int[] arr = new int[length];
    String s = "";
    String[] onesDigit = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
    String[] eleDigit = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
    String[] tenDigit = { " ", " ", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
    String[] hunDigit = { "thousand", "hundred", "lakh", "crore" };
    if (num > 999999999) {
      s = "Number should be less than 10000";
      return s;
    } 
    if (num <= 9) {
      s = s.concat(onesDigit[num]);
    } else {
      int num1 = num;
      int i = 0;
      while (num1 != 0) {
        arr[i++] = num1 % 10;
        num1 /= 10;
      } 
      if (length >= 9) {
        int j = arr[8] * 10 + arr[7] * 1;
        if (j <= 19) {
          if (arr[8] != 0) {
            s = s.concat(String.valueOf(eleDigit[arr[7]]) + " ");
            s = s.concat(String.valueOf(hunDigit[3]) + " ");
          } 
          if (arr[7] != 0) {
            s = s.concat(String.valueOf(onesDigit[arr[7]]) + " ");
            s = s.concat(String.valueOf(hunDigit[3]) + " ");
          } 
        } else {
          s = s.concat(String.valueOf(tenDigit[arr[8]]) + " ");
          if (arr[7] != 0)
            s = s.concat(String.valueOf(onesDigit[arr[7]]) + " "); 
          s = s.concat(String.valueOf(hunDigit[3]) + " ");
        } 
      } else if (length >= 8) {
        s = s.concat(String.valueOf(onesDigit[arr[7]]) + " ");
        s = s.concat(String.valueOf(hunDigit[3]) + " ");
      } 
      if (length >= 7) {
        int j = arr[6] * 10 + arr[5] * 1;
        if (j <= 19) {
          if (arr[6] != 0) {
            s = s.concat(String.valueOf(eleDigit[arr[5]]) + " ");
            s = s.concat(String.valueOf(hunDigit[2]) + " ");
          } 
          if (arr[5] != 0) {
            s = s.concat(String.valueOf(onesDigit[arr[5]]) + " ");
            s = s.concat(String.valueOf(hunDigit[2]) + " ");
          } 
        } else {
          s = s.concat(String.valueOf(tenDigit[arr[6]]) + " ");
          if (arr[5] != 0)
            s = s.concat(String.valueOf(onesDigit[arr[5]]) + " "); 
          s = s.concat(String.valueOf(hunDigit[2]) + " ");
        } 
      } else if (length >= 6) {
        s = s.concat(String.valueOf(onesDigit[arr[5]]) + " ");
        s = s.concat(String.valueOf(hunDigit[2]) + " ");
      } 
      if (length >= 5) {
        int j = arr[4] * 10 + arr[3] * 1;
        if (j <= 19) {
          if (arr[4] != 0) {
            s = s.concat(String.valueOf(eleDigit[arr[3]]) + " ");
            s = s.concat(String.valueOf(hunDigit[0]) + " ");
          } else if (arr[3] != 0) {
            s = s.concat(String.valueOf(onesDigit[arr[3]]) + " ");
            s = s.concat(String.valueOf(hunDigit[0]) + " ");
          } 
        } else {
          s = s.concat(String.valueOf(tenDigit[arr[4]]) + " ");
          if (arr[3] != 0)
            s = s.concat(String.valueOf(onesDigit[arr[3]]) + " "); 
          s = s.concat(String.valueOf(hunDigit[0]) + " ");
        } 
      } else if (length >= 4 && 
        arr[3] != 0) {
        s = s.concat(String.valueOf(onesDigit[arr[3]]) + " ");
        s = s.concat(String.valueOf(hunDigit[0]) + " ");
      } 
      if (length >= 3 && 
        arr[2] != 0) {
        s = s.concat(String.valueOf(onesDigit[arr[2]]) + " ");
        s = s.concat(String.valueOf(hunDigit[1]) + " ");
      } 
      int z = arr[0] * 1 + arr[1] * 10;
      if (z > 19) {
        if (length >= 2 && 
          arr[1] != 0) {
          s = s.concat(String.valueOf(tenDigit[arr[1]]) + " ");
          if (arr[0] != 0)
            s = s.concat(String.valueOf(onesDigit[arr[0]]) + " "); 
        } 
      } else if (arr[1] != 0) {
        s = s.concat(String.valueOf(eleDigit[arr[1] + arr[0] - 1]) + " ");
      } else if (arr[0] != 0) {
        s = s.concat(String.valueOf(onesDigit[arr[0]]) + " ");
      } 
    } 
    return s;
  }
  
  public static void main(String[] args) {
	    Random obj = new Random();
	    System.out.println("********Generating 30 random values between 0-999999999 :*****");
	    int num = 0;
	    int j = 2;
	    for (int i = 2; i <= 30; i++) {
	      num = obj.nextInt(j);
	      j *= 2;
	      String fnum = findnum(num);
	      System.out.println(String.valueOf(num) + " : " + fnum);
	} 
  }
}
