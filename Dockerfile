# --- ETAPA 1: CONSTRUCCIÓN (MAVEN) ---
# Usamos una imagen que tiene Maven y Java 17 listos
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Copiamos todo tu código al contenedor
COPY . .

# Ejecutamos el comando de Maven para crear el .war (saltando los tests para ir rápido)
RUN mvn clean package -DskipTests

# --- ETAPA 2: EJECUCIÓN (TOMCAT) ---
# Usamos Tomcat 10 (compatible con Jakarta EE)
FROM tomcat:10.1-jdk17

# Borramos las apps por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiamos el .war que generó Maven en la etapa anterior a la carpeta de Tomcat
# NOTA: Maven suele crear el archivo en la carpeta 'target'. 
# Lo renombramos a ROOT.war para que sea la app principal via web.
COPY --from=build target/*.war /usr/local/tomcat/webapps/ROOT.war

# Exponemos el puerto 8080
EXPOSE 8080

# Arrancamos Tomcat
CMD ["catalina.sh", "run"]