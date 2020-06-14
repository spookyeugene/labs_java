package lab2;
// Класс трехмерной точки
import com.sun.source.util.SourcePositions;
import java.sql.SQLOutput;
public class Point3d {
  private double x, y, z;
  public Point3d(double x, double y, double z){
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Point3d(){
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }
  public double getX() {
    return this.x;
  }
  public double getY() {
    return this.y;
  }
  public double getZ() {
    return this.z;
  }

  public void setX(double coord) {
    this.x = coord;
  }
  public void setY(double coord) {
    this.y = coord;
  }
  public void setZ(double coord) {
    this.z = coord;
  }

  public static boolean isEqual(Point3d coord1, Point3d coord2) {
    double[] coord_1 = new double[]{coord1.getX(), coord1.getY(), coord1.getZ()};
    double[] coord_2 = new double[]{coord2.getX(), coord2.getY(), coord2.getZ()};
    if (coord_1[0] == coord_2[0] && coord_1[1] == coord_2[1] && coord_1[2] == coord_2[2]) {
      return true;
    } else {
      return false;
    }
  }
  public static boolean isEqual_3(Point3d coord1, Point3d coord2, Point3d coord3){
    double[] coord_list1 = new double[]{coord1.getX(), coord1.getY(), coord1.getZ()};
    double[] coord_list2 = new double[]{coord2.getX(), coord2.getY(), coord2.getZ()};
    double[] coord_list3 = new double[]{coord2.getX(), coord2.getY(), coord2.getZ()};
    if ((coord_list1[0] == coord_list2[0])  && (coord_list1[1] == coord_list2[1]) && (coord_list1[2] == coord_list2[2]) &&
        (coord_list2[0] == coord_list3[0])  && (coord_list2[1] == coord_list3[1]) && (coord_list2[2] == coord_list3[2])) {
      return true;
    } else
      return false;
  }
  public static double distanceTo(Point3d coord1, Point3d coord2) {
    if (Point3d.isEqual(coord1, coord2) != true) {
      double[] coord_1 = new double[]{coord1.getX(), coord1.getY(), coord1.getZ()};
      double[] coord_2 = new double[]{coord2.getX(), coord2.getY(), coord2.getZ()};

      double distance = Math.sqrt(
          Math.pow((coord_1[0] - coord_2[0]), 2) +
              Math.pow((coord_1[1] - coord_2[1]), 2) +
              Math.pow((coord_1[2] - coord_2[2]), 2)
      );
      return distance;
    } else {
      System.out.println("Точки одинаковы");
      return 0;
    }
  }
  public static double computeArea(Point3d coord1, Point3d coord2, Point3d coord3) { //Находит по формуле Герона
    if (Point3d.isEqual_3(coord1,coord2,coord3) == false) { //площадь трехмерного треугольника
      double a = Point3d.distanceTo(coord1, coord2);
      double b = Point3d.distanceTo(coord2, coord3);
      double c = Point3d.distanceTo(coord1, coord3);
      double p = (a + b + c) / 2; //где а, b, c - расстояние между точками
      double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
      return area;
    }
    else
      return 0;
  }
}
