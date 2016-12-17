
import pw3270.*;

public class version
{
    public static void main (String[] args)
    {
        terminal host = new terminal();

        System.out.println("Usando pw3270 versão " + host.get_version());
        
    }
    
}
