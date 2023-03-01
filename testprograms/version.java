
import br.app.pw3270.Terminal;

public class version
{
    public static void main (String[] args)
    {
        Terminal host = new Terminal();

        System.out.println("Using ipc3270 version " + host.getVersion() + "-" + host.getRevision());
        
        host.open();
        System.out.println("Using lib3270 version " + host.getLib3270Version() + "-" + host.getLib3270Revision()); 
        
        System.out.println("The program message is " + host.getProgramMessage());
        System.out.println("The terminal geometry is " + host.getWidth() + "/" + host.getHeight());
        
        host.close();
        
    }
    
}

