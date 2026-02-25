3-Heights(TM) PDF Optimization API
==================================

Version 4.8
Copyright (C) 1995-2017 PDF Tools AG

http://www.pdf-tools.com
pdfsupport@pdf-tools.com


------------------------------------------------------------------------------

Windows
=======

File list
---------
bin\PdfOptimizeAPI.dll    Dynamic link library (required)
bin\*NET.*                .NET assemblies
bin\LicenseManager.exe    License manager tool (GUI version)
doc\pola.pdf              User manual
doc\readme.txt            This file
doc\javadoc\              Java API documentation
doc\pdf-license-v*.pdf    License terms for PDF Tools
doc\PdfOptimizeAPI.idl    COM interface definition (for documentation purposes)
include\*.h               Include files for C/C++ programs
include\bseerror.h        Supplementary C header file containting error codes
jar\pola.jar              Java interface classes archive
lib\PdfOptimize.lib       Stub library for C Programs
samples\*.*               Sample projects

Installation
------------
1. Unpack archive to installation directory, e.g. C:\Program Files\pdf-tools\
2. For COM Interface: Register the dll, using the following command:
     regsvr32.exe bin\PdfOptimizeAPI.dll
3. For Java: Add Java wrapper POLA.jar to CLASSPATH, add the directory where
   PdfOptimizeAPI.dll resides to PATH
4. Install a license key using the license manager tool (also required for evaluation)


------------------------------------------------------------------------------

UNIX
====

File list
---------
bin/libPdfOptimizeAPI.so  Shared object library (.sl on HP-UX, .dylib on macOS)
bin/licmgr                License manager tool (command line version)
doc/pola.pdf              User's Manual
doc/readme.txt            This file
doc/javadoc.zip           Java API documentation
doc/pdf-license-v*.pdf    License terms for PDF Tools
include/*.h               Include file for C / C++ programs
jar/POLA.jar              Java interface classes archive

Samples are available at product site at www.pdf-tools.com

Installation
------------
1. Unpack the archive in an installation directory, e.g. /usr/pdftools.com/
2. Copy or link the shared object into one of the standard library 
   directories, e.g. ln -s /usr/pdftools.com/bin/libPdfOptimizeAPI.so /usr/lib
3. In case you have not yet installed the GNU shared libraries, get a copy of 
   these from http://www.pdf-tools.com; extract the shared images and copy or
   link them into /usr/lib or /usr/local/lib
4. Install a license key using the license manager tool (also required for evaluation)

On macOS platforms, the shared library must have the extension .jnilib for
use with Java. We suggest that you create a file link for this purpose by
using the following command:
ln libPdfOptimizeAPI.dylib libPdfOptimizeAPI.jnilib
