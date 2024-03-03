import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
public class SushiBar {
    int eating = 0,waiting = 0;
    boolean mustWait = false;
    Semaphore block,mutex;

    public SushiBar() {

        block = new Semaphore(0,true);
        mutex = new Semaphore(1,true);

    }

    public void acquire_sem_mutex()
    {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void acquire_sem_block()
    {
        try {
            block.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void release_sem_mutex()
    {

        mutex.release();
    }

    public void release_sem_block()

    {
        block.release();
    }
    public void setMustWait(boolean val)

    {
        mustWait = val;
    }
    public boolean getMustWait()
    {
        return mustWait;
    }
    public int getEating() {

        return eating;
    }

    public void setEating(int eating) {
        this.eating = eating;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting)
    {
        this.waiting = waiting;
    }

}