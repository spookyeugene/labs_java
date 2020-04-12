public class Primes {
  public static void main(String[] args) {
    for (int i = 2; i < 100; i++) //перебираем числа в диапазоне от 2 до 100
      if (isPrime(i)) System.out.print(i + " "); //вызов функции и вывод результатов
  }
  public static boolean isPrime(int n) { //функция, реализующая проверку чисел на простоту
    for (int m = 2; m < n; m++) // перебор чисел от 2 до n, делящихся на n без остатка
      if (n % m == 0)  return false; // условие проверки чисел, если какая-то переменная делится на аргумент без остатка
    return true; // если значение не делится на аргумент без остатка

  }
}
