import java.util.concurrent.locks.Lock;
// Import is not working because is on the same package, it seems i can just use it directly (don't know how)
// import Bufferzinho.Bufferzinho;
public class Productor extends Thread{
    
    private int id; 
    private int itemsToProduce;
    private Bufferzinho buffer; // Import buffer class
    private Lock mutex; // Mutex lock for critical section
    private int remainingItems;

     public Productor(int id, int itemsToProduce, Lock mutex) {
        this.id = id;
        this.remainingItems = itemsToProduce;
        this.mutex = mutex;
        this.itemsToProduce = itemsToProduce; 
    }

    @Override
    public void run() {
        if (mutex.tryLock()){ // How does mutex usage actually works? --> Maybe use Bufferzinho.semaforo here instead?
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Productor " + Productor.this.id); //debug

            if (Bufferzinho.semaforo <= 7){ // trocar
                while (Bufferzinho.semaforo <= 7 && Productor.this.remainingItems > 0 && Bufferzinho.semaforo > 0) { 
                    Productor.this.remainingItems--; 
                    Bufferzinho.buffer[Bufferzinho.getNextPosition()] = true; // see import fault
                    Bufferzinho.semaforo--; 
                    
                    // Both are debugging logs
                    System.out.println("Productor " + Productor.this.id + " produziu um item. Itens restantes para produzir: " + (Productor.this.remainingItems)); 
                    System.out.println(Bufferzinho.semaforo + " \t ||\t Valor do semaforo atual"); 
                }

                if (Productor.this.remainingItems <= 0){
                    Productor.this.remainingItems = Productor.this.itemsToProduce; 
                }



                System.out.println("Productor " + Productor.this.id + " terminou de produzir todos os itens."); //debug 
                // debug log module for buffer
                for (int i = 0; i < Bufferzinho.buffer.length; i++){
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]);
                }


                System.out.println("---------------------------------------");
                mutex.unlock();
                // Flag to indicate the end of cosume --> Productor can play now (Project works based on gambiarra? Then lets refine the code)
                try {
                    sleep(1000);
                    System.out.println("rodando1");
                    System.out.println(Bufferzinho.semaforo);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {

                if (Productor.this.remainingItems <= 0){
                    Productor.this.remainingItems = Productor.this.itemsToProduce; 
                }

                mutex.unlock();

                //sleep 
                try {
                    sleep(1000);
                    System.out.println("rodando2");
                    System.out.println(Bufferzinho.semaforo);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
            }
        } else {
            System.out.print("");
        }
    }
}