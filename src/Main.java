public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println(Bufferzinho.buffer.length);

        Consumer consumidor = new Consumer(1, 12);
        Producer produtor = new Producer(1, 15);


        while (true){
            consumidor.run();
            produtor.run();
        }

        

    }
    
}
