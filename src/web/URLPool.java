package web;

import java.util.LinkedList;

public class URLPool {

    LinkedList<URLDepth> not_searched = new LinkedList<URLDepth>();
    LinkedList<URLDepth> searched = new LinkedList<URLDepth>();
    int mDepth;
    int waitThread;

    public URLPool(int maxDepth) {
        this.mDepth = maxDepth;
        waitThread = 0;
    }


    public synchronized URLDepth getPair() {
        while (not_searched.size() == 0) {
            waitThread++;
            try {
                wait(); //переводит вызывающий поток в состояние ожидания
            }
            catch (InterruptedException e) {
                System.out.println("Ignoring InterruptedException");
            }
            waitThread--;
        }
        return not_searched.removeFirst();
    }

    public synchronized void addPair(URLDepth pair) {
        if (!(searched.contains(pair))){
            searched.add(pair);
            if (pair.getDepth() < mDepth) {
                not_searched.add(pair);
                notify(); //продолжает работу потока, у которого ранее был вызван метод wait()
            }
        }
    }

    public synchronized int getWait() {
        return waitThread;
    }

    public LinkedList<URLDepth> getChecked() {
        return searched;
    }
}