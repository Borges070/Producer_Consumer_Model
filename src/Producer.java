// freundliche Anmerkung --> semáforo = 7: 7 slots vazios no buffer
public class Producer extends Thread {
    
    private int id; // id da thread
    private int itemsToProduce; // quantos itens esse produtor produz de uma vez
    private int remainingItems; // quantos itens faltam

     public Producer(int id, int itemsToProduce) {
        this.id = id; 
        this.remainingItems = itemsToProduce;
        this.itemsToProduce = itemsToProduce; 
    }
    String t12 = "\t".repeat(12);

    @Override  // override do método run, da classe pai Thread
    public void run() {
        if (Bufferzinho.mutex.tryLock()){ // thread tenta obter exclusividade pelo mutex, caso consiga, executa as operações
            Logs.log("========================================"); // debug
            Logs.log("PRODUCER " + Producer.this.id + " ACQUIRED Mutex."); // debug
            Logs.log(t12 + "[Semaphore:   " + Bufferzinho.semaforo + "]"); // debug
            
            while (Bufferzinho.semaforo <= 7 && Producer.this.remainingItems > 0 && Bufferzinho.semaforo > 0) { // enquanto semáforo indica slots vazios no buffer e thread ainda precisa produzir, executa operações
                Producer.this.remainingItems--; // decrementa itens restantes
                Bufferzinho.buffer[Bufferzinho.getNextPosition()] = true; // adiciona item no buffer
                Bufferzinho.semaforo--; // atualiza semáforo
                
                // Esse log aqui foi arrumado para ficar em uma única linha, facilitando a leitura dos logs (agradeco a sugestão da colega IA)
                Logs.log(String.format("-> P%d produced 1 item. Remaining: %3d\t\t[Semaphore: %3d]",
                    Producer.this.id, Producer.this.remainingItems, Bufferzinho.semaforo));
            }

            Logs.log("PRODUCER " + Producer.this.id + " FINISHED its production cycle."); // debug

            Logs.log("--- Current Buffer State ---");
            for (int i = 0; i < Bufferzinho.buffer.length; i++){ // debug
                Logs.log("Slot " + i + ": " + (Bufferzinho.buffer[i] ? "FULL" : "EMPTY")); // debug
            }

            Logs.log("PRODUCER " + Producer.this.id + " RELEASED Mutex.");
            Bufferzinho.mutex.unlock(); // libera o recurso para outras threads
            Logs.log("========================================");

            try {
                sleep(1000); // dá um tempo para a thread (também evita que essa thread já concorra com a próxima na tentativa de pegar o lock)
                Logs.log("PRODUCER " + Producer.this.id + " is now sleeping...");
                Logs.log("  [Semaphore: " + Bufferzinho.semaforo + " before re-entry]");
                if (Producer.this.remainingItems <= 0){
                Producer.this.remainingItems = Producer.this.itemsToProduce; // entra com outra thread, para simbolizar que uma acabou e está sendo criada outra
                this.id++;
            }

            } catch (InterruptedException e) {
                e.printStackTrace(); // trata a exceção de interrupção da thread pelo usuário 
                // a JVM faz a própria implementação da SIGINT, simulando o SIGNAL INTERRUPTION, parecido com o do Linux, e o método sleep deixa o tratamento de erro à mercê do desenvolvedor
            }
        
        }
    }
}

// Tears in Heaven - Eric Clapton
