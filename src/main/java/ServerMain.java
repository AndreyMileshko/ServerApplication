import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println("Сервер запущен.");
        int port = 8086;

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try(Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
                {
                    System.out.printf("Принято новое обращение. Порт %d\n", socket.getPort());
                    out.println("Как я могу к вам обращаться? Для прекращения сеанса введите \"end\"");
                    String name = in.readLine();
                    out.println(String.format("Добрый день %s! Какой номер вы хотите забронировать Эконом/Стандарт/Люкс?", name));
                    String apartmentType = in.readLine();
                    out.println("Желаемая дата размещения?");
                    String date = in.readLine();
                    out.println("Количество дней проживания?");
                    String day = in.readLine();
                    out.println(String.format("%s для вас забронирован %s на %s дней(-я) c %s. Хорошего вам дня!",
                            name, apartmentType, day, date));
                    if (name == null || apartmentType == null || date == null || day == null) throw  new IOException();
                    System.out.printf("%s забронировал %s на %s дней(-я) c %s.\n",
                            name, apartmentType, day, date);
                } catch (IOException e) {
                    System.out.println("Соединение разорвано.");
                }
            }
        } catch (IOException e) {
            System.out.println("Сервер лёг.");;
        }
    }
}
