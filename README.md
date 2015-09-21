# Gestión De Torneos #

***
![Screenshot](src/main/resources/fmxl/logo.png)
***

### Glosario ###

- **IntelliJ IDEA**: IDE (Entorno de desarrollo) a usar
- **Git**: Sistema de versionado para desarrollo de software.
- **GitHub**: Aplicación de Git que permite hostear repositorios.
- **MySQL**: Base de datos open source.
- **Hibernate**: Framework de persistencia para Java. Se encarga de hacer el mapeo objeto-relacional (que las entidades de tu sistema que corren en memoria se vinculen con la base de datos).
- **Maven**: Gestor de proyectos Java. Se encarga de armar una estructura según las buenas prácticas, gestionar dependencias (distintos plug-ins y demás que necesitás), etc.
- **JavaFX**: Para armar y lanzar la aplicación Java. Gestiona la interfaz de usuario.

#### Arrancar el proyecto ####

1. Instalar Java SE JDK - [Windows](http://treehouse.github.io/installation-guides/windows/jdk-windows.html) | [Mac](http://treehouse.github.io/installation-guides/mac/jdk-mac.html)
2. Maven
    1. [Instalar](http://mirrors.nxnethosting.com/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.zip) 
    2. Copiar la carpeta a C:/
    3. Agregar ```C:\apache-maven-3.3.3\bin``` a la variable de entorno PATH
3. Git
    1. [Instalar](http://git-scm.com/download/win)
3. IntelliJ IDEA
    1. Instalar - [Windows](http://treehouse.github.io/installation-guides/windows/intellij-idea-win.html) | [Mac](http://treehouse.github.io/installation-guides/mac/intellij-idea-mac.html)
    2. Checkout from Version Control > GitHub
    3. Git Repository URL: ```https://github.com/juanferreras/GestionDeTorneos.git```
    4. Seleccionar directorio y todo y darle a *Clone*
4. JavaFX Scene Builder
    1. [Instalar](http://download.oracle.com/otn-pub/java/javafx_scenebuilder/2.0-b20/javafx_scenebuilder-2_0-windows.msi)
5. MySQL
    1. Instalar - [Windows](http://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-web-community-5.6.26.0.msi) | [Mac](http://dev.mysql.com/get/Downloads/MySQL-5.6/mysql-5.6.26-osx10.9-x86_64.dmg)
    2. Seleccionar la versión de desarrollador y dejar que instale todo.
    3. Ponerle de password al usuario *root* ```grupo5b```
    4. Abrir el Workbench, seleccionar la Local Instance y loguearse con las credenciales.
    5. Hacer click en el cuarto icono (_Create a new schema[..]_) y crear un nuevo Schema llamado ```gestiondetorneos```
