public class Bufferzinho {
    boolean[] buffer = new boolean[7];

    public Bufferzinho() {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = false; 
        }

    
    for (int i = 0; i < buffer.length; i++){
        System.out.println("Buffer position " + i + ": " + buffer[i]);
    }
    }

    

}
