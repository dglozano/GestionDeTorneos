# Gestión De Torneos #

***
![Screenshot](src/main/resources/fmxl/logo.png)
***

#### Glosario ####

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
    4. Seleccionar directorio local donde guardarlo y todo y darle a *Clone*
    5. Tocar *Yes* y esperar que cargue las dependencias
4. JavaFX Scene Builder
    1. Instalar - [Windows](http://www.oracle.com/technetwork/java/javase/downloads/javafxscenebuilder-1x-archive-2199384.html#javafx-scenebuilder-2.0-oth-JPR) | [Mac](http://download.oracle.com/otn-pub/java/javafx_scenebuilder/2.0-b20/javafx_scenebuilder-2_0-macosx-universal.dmg)
5. MySQL
    1. Instalar - [Windows](http://dev.mysql.com/get/Downloads/MySQLInstaller/mysql-installer-web-community-5.6.26.0.msi) | [Mac: MySQL Server](http://dev.mysql.com/get/Downloads/MySQL-5.6/mysql-5.6.26-osx10.9-x86_64.dmg) & [Mac: MySQL Workbench](https://dev.mysql.com/downloads/file.php?id=457796)
    2. Seleccionar la versión de desarrollador y dejar que instale todo.
    3. Tocar el (+) y crear nueva conexión.
        1. Hostname: ```localhost```
        2. Port: ```3306```
        3. Ponerle de password al usuario *root* ```grupo5b```
    5. Seleccionar la Local Instance y loguearse con las credenciales.
    6. Hacer click en el cuarto icono (_Create a new schema[..]_) y crear un nuevo Schema llamado ```gestiondetorneos```

#### Git desde IntelliJ Idea ####

Una vez que en tu versión local tenés una nueva funcionalidad estable que *no rompe*, con ```CTRL+K``` abrís la ventana de Commit. Ahí en Commit Message hay que escribir que funcionalidad estás agregando, y tocar **Commit & Push**. Te va a abrir otra ventana donde hay que confirmar tocando en **Push**. Abajo de todo el IDE te notifica si salió bien o no.
Es posible que te tire que el Push fue rechazado, debido a que algunos archivos en el servidor son distintos a los que tenés vos en tu versión local. Es decir, mientras vos hacías una funcionalidad, otro ya subió cambios que vos todavía no tomaste. En este caso hay que tocar el botón **Merge**; en la mayoría de los casos la integración entre las versiones es automática pero puede ser que te baje los cambios a tu versión local y te haga decidir a vos, en cuyo caso te va a mostrar los archivos afectados, y vos vas a editarlos y nuevamente hacer un nuevo Commit & Push.
Para tomar nuevos cambios en el servidor tocás ```CTRL+T``` (Update Project) y le das a OK.