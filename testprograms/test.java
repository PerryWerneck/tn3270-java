
import br.app.pw3270.Terminal;

public class test {

    public static void main (String[] args) {

        try (Terminal host = new Terminal(":a")) {
			
			host.setCharSet("UTF-8");
			
			if(!host.getConnected()) {
				System.out.println("Connecting to host");
				host.connect(20);
			}

			if(!host.getConnected()) {
				System.out.println("The host is not connected");
			} else if(!host.getReady()) {
				System.out.println("The host is not ready");
			} else {
				System.out.println(host.toString());
			}
		        
		} catch (Exception e) {

		    e.printStackTrace();

		}
        
    }
    
}

