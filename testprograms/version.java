
import br.app.pw3270.Terminal;

public class version
{
    public static void main (String[] args)
    {
        Terminal host = new Terminal();

        System.out.println("Using ipc3270 version " + host.get_version() + " " + host.get_revision());
        
    }
    
}

