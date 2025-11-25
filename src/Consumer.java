public class Consumer extends Thread { 
    
    
    private int id; 
    private int itemsToConsume; 
    private int remainingItems;

    public Consumer(int id, int itemsToConsume) {
        this.id = id;
        this.remainingItems = itemsToConsume; 
        this.itemsToConsume = itemsToConsume;  
    }

    @Override
    public void run() {
        if (Bufferzinho.mutex.tryLock()){ 
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Consumer " + Consumer.this.id); //debug

            if (Bufferzinho.semaforo >= 0){ 
                while (Bufferzinho.semaforo < 7 && Consumer.this.remainingItems > 0) { 
                    Bufferzinho.semaforo++; 
                    Consumer.this.remainingItems--; 
                    Bufferzinho.buffer[Bufferzinho.getNextPosition()] = false; // 
                    
                    // Both are debugging logs
                    System.out.println("Consumer " + Consumer.this.id + " consumiu um item. Itens restantes para consumir: " + (Consumer.this.remainingItems)); 
                    System.out.println(Bufferzinho.semaforo + " \t||Valor do semaforo atual"); 
                }

                if (Consumer.this.remainingItems <= 0){
                    Consumer.this.remainingItems = Consumer.this.itemsToConsume; 
                    this.id++; 
                }



                System.out.println("Consumer " + Consumer.this.id + " terminou de consumir todos os itens."); //debug 
                // debug log module for buffer
                for (int i = 0; i < Bufferzinho.buffer.length; i++){
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]);
                }


                System.out.println("---------------------------------------");
                Bufferzinho.mutex.unlock();
                try {
                    sleep(1000);
                    System.out.println("rodando1");
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
