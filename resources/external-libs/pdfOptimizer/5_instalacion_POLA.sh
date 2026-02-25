#Windows version
mvn install:install-file -Dfile=./libs/5/Windows/jar/POLA.jar -DgroupId=com.pdfTools -DartifactId=pDFOptimizer-windows -Dversion=4.8 -Dpackaging=jar
#Linux version
mvn install:install-file -Dfile=./libs/5/Linux/jar/POLA.jar -DgroupId=com.pdfTools -DartifactId=pDFOptimizer-linux -Dversion=4.8 -Dpackaging=jar