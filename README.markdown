Zenses2 Beta
============

Zenses is a simple application written in [Java][] that allows you to scrobble tracks from an [MTP][] media device to [Last.fm][]

The plan for Zenses is to build a cross platform application but at the moment only Windows is supported, work is in progress to bring the same functionality to Mac OS X and Linux.

Requirements
------------

### Windows

* [Java Runtime Environment 6+][JRE_WIN]
* [Windows Media Player 11][WMP11]

### Mac OS X

Unsupported

### Linux

Unsupported

### Build Requirements (only required to compile Zenses from source)

* [Java Development Kit 6+ (Windows)][JDK_WIN]
* [Maven][]

Compiling
---------

Zenses is split into 2 Java projects, `zenses-lib` and `zenses`. All the logic and the main workload happens in `zenses-lib` whilst `zenses` provides the GUI using [Java Swing][Swing].

Before you can build the two Zenses projects you will need to add the required dependencies to your [Maven][] installation, most will be automatically installed when you run the commands below. A few dependencies require a manual install as they cannot be redistributed. **TODO: add notes about installing extra dependencies.**

To build `zenses-lib`:

	mvn install
	
To build `zenses`:
	
	mvn assembly:assembly
	
After you build `zenses` you should have a [Jar][] in your `target` directory called `zenses2.jar`, you can run Zenses directly from the jar with:

	java -jar zenses2.jar
	
To build the launcher application (a Windows exe that launches the jar with the required arguments) and the installer application you will need the [Nullsoft Scriptable Install System][NSIS] installed on your machine. Once you have NSIS installed, you can use the build scripts inside the `scripts` directory which is in the `zenses` project folder.

Feature Plans
-------------

* Localisation support
* Mac OS X and Linux support (through [libMTP][])
* Multiple [Last.fm][] user authentication

Links
-----

[Java]: http://en.wikipedia.org/wiki/Java_%28programming_language%29 "Java"
[MTP]: http://en.wikipedia.org/wiki/Media_Transfer_Protocol "MTP"
[Swing]: http://en.wikipedia.org/wiki/Swing_(Java) "Java Swing"
[Jar]: http://en.wikipedia.org/wiki/JAR_(file_format) "Jar"

[WMP11]: http://www.microsoft.com/windows/windowsmedia/player/11/default.aspx "Windows Media Player 11"
[JRE_WIN]: http://www.java.com/en/download/installed.jsp?detect=jre&try=1 "Java Runtime Environment 6+"
[JDK_WIN]: http://java.sun.com/javase/downloads/index.jsp "Java Development Kit 6+"
[NSIS]: http://nsis.sourceforge.net/Main_Page "Nullsoft Scriptable Install System"

[libMTP]: http://libmtp.sourceforge.net/ "libMTP"

[Last.fm]: http://last.fm/ "Last.fm"
[Maven]: http://maven.apache.org/ "Maven"
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