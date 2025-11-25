import java.util.concurrent.locks.Lock;
// Import is not working because is on the same package, it seems i can just use it directly (don't know how)
// import Bufferzinho.Bufferzinho;

public class Consumer extends Thread { // trocar
    
    
    private int id; 
    private int itemsToConsume; // trocar
    private int semaphore; // Import semaphore class || Sem = 0 (all filled) Sem = 7 (all empty) --> Is now a variable of the class
    private Lock mutex; // Mutex lock for critical section
    private int remainingItems;

    public Consumer(int id, int itemsToConsume, Lock mutex) {
        this.id = id;
        this.remainingItems = itemsToConsume; // trocar
        this.mutex = mutex;
        this.itemsToConsume = itemsToConsume; // trocar 
    }

    @Override
    public void run() {
        if (mutex.tryLock()){ // How does mutex usage actually works? --> Maybe use semaphore here instead?
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Consumer " + Consumer.this.id); //debug

            if (semaphore > 0){ // trocar
                while (semaphore < 7 && Consumer.this.remainingItems > 0) { // trocar
                    semaphore++; // trocar
                    Consumer.this.remainingItems--; // trocar
                    Bufferzinho.buffer[Bufferzinho.getNextPosition()] = false; // see import fault
                    
                    // Both are debugging logs
                    System.out.println("Consumer " + Consumer.this.id + " consumiu um item. Itens restantes para consumir: " + (Consumer.this.remainingItems)); // trocar
                    System.out.println(semaphore + " \t||Valor do semaforo atual"); // trocar
                }



                System.out.println("Consumer " + Consumer.this.id + " terminou de consumir todos os itens."); //debug // trocar
                // debug log module for buffer
                for (int i = 0; i < Bufferzinho.buffer.length; i++){
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]);
                }


                System.out.println("---------------------------------------");
                mutex.unlock();
                // Flag to indicate the end of cosume --> Productor can play now (Project works based on gambiarra? Then lets refine the code)
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {

                if (Consumer.this.remainingItems == 0){
                    Consumer.this.remainingItems = Consumer.this.itemsToConsume; // trocar
                }

                mutex.unlock();

                //sleep 
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            
            }
        } else {
            System.out.print("");
        }
    }
}
