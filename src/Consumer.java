import java.util.concurrent.locks.Lock;
// Import is not working because is on the same package, it seems i can just use it directly (don't know how)
// import Bufferzinho.Bufferzinho;

public class Consumer extends Thread {
    
    
    private int id; 
    private int itemsToConsume;
    private Bufferzinho buffer; // Import buffer class
    private int semaphore; // Import semaphore class || Sem = 0 (all filled) Sem = 7 (all empty) --> Is now a variable of the class
    private Lock mutex; // Mutex lock for critical section

    public Consumer(int id, int itemsToConsume, boolean[] buffer, Lock mutex ) {
        this.id = 1;
        this.itemsToConsume = 12;
        this.mutex = mutex;
        this.buffer = buffer; // See import fault
    }

    @Override
    public void run() {
        if (mutex.trylock(buffer)){ // How does mutex usage actually works? --> Maybe use semaphore here instead?
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Consumer " + Consumer.this.id); //debug

            if (semaphore > 0){
                while (semaphore < 7 && Consumer.this.itemsToConsume > 0) {
                    semaphore++;
                    Consumer.this.itemsToConsume--;
                    Bufferzinho.buffer[Math.abs(semaphore - 7)] = false; // see import fault
                    
                    // Both are debugging logs
                    System.out.println("Consumer " + Consumer.this.id + " consumiu um item. Itens restantes para consumir: " + (Consumer.this.itemsToConsume));
                    System.out.println(semaphore + " \t||Valor do semaforo atual"); 
                    // Both are debugging logs
                }
                System.out.println("Consumer " + Consumer.this.id + " terminou de consumir todos os itens."); //debbug
                // debbug log module for buffer
                for (int i = 0; i < Bufferzinho.buffer.length; i++){
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]);
                }
                System.out.println("---------------------------------------");
                mutex.unlock();
                // Flag to indicate the end of cosume --> Productor can play now (Project works based on gambiarra? Then lets refine the code)
                sleep(1000);
            }
            else {
                mutex.unlock();
                sleep(1000);
            
            }
        }
    }
}
