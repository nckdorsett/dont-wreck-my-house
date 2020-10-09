import learn.data.*;
import learn.domain.GuestService;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.ui.ConsoleIO;
import learn.ui.Controller;
import learn.ui.View;

public class App {
    public static void main(String[] args) {


        GuestRepository guestRepository = new GuestFileRepository("./guests.csv");
        HostRepository hostRepository = new HostFileRepository("./hosts.csv");

        Controller controller = new Controller(
                new GuestService(guestRepository),
                new HostService(hostRepository),
                new ReservationService(new ReservationFileRepository("./reservations"),
                    guestRepository, hostRepository),
                new View(new ConsoleIO())
        );

        controller.run();

        //run all tests using this email
        //Host: irosenkranc8w@reverbnation.com
        //Guest: slomas0@mediafire.com

    }
}
