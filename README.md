# Java tn3270 protocol library


## Examples

Getting versions of jni module and loaded lib3270

```java
import br.app.pw3270.Terminal;

public class Sample {

    public static void main (String[] args) {

        try (Terminal host = new Terminal()) {

		System.out.println("Using ipc3270 version " + host.getVersion() + "-" + host.getRevision());
		System.out.println("Using lib3270 version " + host.getLib3270Version() + "-" + host.getLib3270Revision()); 
		    
	} catch (Exception e) {

		e.printStackTrace();

	}
        
    }
    
}
```

Get pw3270's session contents

```java
import br.app.pw3270.Terminal;

public class Sample {

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
```

