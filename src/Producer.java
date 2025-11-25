public class Producer extends Thread {
    
    private int id; // id da thread
    private int itemsToProduce; // quantos itens esse produtor produz de uma vez
    private int remainingItems; // quantos itens faltam

     public Producer(int id, int itemsToProduce) {
        this.id = id; 
        this.remainingItems = itemsToProduce;
        this.itemsToProduce = itemsToProduce; 
    }

    @Override  // override do método run, da classe pai Thread
    public void run() {
        if (Bufferzinho.mutex.tryLock()){ // thread tenta obter exclusividade pelo mutex, caso consiga, executa as operações
            System.out.println("----------------------------------------"); //debug
            System.out.println("Mutex acquired by Producer " + Producer.this.id); //debug

            if (Bufferzinho.semaforo <= 7){ // semáforo = 7: 7 slots vazios no buffer // VERIFICAR AQUI PODE TIRAR O IF
                while (Bufferzinho.semaforo <= 7 && Producer.this.remainingItems > 0 && Bufferzinho.semaforo > 0) { // enquanto semáforo indica slots vazios no buffer e thread ainda precisa produzir, executa operações
                    Producer.this.remainingItems--; // decrementa itens restantes
                    Bufferzinho.buffer[Bufferzinho.getNextPosition()] = true; // adiciona item no buffer
                    Bufferzinho.semaforo--; // atualiza semáforo
                    
                    System.out.println("Producer " + Producer.this.id + " produziu um item. Itens restantes para produzir: " + (Producer.this.remainingItems)); // debug
                    System.out.println(Bufferzinho.semaforo + " \t ||\t Valor do semaforo atual"); // debug
                }

                if (Producer.this.remainingItems <= 0){
                    Producer.this.remainingItems = Producer.this.itemsToProduce; // entra com outra thread, para simbolizar que uma acabou e está sendo criada outra
                    this.id++;
                }



                System.out.println("Producer " + Producer.this.id + " terminou de produzir todos os itens."); //debug 

                for (int i = 0; i < Bufferzinho.buffer.length; i++){ // debug
                    System.out.println("Buffer position " + i + ": " + Bufferzinho.buffer[i]); // debug
                }


                System.out.println("---------------------------------------");

                Bufferzinho.mutex.unlock(); // libera o recurso para outras threads

                try {
                    sleep(1000); // dá um tempo para a thread (também evita que essa thread já concorra com a próxima na tentativa de pegar o lock)

                    System.out.println("rodando1"); // debug
                    System.out.println(Bufferzinho.semaforo); // debussy

                } catch (InterruptedException e) {
                    e.printStackTrace(); // trata a exceção de interrupção da thread pelo usuário 
                    // a JVM faz a própria implementação da SIGINT, simulando o SIGNAL INTERRUPTION, parecido com o do Linux, e o método sleep deixa o tratamento de erro à mercê do desenvolvedor
                }
            } 
        }
    }
}

// Tears in Heaven - Eric Clapton