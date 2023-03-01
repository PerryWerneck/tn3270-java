
import br.app.pw3270.Terminal;

public class version
{
    public static void main (String[] args)
    {
        Terminal host = new Terminal();

        System.out.println("Using ipc3270 version " + host.get_version() + "-" + host.get_revision());
        
        host.open();
        
        System.out.println("The program message is " + host.get_program_message());
        System.out.println("The terminal geometry is " + host.get_screen_width() + "/" + host.get_screen_height());
        
        host.close();
        
    }
    
}

