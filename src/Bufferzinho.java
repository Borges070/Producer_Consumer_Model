import java.util.concurrent.locks.Lock; 
import java.util.concurrent.locks.ReentrantLock;
// Implementamos a classe ReentrantLock para servir como um Mutex, que protege a região crítica
// Exclusão mútua -> evita condição de corrida
// Lock é o tipo da variável 

/*
 - Buffer (estrutura partilhada) como estudado em sala:
 - Estrutura de dados partilhada entre produtores e consumidores (array com N slots).
 - Cada slot pode estar cheio (item presente) ou vazio.
 - Produtores preenchem slots; consumidores esvaziam slots.
 - Acesso ao buffer deve ser sincronizado para evitar inconsistências.
*/

/*
- Seção crítica 
 - Trecho de código que acessa ou modifica dados partilhados (buffer, contadores, flags).
 - Precisam de sincronização (mutex/semaforo) para evitar "interleavings" incorretos. (não sei tradução boa)
 - Problemas quando não protegidas: race conditions, leituras/ escritas inconsistentes, perda de atualizações.
*/

public class Bufferzinho {
    // static transforma a variável em uma variável de classe, não de objeto, substitui, nesse caso, a necessidade de um método construtor
    static boolean[] buffer = {false, false, false, false, false, false, false}; // Buffer de 7 posições inicializado
    
    static int semaforo = 7; // semáforo implementado como uma variável inteira || 7 é cheio, 0 é vazio
    static Lock mutex = new ReentrantLock(); // implementa o mutex supracitado


    public static int getNextPosition(){
        return Math.abs(semaforo - 7); // truque para pegar a próxima posição do buffer
    }
}

