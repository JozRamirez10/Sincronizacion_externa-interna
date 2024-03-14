import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente extends Thread{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private long timeBegin, timeEnd, timeServer, time;

    // Levanta la conexión del socket en el 'localhost' y el puerto que recibe como parámetro
    private void levantarConexion(int port) throws IOException {
        this.socket = new Socket("localhost", port);
    }

    // Inicializa los flujos de entrada y salida del socket
    private void inicializarFlujos() throws IOException {
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    // Cierra la conexión de los flujos y el socket, finalmente termina la conexión
    private void cerrarConexion(){
        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Conexión finalizada");
            System.exit(0);
        }
    }

    // Envía los datos al servidor
    private void enviarDatos() throws IOException {
        this.timeBegin = System.currentTimeMillis(); // Obtiene el tiempo actual en milisegundos
        output.writeLong(timeBegin); // Envia el tiempo al servidor
        System.out.println("T0: " + timeBegin); // Imprime el tiempo actual
    }

    // Recibe los datos del servidor
    private void recibirDatos() throws IOException {
        this.timeServer = input.readLong(); // Recibe el tiempo del servidor
        this.timeEnd = System.currentTimeMillis(); // Obtiene el tiempo actual del cliente
        System.out.println("Tserver: " + timeServer); // Imprime el tiempo del servidor
        System.out.println("T1: " + timeEnd); // Imprime el tiempo actual del cliente
    }

    // Realiza el cálculo del algoritmo de Cristian
    private void algoritmoCristian(){
        time = timeServer + ( (timeEnd - timeBegin) / 2 ); // Con los tiempo obtenidos realiza los cálculos
        System.out.println("Time: " + this.time); // Imprime el resutado del algoritmo
    }

    // Ejecución del hilo cliente
    @Override
    public void run() {
        while(true){
            try {
                levantarConexion(9182); // Levanta la conexión del cliente en el puerto 9182
                inicializarFlujos(); // Inicia los flujos de entrada y salida
                enviarDatos(); // Envía el tiempo al servidor
                recibirDatos(); // Obtiene el tiempo del servidor
                algoritmoCristian(); // Realiza los cálculos del tiempo
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                cerrarConexion();
            }
        }
    }

    // Método principal para la ejecución del cliente
    public static void main(String[] args) {
        Cliente cliente = new Cliente(); // Construye el objeto cliente
        cliente.start(); // Inicia la ejecución del cliente
    }
}
