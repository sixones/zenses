jMTP
====

jMTP is a Java native interface library for communicating with MTP devices via libMTP and libUSB, jMTP is a sub-project of Zenses.

Compiling libs
==============

libUSB
------

	./configure
	make
	sudo make install

libMTP
------

	# fix issues with missing symbol: _kCFRunLoopDefaultMode (OSX only)
	export LDFLAGS="-framework IOKit -framework CoreFoundation"

	# fix for undefined reference to: rpl_malloc on x64 / x86_64
	export ac_cv_func_malloc_0_nonnull=yes

	./configure

	# sudo require to fix permission issue with writing to: libmtp.rules
	sudo make

	sudo make install
	
Compiling jMTP
==============


