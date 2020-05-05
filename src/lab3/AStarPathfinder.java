package lab3;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Этот класс содержит реализацию алгоритма поиска пути A*.
 * Алгоритм реализован как статический метод, поскольку алгоритм поиска пути
 * не должен поддерживать состояние между вызовами алгоритма.
 */
public class AStarPathfinder
{
    /**
     * Эта константа содержит максимальный предел отсечения для стоимости пути.
     * Если конкретная путевая точка превышает этот предел, то она отбрасывается.
     **/
    public static final float COST_LIMIT = 1e6f;

    
    /**
     * Попытки вычислить путь, который перемещается между начальным и конечным местоположением указанной карты.
     * Если путь может быть найден, возвращается путевая точка последнего шага пути;
     * эта путевая точка может быть использована для перехода назад к начальной точке.
     * Если путь не найден, возвращается null.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для алгоритма А*.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Установка начальной путевой точки для начала поиска по алгоритму А*.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;
        
        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Поиск "лучшей" (т.е. самой низкой по стоимости) путевой точки на данный момент.
            Waypoint best = state.getMinOpenWaypoint();
            
            // Если лучшее местоположение - конечная точка, конец!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }
            
            // Добавление/обновление всех соседних точек текущего лучшего местоположения.
            // Это равносильно выполнению всех "следующих шагов" из этого места.
            takeNextStep(best, state);
            
            // Наконец, переместить это местоположение из "открытого" списка в "закрытый".
            state.closeWaypoint(best.getLocation());
        }
        
        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод принимает путевую точку и генерирует все действительные
     * "следующие шаги" с этой точки. Новые путевые точки добавляются в коллекцию "открытых путевых точек"
     * переданного объекта состояния А*.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();
        
        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);
                
                // Если "следующее местоположение" вне карты, пропустить её.
                if (!map.contains(nextLoc))
                    continue;
                
                // Если "следующее местоположение" - теукщее местоположение, пропустить её.
                if (nextLoc == loc)
                    continue;
                
                // Если это местоположение уже в списке "закрытых",
                // продолжить со следующим местоположением.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Сделать путевую точку для "следующего местоположения".
                
                Waypoint nextWP = new Waypoint(nextLoc, currWP);
                
                // Мы используем оценку стоимости, чтобы вычислить фактическую стоимость
                // из предыдущей ячейки. Затем, мы добавляем в стоимость из ячейки карты,
                // в которую входим чтобы включить барьеры и т.д.

                float prevCost = currWP.getPreviousCost() +
                    estimateTravelCost(currWP.getLocation(),
                                       nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);
                
                // Пропускаем "следующее местоположение" если слишком дорого.
                if (prevCost >= COST_LIMIT)
                    continue;
                
                nextWP.setCosts(prevCost,
                    estimateTravelCost(nextLoc, map.getFinish()));

                // Добавляем путевую точку в набор открытых путевых точек.
                // Если для этого местоположения уже есть путевая точка,
                // новая путевая точка заменяет старую, если она дешевле, чем старая.
                state.addOpenWaypoint(nextWP);
            }
        }
    }
    
    /**
     * Оценивает стоимость поездки между двумя указанными местами.
     * Фактическая стоимость - это прямое расстояние между двумя точками.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
