package lab4;

import java.awt.geom.Rectangle2D;
import lab4.FractalGenerator;

public class BurningShip extends FractalGenerator {

  public static final int MAX_ITERATIONS = 2000;

  @Override
  public void getInitialRange(Rectangle2D.Double range){
    range.x = -2;
    range.y = -2.5;
    range.width = 4;
    range.height = 4;
  }
  /** Реализует итеративную функцию для фрактала BurningShip
   * (рассчитывает количество итераций для соответсвующей координаты
   */
  @Override
  public int numIterations(double x, double y) {
    int iteration = 0;
    double complex_real = 0;
    double complex_imaginary = 0;

    while ((iteration < MAX_ITERATIONS) && (complex_real * complex_real + complex_imaginary * complex_imaginary) < 4) {
      double complex_real1 = Math.abs(complex_real * complex_real - complex_imaginary * complex_imaginary + x);
      double complex_imaginary1 = Math.abs(2 * complex_real * complex_imaginary + y);
      complex_real = complex_real1;
      complex_imaginary = complex_imaginary1;
      iteration += 1;
    }
    if (iteration == MAX_ITERATIONS)
    {
      return -1;
    }
    return iteration;
  }
  public String toString(){
    return "Burning Ship";
  }
}
