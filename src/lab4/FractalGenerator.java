package lab4;
import java.awt.geom.Rectangle2D;
  /**
   * Этот класс предоставляет общий интерфейс и операции
   * для генераторов фракталов, которые можно просматривать
   * в Fractal Explorer.
   */
  public abstract class FractalGenerator {
    /**
     * Эта статическая вспомогательная функция принимает целочисленную
     * координату и преобразует ее в значение типа double,
     * соответствующее определенному диапазону.
     * Он используется для преобразования координат пикселей
     * в значения типа double для вычисления фракталов и т.д.
     *
     * @param rangeMin минимальное значение диапазона с плавающей точкой
     * @param rangeMax максимальное значение диапазона с плавающей точкой
     *
     * @param size размер измерения, из которого берется координата пикселя
     *        Например, это может быть ширина или высота изображения
     *
     * @param coord координата для вычисления значения типа double
     *        Координата должна быть в диапазоне [0, size].
     */
    public static double getCoord(double rangeMin, double rangeMax,
        int size, int coord) {
      assert size > 0;
      assert coord >= 0 && coord < size;

      double range = rangeMax - rangeMin;
      return rangeMin + (range * (double) coord / (double) size);
    }
    /**
     * Устанавливает указанный прямоугольник, чтобы содержать
     * начальный диапазон, подходящий для генерируемого фрактала.
     */
    public abstract void getInitialRange(Rectangle2D.Double range);
    /**
     * Обновляет текущий диапазон с центром в указанных координатах,
     * а также для увеличения или уменьшения с помощью
     * указанного коэффициента масштабирования.
     */
    public void recenterAndZoomRange(Rectangle2D.Double range,
        double centerX, double centerY, double scale) {
      double newWidth = range.width * scale;
      double newHeight = range.height * scale;
      range.x = centerX - newWidth / 2;
      range.y = centerY - newHeight / 2;
      range.width = newWidth;
      range.height = newHeight;
    }
    /**
     * Учитывая координату х+iу в комплексной плоскости,
     * вычисляет и возвращает количество итераций,
     * прежде чем фрактальная функция покинет
     * ограничивающую область для этой точки.
     * Точка, которая не проходит до достижения
     * предела итерации, указывается с результатом -1.
     */
    public abstract int numIterations(double x, double y);
  }
