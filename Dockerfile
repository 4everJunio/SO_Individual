# Folosim o imagine de bază cu JDK
FROM openjdk:11-jre-slim

# Setăm directorul de lucru
WORKDIR /app

# Copiem fișierul JAR în imagine
COPY target/SO_LC.jar /app/SO_LC.jar

# Comanda de rulare a aplicației
CMD ["java", "-jar", "SO_LC.jar"]
