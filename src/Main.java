public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(Bufferzinho.buffer.length);

        Consumer consumidor = new Consumer(1, 12, Bufferzinho.mutex);
        Productor produtor = new Productor(1, 15, Bufferzinho.mutex);


        while (true){
            consumidor.run();
            produtor.run();
        }

        

    }
    
}
