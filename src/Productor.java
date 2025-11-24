import java.util.concurrent.locks.Lock;
// Import is not working because is on the same package, it seems i can just use it directly (don't know how)
// import Bufferzinho.Bufferzinho;

public class Productor extends Thread{
    
    private int id; 
    private int itemsToProduce;
    private Bufferzinho buffer; // Import buffer class
    private int semaphore; // Import semaphore class || Sem = 0 (all filled) Sem = 7 (all empty) --> Is now a variable of the class
    private Lock mutex; // Mutex lock for critical section

    public Productor(int id, int itemsToProduce, Bufferzinho buffer, Lock mutex ) {
        this.id = id;
        this.itemsToProduce = 15;
        this.mutex = mutex;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        if (mutex.tryLock(buffer)){
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Produtores " + Productor.this.id); //debug

            if (semaphore > 0){
                while (semaphore > 0 && Productor.this.itemsToProduce > 0) {
                    semaphore--;
                    Productor.this.itemsToProduce--;
                    Bufferzinho.buffer[Math.abs(semaphore - 7)] = true; // see import fault
                    
                    // Both are debugging logs
                    System.out.println("Produtores " + Productor.this.id + " produziu um item. Itens restantes para produzir: " + (Productor.this.itemsToProduce));
                    System.out.println(semaphore + " \t||Valor do semaforo atual"); 
                    // Both are debugging logs
                }
                System.out.println("Produtores " + Productor.this.id + " terminou de produzir todos os itens."); //debbug
                // debbug log module for buffer
                for (int i = 0; i < Bufferzinho.buffer.length; i++){
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]);
                }
                System.out.println("---------------------------------------");
                mutex.unlock();
                // Flag to indicate the end of production --> consumer can play now (Project works based on gambiarra? Then lets refine the code)
                sleep(1000);
            }
            else {
                mutex.unlock();
                sleep(1000);
            
            }
        }
    }
}