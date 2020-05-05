package lab3;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * Этот класс является пользовательским компонентом Swing для представления одной ячейки на
 * 2D карте.  Ячейка имеет несколько различных типов состояний, но самое основное состояние
 * состоитс в том, является ли ячейка проходимой или нет.
 */
public class JMapCell extends JComponent
{
    private static final Dimension CELL_SIZE = new Dimension(12, 12);
    
    /** True означает, что ячейка является конечной точкой, начала или окончания. **/
    boolean endpoint = false;
    
    
    /** True показывает, что ячейка проходима; false - нет. **/
    boolean passable = true;
    
    /**
     * True показывает, что эта ячейка является частью пути между началом и концом.
     **/
    boolean path = false;
    
    /**
     * Построить новую ячейку карты с заданной "проходимостью".
     * Ввод True означает, что ячейка проходима.
     **/
    public JMapCell(boolean pass)
    {
        // Установите предпочтительный размер ячейки, чтобы управлять начальным размером окна.
        setPreferredSize(CELL_SIZE);
        
        setPassable(pass);
    }
    
    /** Построить новую ячейку карты, которая будет проходима по умолчанию. **/
    public JMapCell()
    {
        // Вызвать другой конструктор,указав true для "проходимых".
        this(true);
    }
    
    /** Отмечает эту ячейку как начальную или конечную. **/
    public void setEndpoint(boolean end)
    {
        endpoint = end;
        updateAppearance();
    }
    
    /**
     * Устанавливает эту ячейку как проходимую или не проходимую. Ввод true помечает
     * ячейку как проходимую; ввод false помечает ячейку как непроходимую.
     **/
    public void setPassable(boolean pass)
    {
        passable = pass;
        updateAppearance();
    }
    
    /** Возвращает true, если эта ячейка проходима, false - в остальных случаях. **/
    public boolean isPassable()
    {
        return passable;
    }
    
    /** Переключает текущее "проходимое" состояние ячейки карты. **/
    public void togglePassable()
    {
        setPassable(!isPassable());
    }
    
    /** Помечает эту ячейку как часть пути, пройденного алгоритмом A*. **/
    public void setPath(boolean path)
    {
        this.path = path;
        updateAppearance();
    }
    
    /**
     * Этот вспомогательный метод обновляет цвета фона, чтобы соответствовать текущему
     * внутреннему состоянию ячейки.
     **/
    private void updateAppearance()
    {
        if (passable)
        {
            // Проходимая ячейка. Показывает свое состояние с помощью границы.
            setBackground(Color.WHITE);

            if (endpoint)
                setBackground(Color.CYAN);
            else if (path)
                setBackground(Color.GREEN);
        }
        else
        {
            // Непроходимая ячейка. Закрашиваем все красным.
            setBackground(Color.RED);
        }
    }

    /**
     * Реализация метода рисования для закраски цвета фона в ячейке карты.
     **/
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
