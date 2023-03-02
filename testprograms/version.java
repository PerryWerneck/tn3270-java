
import br.app.pw3270.Terminal;

public class version {

    public static void main (String[] args) {

        try (Terminal host = new Terminal()) {

			System.out.println("Using ipc3270 version " + host.getVersion() + "-" + host.getRevision());
			System.out.println("Using lib3270 version " + host.getLib3270Version() + "-" + host.getLib3270Revision()); 
		        
		} catch (Exception e) {

		    e.printStackTrace();

		}
        
    }
    
}

