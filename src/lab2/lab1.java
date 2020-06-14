package lab2;

public class lab1 {

  public static void main(String[] args) {
    Point3d point0 = new Point3d(0, 0, 0);
    Point3d point1 = new Point3d(1, 1, 1);

    boolean equal = Point3d.isEqual(point0, point1);
    double distance = Point3d.distanceTo(point0, point1);
    System.out.println("одинаковы ли точки: "+equal);
    System.out.println("расстояние между точками: "+distance);

    Point3d first = new Point3d(5, 5, 2);
    Point3d second = new Point3d(8, 9, 2);
    Point3d third = new Point3d(3, 4, 9);
    double area = Point3d.computeArea(first, second, third);
    System.out.println("Площадь: "+area);
    }
}
