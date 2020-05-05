package lab3;
/**
 * Класс показывает местоположение на 2D-карте.  Координаты - целые значения.
 **/
public class Location {
    /** X координата этого положения. **/
    public int xCoord;

    /** Y координата этого положения. **/
    public int yCoord;


    /** Создание нового местоположения с целочисленными значениями координат. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создание нового местоположения с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    @Override //Переопределение метода
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + xCoord;
        result = prime * result + yCoord;
        return result;
    }
    @Override //Переопределение метода
    public boolean equals(Object obj) { //Сравнение местоположений
        if (this == obj)
            return true;
        if (obj == null) //Проверка объекта на null
            return false;
        if (getClass() != obj.getClass()) //Проверка объектов на совпадение типов
            return false;
        Location other = (Location) obj; //Приведение объекта к объявленной переменной
        if (xCoord != other.xCoord) //если не равны - false
            return false;
        if (yCoord != other.yCoord)
            return false;
        return true;
    }
}
