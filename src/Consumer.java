public class Consumer extends Thread { 
/* Thread
 - Uma thread é uma linha de execução independente dentro do processo.
 - Permite execução concorrente de código que nem implementado.
 - Cada Thread tem um método run() com o trabalho a executar.
 - Concorrência exige sincronização ao partilhar dados para evitar condições de corrida.
 - Threads permitem que um programa faça uma tarefa em segundo plano enquanto outra tarefa é executada na frente.(baixar arquivo)
 - Gasta menos recursos do que processos completos, facilitando multitarefa.
*/

	private int id;
	private int itemsToConsume;
	private int remainingItems;

	public Consumer(int id, int itemsToConsume) {
		this.id = id;
		this.remainingItems = itemsToConsume;
		this.itemsToConsume = itemsToConsume;
	}
    String t12 = "\t".repeat(12);

/*
 - Mutex (mutual exclusion) conforme estudado em sala:
 - Mutex é uma trava binária que garante exclusão mútua: apenas uma thread pode ter o lock.
 - Protege a seção crítica onde se lê/escreve estado partilhado.
 - Threads que não obtêm o lock devem esperar até que ele seja liberado.
 - Deve-se manter a região protegida o mais curta possível para reduzir contenção.
*/

	@Override
	public void run() {
		if (Bufferzinho.mutex.tryLock()) { // thread tenta obter exclusividade pelo mutex, caso consiga, executa as operações
                                           // Conforme estudado em sala, o mutex garante exclusão mútua, evitando condições de corrida
			Logs.log("========================================"); // debug
			Logs.log("CONSUMER " + Consumer.this.id + " ACQUIRED Mutex."); // debug
            Logs.log(t12 + "[Semaphore:   " + Bufferzinho.semaforo + "]"); // debug
		    
            while (Bufferzinho.semaforo < 7 && Consumer.this.remainingItems > 0) { // enquanto semáforo indica slots cheios no buffer e thread ainda precisa consumir, executa operações      
                Bufferzinho.semaforo++;                                               
                Consumer.this.remainingItems--;
                Bufferzinho.buffer[Bufferzinho.getNextPosition()] = false; // remove item do buffer

                // Esse log aqui foi arrumado para ficar em uma única linha, facilitando a leitura dos logs (agradeco a sugestão do colega IA)
                Logs.log(String.format("-> C%d consumed 1 item. Remaining: %3d\t\t[Semaphore: %3d]",
                    Consumer.this.id, Consumer.this.remainingItems, Bufferzinho.semaforo));
            }

            

            Logs.log("CONSUMER " + Consumer.this.id + " FINISHED its consumption cycle."); // debug
            // debug log module for buffer
            Logs.log("--- Current Buffer State ---");
            for (int i = 0; i < Bufferzinho.buffer.length; i++) {
                Logs.log("Slot " + i + ": " + (Bufferzinho.buffer[i] ? "FULL" : "EMPTY"));
            }

            Bufferzinho.mutex.unlock(); // libera o recurso para outras threads
            Logs.log("CONSUMER " + Consumer.this.id + " RELEASED Mutex.");

            Logs.log("========================================");
            try {
                sleep(1000); // dá um tempo para a thread (também evita que essa thread já concorra com a próxima na tentativa de pegar o lock)
                Logs.log("CONSUMER " + Consumer.this.id + " is now sleeping...");
                Logs.log("  [Semaphore: " + Bufferzinho.semaforo + " before re-entry]");
                if (Consumer.this.remainingItems <= 0) {
                Consumer.this.remainingItems = Consumer.this.itemsToConsume; // entra com outra thread, para simbolizar que uma acabou e está sendo criada outra
                this.id++;
            }

            } catch (InterruptedException e) {
                e.printStackTrace(); // trata a exceção de interrupção da thread pelo usuário
                // a JVM faz a própria implementação da SIGINT, simulando o SIGNAL INTERRUPTION, parecido com o do Linux, e o método sleep deixa o tratamento de erro à mercê do desenvolvedor
            }
		} 
	}
}