import java.util.concurrent.Semaphore;

public class Main  {

    static SushiBar sushibar= new SushiBar();
    static Semaphore block = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);


    public static void main(String[] args) {


        Thread[] cus= new Thread[10];
        for(int i=0; i<10; i++){


            cus[i] = new Thread (new SushiRunner(i,mutex,block,sushibar));

        }
        for(int i=0; i<10; i++){
            cus[i].start();
        }

    }
}