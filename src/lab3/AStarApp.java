package lab3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.*;

/**
*
    * Простое приложение Swing для демонстрации алгоритма поиска пути А*.
    * Пользователю предоставляется карта, содержащая начальное и конечное местоположение.
    * Пользователь может нарисовать или очистить препятствия на карте, а затем нажать кнопку,
    * чтобы вычислить путь от начала до конца, используя алгоритм поиска пути А*.
    * Если путь найден, он отображается зеленым цветом.
    **/
public class AStarApp {

    /** Количество ячеек сетки по координате Х. **/
    private int width;

    /** Количество ячеек сетки по координате У. **/
    private int height;

    /** Местоположение, где начинается путь. **/
    private Location startLoc;

    /** Местоположение, где путь должен закончиться. **/
    private Location finishLoc;

    /**
     * Двумерный массив компонентов пользовательского интерфейса, которые обеспечивают отображение
     * и манипулирование ячейками на карте.
     ***/
    private JMapCell[][] mapCells;


    /**
     * Этот внутренний класс обрабатывает движения мыши в основной сетке ячеек карты, изменяя ячейки
     * на основе состояния кнопки мыши и начального редактирования, которое было выполнено.
     **/
    private class MapCellHandler implements MouseListener
    {
        /**
         * Это значение будет истинным, если была нажата кнопка мыши,
         * и в данный момент мы выполняем операцию модификации.
         **/
        private boolean modifying;

        /**
         * Это значение указывает, делаем ли мы ячейки проходимыми или непроходимыми.
         * Это зависит от исходного состояния ячейки, в которой была запущена операция.
         **/
        private boolean makePassable;

        /** Инициализация операции модификации. **/
        public void mousePressed(MouseEvent e)
        {
            modifying = true;

            JMapCell cell = (JMapCell) e.getSource();

            // Если текущая ячейка проходимая, тогда мы делаем их непроходимыми;
            // если непроходимая, то сделать проходимой.

            makePassable = !cell.isPassable();

            cell.setPassable(makePassable);
        }

        /** Конец операции модификации. **/
        public void mouseReleased(MouseEvent e)
        {
            modifying = false;
        }

        /**
         * Если мышь была нажата, это продолжает операцию модификации в новую ячейку.
         **/
        public void mouseEntered(MouseEvent e)
        {
            if (modifying)
            {
                JMapCell cell = (JMapCell) e.getSource();
                cell.setPassable(makePassable);
            }
        }

        /** не требуется для этого обработчика. **/
        public void mouseExited(MouseEvent e)
        {
            // игнорируем.
        }

        /** не требуется для этого обработчика **/
        public void mouseClicked(MouseEvent e)
        {
            // тоже.
        }
    }


    /**
     * Создаем новый экземпляр AStarApp с указанной шириной и высотой карты.
     **/
    public AStarApp(int w, int h) {
        if (w <= 0)
            throw new IllegalArgumentException("w must be > 0; got " + w);

        if (h <= 0)
            throw new IllegalArgumentException("h must be > 0; got " + h);

        width = w;
        height = h;

        startLoc = new Location(2, h / 2);
        finishLoc = new Location(w - 3, h / 2);
    }


    /**
     * Простой вспомогательный метод для настройки пользовательского интерфейса Swing.
     * Он вызывается из потока обработчика событий Swing для обеспечения безопасности потока.
     **/
    private void initGUI()
    {
        JFrame frame = new JFrame("Pathfinder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new BorderLayout());

          // Используем GridBagLayout.

        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 1;
        gbConstraints.insets.set(0, 0, 1, 1);

        JPanel mapPanel = new JPanel(gbLayout);
        mapPanel.setBackground(Color.GRAY);

        mapCells = new JMapCell[width][height];

        MapCellHandler cellHandler = new MapCellHandler();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mapCells[x][y] = new JMapCell();

                gbConstraints.gridx = x;
                gbConstraints.gridy = y;

                gbLayout.setConstraints(mapCells[x][y], gbConstraints);

                mapPanel.add(mapCells[x][y]);
                mapCells[x][y].addMouseListener(cellHandler);
            }
        }

        contentPane.add(mapPanel, BorderLayout.CENTER);

        JButton findPathButton = new JButton("Find Path");
        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { findAndShowPath(); }
        });

        contentPane.add(findPathButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        mapCells[startLoc.xCoord][startLoc.yCoord].setEndpoint(true);
        mapCells[finishLoc.xCoord][finishLoc.yCoord].setEndpoint(true);
    }


    /** Стартует приложение. Вызывается из метода main. **/
    private void start()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { initGUI(); }
        });
    }


    /**
     * Этот вспомогательный метод пытается вычислить путь, используя текущее состояние карты.
     * Реализация довольно медленная; новый объект Map2D создается
     * и инициализируется из текущего состояния приложения. Затем вызывается A*
     * и если путь найден, дисплей обновляется, чтобы показать найденный путь.
     **/
    private void findAndShowPath()
    {
        // Создание объекта Map2D содержащего текущее состояние ввода пользователя.

        Map2D map = new Map2D(width, height);
        map.setStart(startLoc);
        map.setFinish(finishLoc);

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                mapCells[x][y].setPath(false);
                if (mapCells[x][y].isPassable()) map.setCellValue(x, y, 0);
                else map.setCellValue(x, y, Integer.MAX_VALUE);
            }
        }

        // Попытка вычисления пути. Если можно вычислить, отметить все ячейки пути.

        Waypoint wp = AStarPathfinder.computePath(map);

        while (wp != null)
        {
            Location loc = wp.getLocation();
            mapCells[loc.xCoord][loc.yCoord].setPath(true);

            wp = wp.getPrevious();
        }
    }


    /**
     * Точка входа в приложение.
     **/
    public static void main(String[] args) {
        AStarApp app = new AStarApp(40, 30);
        app.start();
    }
}