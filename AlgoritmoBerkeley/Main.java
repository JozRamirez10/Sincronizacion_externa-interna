import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Cliente> clientes = Arrays.asList( // Lista de clientes
                new Cliente("Cliente 1", 90L),
                new Cliente("Cliente 2", 120L),
                new Cliente("Cliente 3 ", 130L)
        );
        Servidor servidor = new Servidor(100, clientes); // Constructor del servidor
        clientes.forEach(Thread::start); // Inicia la ejecución de hilo de cada cliente
        servidor.start(); // Inicia la ejecución del hilo del servidor
    }
}