import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SushiRunner implements Runnable {

    int id;

    Semaphore block,mutex;
    SushiBar sushibar;



    public SushiRunner(int id,Semaphore mutex,Semaphore block,SushiBar sushibar) {
        this.id=id;
        this.mutex=mutex;
        this.block=block;
        this.sushibar=sushibar;

    }


    @Override
    public void run() {


        sushibar.acquire_sem_mutex();//MÜŞTERİLER MUTEX İ KAPAR

        if(sushibar.getMustWait())
        {

            sushibar.setWaiting(sushibar.getWaiting() + 1 );//BEKLEME SIRASINA 1 KİŞİ DAHA EKLEME
            System.out.println("Waiting Customer: " +id);//BEKLEME SIRASINDAKİ MÜŞTERİLERİN IDLERİ

            //sushibar.release_sem_mutex();//BEKLEYEN MÜŞTERİLER MUTEX İ BIRAKIR
            sushibar.acquire_sem_block();//BEKLEYEN MÜŞTERİLER BLOCK U KAPAR

        }else
        {

            sushibar.setEating(sushibar.getEating() + 1);//SUSHI YİYEN KİŞİLERE 1 KİŞİ DAHA EKLEME
            sushibar.setMustWait(sushibar.getEating() == 5);//SUSHI YİYEN KİŞİ SAYISA 5 OLURSA GELEN KİŞİYİ BEKLEME SIRASINA AL
            sushibar.release_sem_mutex();//SUSHI YİYEN MÜŞTERİLER MUTEX İ BIRAKIR

        }

        System.out.println(" + Eating Customer : " +id);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SushiRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

        sushibar.acquire_sem_mutex();//AYRILAN MÜŞTERİLER MUTEX İ KAPAR
        sushibar.setEating(sushibar.getEating() - 1);//SUSHI YİYEN BİR KİŞİ AYRILIR

        System.out.println(" - Leaving Customer : " +id);



        if(sushibar.getEating() == 0 && sushibar.getWaiting() !=0 )//SUSHI YİYEN KİŞİ SAYISI 0 SA VE BEKLEYEN SIRASI BOŞ DEĞİLSE
        {
            int n = Math.min(5,sushibar.getWaiting());//BEKLEME SIRASINDAN 5 KİŞİ ÇEKİLİR
            sushibar.setWaiting(sushibar.getWaiting() - n);//n TANE KİŞİ BEKLEME SIRASINDAN AZALTILIR
            sushibar.setEating(sushibar.getEating() + n);//SUSHI YİYEN KİŞİ SAYISINA n TANE KİŞİ EKLENİR
            sushibar.setMustWait(sushibar.getEating() == 5);//SUSHI YİYEN KİŞİ SAYISI 5'E EŞİTSE GERİ KALAN MÜŞTERİLER BEKLEMEYE ALINIR

            for(int i=0;i<n;i++)
            {
                sushibar.release_sem_block();//BEKLEYEN MÜŞTERİLER BLOCK SEMAPHORE UNU BIRAKIR
            }
        }

        sushibar.release_sem_mutex();//AYRILAN MÜŞTERİLER MUTEX İ BIRAKIR











    }

}