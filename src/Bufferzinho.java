import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bufferzinho {
    private static int CAPACIDADE_BUFFER = 7;
    static boolean[] buffer = {false, false, false, false, false, false, false};
    static int semaforo = 7;
    static Lock mutex = new ReentrantLock();

    

    boolean[] get_arr(){
        return buffer;
    }

    public static int getNextPosition(){
        return Math.abs(semaforo - 7);
    }

    

}

    // Consumidor consumidor = new Consumidor(mutex, thread_id, QTDE_PRODUCAO)

    
        



