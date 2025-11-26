public class Main {
    public static void main(String[] args) throws Exception { // freundliche Anmerkung: Rode o Código e pare-o com CTRL+C para gerar o log final
        System.out.println("Starting Producer-Consumer Simulation");

        Consumer consumidor = new Consumer(1, 12);
        Producer produtor = new Producer(1, 15);

        Logs.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        Logs.log("$             New Session Detected --> Logs started           $");
        Logs.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down. Check bobo.txt for logs.");
            Logs.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            Logs.log("$                 PROCESS STOPPED BY THE USER                 $");
            Logs.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }));
    
        while (true){
            produtor.run();
            consumidor.run();
        }

        

    }
    
}
