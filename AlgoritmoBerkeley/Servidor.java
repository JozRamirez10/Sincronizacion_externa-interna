import java.util.List;

public class Servidor extends Thread{
    private long time;
    private long suma = 0;
    private long promedio;

    private List<Cliente> clientes;

    // Recibe el tiempo de inicio y una lista de clientes
    public Servidor(long time, List<Cliente> clientes) {
        this.time = time;
        this.clientes = clientes;
    }

    // Ejecución del hilo
    @Override
    public void run() {
        while(true){
            try {
                sleep(1000); // Duerme 1 segundo
                System.out.println("Servidor: " + this.time); // Imprime el time del servidor
                clientes.forEach(cliente -> System.out.println(cliente.getName() +": " + cliente.getTime())); // Imprime el time de los clientes
                System.out.println("-------------------");

                algoritmoBerkeley(); // Realiza el algoritmo berkeley
                this.time++; // Incrementa el time del servidor
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void algoritmoBerkeley(){
        // Obtiene el promedio
        for(Cliente cliente : clientes){
            cliente.setReajuste(cliente.getTime() - this.time); // Obtiene la diferencia entre el tiempo del cliente y el servidor
                                                                // El resultado lo guarda en el atributo del cliente "reajuste"
            suma += cliente.getReajuste(); // Suma obtenida en cada cliente

        }
        promedio = suma / (clientes.size() + 1); // Obtiene el promedio de la suma de las diferencias

        // Hace el reajuste
        this.time += promedio; // Incrementa el promedio al servidor
        for(Cliente cliente : clientes){
            cliente.setTime((promedio - cliente.getReajuste()) + cliente.getTime());
                // Al promedio se le resta el reajuste y el resultado lo suma al time del cliente
        }
    }
}
