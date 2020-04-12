import java.util.Scanner;
public class Palindrome {
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in); //считывание вводимых с клавиатуры значений
    String input_string = in.next();
    System.out.println(isPalindrome(input_string));//вызов функции и вывод результатов
  }
  public static boolean isPalindrome(String s){ //функция, показывающая является ли строка палиндромом
    if (s.equals(reverseString(s))){ //проверка, равна ли введенная строка отображенной
      return true;
    } else {
      return false;
    }
  }
  public static String reverseString(String any_str) { //функция, отображающая введенные
    String default_str = "";                           //данные в обратном порядке
    for (int litter_number = any_str.length(); litter_number>0; litter_number--){
      default_str+=any_str.charAt(litter_number-1);
    }
    return default_str;
  }
}
