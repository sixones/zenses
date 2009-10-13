Zenses2 Beta
============

Zenses is a simple application written in [Java][] that allows you to scrobble tracks from an [MTP][] media device to [Last.fm][]

The plan for Zenses is to build a cross platform application but at the moment only Windows is supported, work is in progress to bring the same functionality to Mac OS X and Linux.

Requirements
------------

#### Windows

* [Java Runtime Environment 6+][JRE_WIN]
* [Windows Media Player 11][WMP11]

#### Mac OS X / Linux

* [libMTP][]
* [libUSB][]

### Build Requirements (only required to compile Zenses from source)

* [Java Development Kit 6+ (Windows)][JDK_WIN]
* [Maven][]

Compiling
---------

Zenses is split into 2 Java projects, `zenses-lib` and `zenses`. All the logic and the main workload happens in `zenses-lib` whilst `zenses` provides the GUI using [Java Swing][Swing]. To build Zenses with [Eclipse][] install the [MavenEmbed][] plugin, and use the instructions in the Dependencies section to import the missing dependencies.

Before you can build the two Zenses projects you will need to add the required dependencies to your [Maven][] installation, most will be automatically installed when you run the commands below. A few dependencies require a manual install as they cannot be redistributed, see the Dependencies section for instructions and information on the required artefacts.

To build `zenses-lib`:

	mvn install
	
To build `zenses`:
	
	mvn assembly:assembly
	
After you build `zenses` you should have a [Jar][] in your `target` directory called `zenses2.jar`, you can run Zenses directly from the jar with:

	java -jar zenses2.jar
	
To build the launcher application (a Windows exe that launches the jar with the required arguments) and the installer application you will need the [NSIS][] installed on your machine. Once you have NSIS installed, you can use the build scripts inside the `scripts` directory which is in the `zenses` project folder.

Dependencies (requires manual install)
--------------------------------------

JMTP

	Artifact file:	jmtp.jar
	Group Id:		jmtp
	Artifact Id:	jmtp
	Version:		1.0
	Packaging:		jar

Last.fm Bindings

	Artifact file:	last.fm-bindings.jar
	Group Id:		last.fm
	Artifact Id:	last.fm-bindings
	Version:		5.21
	Packaging:		jar

JMS

	Artifact file:	jms.jar
	Group Id:		javax.jms
	Artifact Id:	jms
	Version:		1.1
	Packaging:		jar

JMX Tools

	Artifact file:	jmxtools.jar
	Group Id:		com.sun.jdmk
	Artifact Id:	jmxtools
	Version:		1.2.1
	Packaging:		jar

JMXRI

	Artifact file:	jmxri.jar
	Group Id:		com.sun.jmx
	Artifact Id:	jmxri
	Version:		1.2.1
	Packaging:		jar

Spring Application Framework

	Artifact file:	appframework-1.0.3.jar
	Group Id:		org.jdesktop
	Artifact Id:	appframework
	Version:		1.0.3
	Packaging:		jar

### Installing Dependencies (through Eclipse)

For each;
	
	File => Import
	Maven => Install or deploy an artifact to a Maven repository
	For artifact file => Click browse => Browse to the libs directory in the Zenses2 root and select the right jar file
	Enter the artifact information (see list above, make sure Generate POM and Create Checksum are both checked)
	Project => Update Maven Dependencies

You can then import the projects:

	File => Import
	General => Maven Projects
	
	// browse to the root of the zenses project directory (the one that contains zenses and zenses-lib)
	select the 2 pom.xml projects (zenses and zenses-lib)
	click finish
	
	// this may take a while
	// NOTE: let Eclipse / Maven finish before attempting to compile Zenses, doing so will cause countless errors about missing dependencies.

Links
-----

* [MTP][]
* [Last.fm][]
* [Java][]
* [Swing][]
* [Jar][]
* [JRE_WIN][]
* [JDK_WIN][]
* [WMP11][]
* [NSIS][]
* [libMTP][]
* [libUSB][]
* [Maven][]
* [MavenEmbed][]
* [Eclipse][]
* [MIT][]

[Java]: http://en.wikipedia.org/wiki/Java_%28programming_language%29 "Java"
[MTP]: http://en.wikipedia.org/wiki/Media_Transfer_Protocol "MTP"
[Swing]: http://en.wikipedia.org/wiki/Swing_(Java) "Java Swing"
[Jar]: http://en.wikipedia.org/wiki/JAR_(file_format) "Jar"

[WMP11]: http://www.microsoft.com/windows/windowsmedia/player/11/default.aspx "Windows Media Player 11"
[JRE_WIN]: http://www.java.com/en/download/installed.jsp?detect=jre&try=1 "Java Runtime Environment 6+"
[JDK_WIN]: http://java.sun.com/javase/downloads/index.jsp "Java Development Kit 6+"
[NSIS]: http://nsis.sourceforge.net/Main_Page "Nullsoft Scriptable Install System"
[Eclipse]: http://eclipse.org "Eclipse"

[libMTP]: http://libmtp.sourceforge.net/ "libMTP"
[libUSB]: http://www.libusb.org/ "libUSB"

[Last.fm]: http://last.fm/ "Last.fm"
[Maven]: http://maven.apache.org/ "Maven"
[MavenEmbed]: http://m2eclipse.sonatype.org/update/ "Maven Integration"
[MIT]: http://www.opensource.org/licenses/mit-license.php "MIT"

License
-------

Zenses and Zenses2 is released under the [MIT][] license, which is defined as;

Copyright (c) 2009 Adam Livesley

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.