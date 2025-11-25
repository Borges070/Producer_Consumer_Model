import java.util.concurrent.locks.Lock; 
import java.util.concurrent.locks.ReentrantLock;
// Implementamos a classe ReentrantLock para servir como um Mutex, que protege a região crítica
// Exclusão mútua -> evita condição de corrida
// Lock é o tipo da variável 

public class Bufferzinho {
    // static transforma a variável em uma variável de classe, não de objeto, substitui, nesse caso, a necessidade de um método construtor
    static boolean[] buffer = {false, false, false, false, false, false, false}; // Buffer de 7 posições inicializado
    
    static int semaforo = 7; // semáforo implementado como uma variável inteira
    static Lock mutex = new ReentrantLock(); // implementa o mutex supracitado


    public static int getNextPosition(){
        return Math.abs(semaforo - 7); // truque para pegar a próxima posição do buffer
    }
}

