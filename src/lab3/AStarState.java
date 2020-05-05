package lab3;

import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import javax.lang.model.type.NullType;

/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A* для вычисления пути по карте.
 * Это состояние включает коллекцию "открытых путевых точек" и
 * коллекцию "закрытых путевых точек. Кроме того, этот класс представляет
 * основные операции, необходимые алгоритму поиска пути A* для его обработки.
 **/
public class AStarState {
    //Это ссылка на карту, по которой движется алгоритм A *
    //Вершины будут храниться в хэш-карте, где местоположение вершин является ключом, а сами вершины являются значениями
    private Map2D map;
    public HashMap<Location, Waypoint> openPoints = new HashMap<>(); // хэш-карта для открытых вершин
    public HashMap<Location, Waypoint> closedPoints = new HashMap<>(); // хэш-карта для закрытых вершин


    //Инициализирует новый объект состояния для алгоритма поиска пути A *
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    // Возвращает карту, по которой перемещается указатель пути A *
    public Map2D getMap() {
        return map;
    }

    // Этот метод проверяет все вершины в наборе открытых вершин,
    // и после этого возвращает ссылку на вершину с наименьшей общей стоимостью.
    // Если в открытом наборе нет вершин, функция возвращает NULL.
    public Waypoint getMinOpenWaypoint() {
        Waypoint res;
        if (openPoints.isEmpty()) { //если в наборе нет вершин, возвращаем NULL
            res = null;
        } else { // Если вершины есть
            ArrayList<Location> keys = new ArrayList(openPoints.keySet()); //ключи и значения openPoints для удобного доступа к ним
            ArrayList<Waypoint> values = new ArrayList(openPoints.values()); // Значения открытых вершин
            Waypoint min = values.get(0); //минимальным принимаем первый элемент значений
            for (int i = 1; i <= values.size() - 1; i++) {
                if (values.get(i).getTotalCost() < min.getTotalCost()) { // Если total cost текущей вершины меньше минимального, то текущий становится минимальным
                    min = values.get(i);
                }
            }
            res = min;
        }
        return res;
    }

    // Данный метод должен добавлять указанную вершину только в том случае, если существующая вершина хуже новой.
    // Если в наборе «открытых вершин» в настоящее время нет вершины для данного местоположения, то необходимо просто добавить новую вершину
    // Если в наборе «открытых вершин» уже есть вершина для этой локации, то необходимо добавить новую вершину только в том случае, если путь через новую
    // вершину короче, чем путь через текущую вершину
    public boolean addOpenWaypoint(Waypoint newWP) {
        boolean res = false;
        Location location = newWP.getLocation(); // Получаем локацию новой вершины
        ArrayList<Location> keys = new ArrayList(openPoints.keySet()); // Ключи открытых вершин
        ArrayList<Waypoint> values = new ArrayList(openPoints.values()); // Значения открытых вершин
        if (keys.contains(location)) { // Если локация новой вершины содержится в ключах открытых вершин
            int index = keys.indexOf(location); // Находим индекс ключа
            Waypoint oldWP = values.get(index); // По индексу keys находим values старой вершины
            if (newWP.getPreviousCost() < oldWP.getPreviousCost()) { // Сравниваем стоимость у старой и новой вершин
                openPoints.put(location, newWP); // Если у новой меньше, меняем старую на новую
                res = true;
            }
        } else { // Если локации в ключах нет
            openPoints.put(location, newWP); // Добавляем новую вершину
            res = true;
        }
        return res;
    }

    //Возвращает текущее количество точек в наборе открытых вершин
    public int numOpenWaypoints() {
        int res = openPoints.size();
        return res;
    }


    //Этот метод перемещает путевую точку в указанном месте из открытого списка в закрытый список
    public void closeWaypoint(Location loc) {
        closedPoints.put(loc, openPoints.get(loc)); // Добавляем в закрытые вершины
        openPoints.remove(loc); // Удаляем из открытых вершин
    }

    //Метод возвращает значение true, если указанное местоположение встречается в наборе закрытых вершин, и false в противном
    //случае
    public boolean isLocationClosed(Location loc) {
        boolean res = false;
        if (closedPoints.containsKey(loc)) { //если локация содержится в закрытых
            res = true;
        }
        return res;
    }
}