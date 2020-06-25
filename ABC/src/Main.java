public class Main {
    static final Object mon = new Object();
    static int currentNum = 1;
    static final int num = 5;

    public static void main(String[] args) {
        new Thread(() -> printChar("A",1,2)).start();
        new Thread(() -> printChar("B",2,3)).start();
        new Thread(() -> printChar("C",3,1)).start();
    }

    public static void printChar(String s, int a, int b){
        try {
            for (int i = 0; i < num; i++) {
                synchronized (mon) {
                    while (currentNum != a) {
                        mon.wait();
                    }
                    System.out.print(s);
                    currentNum = b;
                    mon.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
