# Java tn3270 protocol library


## Examples

Getting versions of jni module and loaded lib3270

```java
import br.app.pw3270.Terminal;

public class sample {

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

