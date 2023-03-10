# Java tn3270 protocol library

Java class for interaction with pw3270 or lib3270.

[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![build result](https://build.opensuse.org/projects/home:PerryWerneck:pw3270/packages/tn3270-java/badge.svg?type=percent)](https://build.opensuse.org/package/show/home:PerryWerneck:pw3270/tn3270-java)

## Instalation

### Linux

[<img src="https://raw.githubusercontent.com/PerryWerneck/pw3270/master/branding/obs-badge-en.svg" alt="Download from open build service" height="80px">](https://software.opensuse.org/download.html?project=home%3APerryWerneck%3Apw3270&package=tn3270-java)

### Windows

The updated jar file for window can be found on [DropBox](https://www.dropbox.com/s/o14a5mjm02yexig/java-tn3270-5.2.23.3.9-x86_64.zip?dl=0)

## Building for Linux

### Requirements

 * [libipc3270](../../../libipc3270)

## Building for windows

### Windows native with MSYS2

1. Install java sdk

2. Build and install [libipc3270](../../../libipc3270)

3. Get sources from git

	```shell
	git clone https://github.com/PerryWerneck/tn3270-java.git ./tn3270-java
	```

4. Build package using the mingw shell

	```shell
	cd tn3270-java
	./autogen.sh
	make zip
	```
5. Install

	```shell
	make install
	```

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

