import java.util.concurrent.locks.Lock;
// Import is not working because is on the same package, it seems i can just use it directly (don't know how)
// import Bufferzinho.Bufferzinho;

public class Consumer extends Thread { 
    
    
    private int id; 
    private int itemsToConsume; 
    private Lock mutex; // Mutex lock for critical section
    private int remainingItems;

    public Consumer(int id, int itemsToConsume, Lock mutex) {
        this.id = id;
        this.remainingItems = itemsToConsume; 
        this.mutex = mutex;
        this.itemsToConsume = itemsToConsume;  
    }

    @Override
    public void run() {
        if (mutex.tryLock()){ // How does mutex usage actually works? --> Maybe use Bufferzinho.semaforo here instead?
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Consumer " + Consumer.this.id); //debug

            if (Bufferzinho.semaforo >= 0){ 
                while (Bufferzinho.semaforo < 7 && Consumer.this.remainingItems > 0) { 
                    Bufferzinho.semaforo++; 
                    Consumer.this.remainingItems--; 
                    Bufferzinho.buffer[Bufferzinho.getNextPosition()] = false; // see import fault
                    
                    // Both are debugging logs
                    System.out.println("Consumer " + Consumer.this.id + " consumiu um item. Itens restantes para consumir: " + (Consumer.this.remainingItems)); 
                    System.out.println(Bufferzinho.semaforo + " \t||Valor do semaforo atual"); 
                }

                if (Consumer.this.remainingItems <= 0){
                    Consumer.this.remainingItems = Consumer.this.itemsToConsume; 
                }



                System.out.println("Consumer " + Consumer.this.id + " terminou de consumir todos os itens."); //debug 
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
                
                if (Consumer.this.remainingItems <= 0){
                    Consumer.this.remainingItems = Consumer.this.itemsToConsume; 
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
