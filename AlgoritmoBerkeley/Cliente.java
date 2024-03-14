import java.util.Random;

public class Cliente extends Thread{
    private Long time;
    private Long reajuste;
    private int timeSleep;

    // Recibe el tiempo de inicio del cliente y un nombre
    public Cliente(String name, Long time) {
        super(name);
        this.time = time;
        setTimeSleep();
    }

    // Para que los clientes puedan dormir en intervalos diferentes y simular un entorno más real de diferentes
    // ciclos de reloj, el timeSleep se obtiene de forma aleatorio entre 1000 (1seg) y 1500 (1.5seg)
    private void setTimeSleep(){
        Random rand = new Random();
        // Generar un número aleatorio entre 1000 y 1500
        this.timeSleep = rand.nextInt(501) + 1000;
        System.out.println(this.getName() + " - Time Sleep: " + this.timeSleep);
        System.out.println("--------------");
    }

    @Override
    public void run() {
        while(true){
            try {
                sleep(timeSleep); // El cliente duerme su timeSleep
                this.time += 1; // Cada timeSleep el cliente incrementa en 1 su time
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // ---- Getters y Setters ---

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getReajuste() {
        return reajuste;
    }

    public void setReajuste(Long reajuste) {
        this.reajuste = reajuste;
    }
}
