import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
    private ServerSocket serverSocket;
    private Socket socket;
    private long time;
    private DataInputStream input;
    private DataOutputStream output;

    // Inicializa el servidor en el puerto que recibe como parámetro
    public Servidor(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Establece la conexión con el cliente
    public void levantarConexion() throws IOException {
        System.out.println("Esperando conexión entrante en el puerto " + serverSocket.getLocalPort());
        this.socket = serverSocket.accept(); // Acepta la conexión con el socket cliente
        System.out.println("Conexión establecida con " + socket.getRemoteSocketAddress());
    }

    // Inicializa los flujos de entrada y salida
    private void inicializarFlujos() throws IOException {
        this.input = new DataInputStream(socket.getInputStream()); // Recibe datos del cliente
        this.output = new DataOutputStream(socket.getOutputStream()); // Envía datos al cliente
    }

    // Cierra los flujos, el socket y termina la comunicación
    private void cerrarConexion() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            System.out.println("Conexión finalizada");
            System.exit(0);
        }

    }

    // El servidor recibe un mensaje por parte del cliente
    private void recibirDatos() throws IOException {
        System.out.println("Recibi datos " + input.readLong());
    }

    // El servidor envía un mensaje al cliente
    private void enviarDatos() throws IOException {
        this.time = System.currentTimeMillis() + 100; // Obtiene el tiempo actual en milisegundos
        output.writeLong(this.time); // Envía el tiempo al cliente
    }

    // Ejecución del hilo
    @Override
    public void run() {
        while(true){
            try {
                levantarConexion(); // Levanta la conexión del servidor
                inicializarFlujos(); // Inicializa los flujos de entrada y salida
                recibirDatos(); // Espera a recibir los datos del cliente
                enviarDatos(); // Envía su tiempo al cliente
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                cerrarConexion();
            }
        }
    }

    // Método principal que permite la ejecución del servidor
    public static void main(String[] args) {
        Servidor servidor = new Servidor(9182); // Crea el objeto servidor en el puerto 9182
        servidor.start(); // Comienza la ejecución del servidor
    }
}
