# Lander-Game-ServletWithJava
Servlet with Java, Lander Game.

## README

El funcionamiento es el siguiente: al arrancar el programa se inicia el "login.jsp" que realiza un redireccionamiento al Servlet "IndexServlet" ejecutando el método "GET".

El método **"GET"** realiza lo siguiente:

1. Realiza una comprobación obteniendo la lista de cookies asociadas a la página web. En caso de que existan las cookies:
  * Partiendo de una lista de usuarios, la recorremos.
  * En esa lista, realizamos recorremos la lista de cookies.
  * Si la cookie asociada al nombre de usuario y a la contraseña coinciden con ese usuario, entonces realizará el login.
  * Llamámos al método **response.sendRedirect()** redireccionando a la página principal del usuario, donde podrá consultar diversas opciones.

2. En caso de que no existan las cookies, redirigimos a la página principal de LOGIN/REGISTER, ya que no habrá encontrado coincidencias y el usuario deberá elegir entre esas opciones.

Si el usuario no se encuentra registrado, deberá hacerlo para poder acceder al juego. Cuando el botón **REGISTER** es pulsado, un formulario es mostrado para su registro. Este formulario consta de:
  * Nickname
  * Password
  * Password verification
  * Email
   
**Si el usuario introduce la segunda contraseña de forma errónea, la página mostrará un mensaje de error y recargará una web con el mensaje informativo, haciendo posible volver a intentarlo. De igual manera, un usuario deberá introducir un email verídico (cumpliendo el estándar HTML5, PATTERN).**

Una vez registrado el usuario, se mandan los datos al servlet "RegisterServlet" mediante el método "POST".

El método **"POST"** realiza lo siguiente:

1. Recibe las contraseñas del usuario y las encripta a la encriptación **SHA1**.
2. En caso de que ambas contraseñas coincidan, creará el usuario en la base de datos pasando un objeto de tipo **uGAME**.
3. Una vez completado el registro, redirigimos a una web que informará al usuario de que el registro se ha realizado de manera correcta.

¡YA PUEDE JUGAR!

Si el usuario se ha registrado, automáticamente se le redirigirá a la página principal de opciones (no es el juego) donde el usuario antes de realizar una partida podrá hacer lo siguiente:

  * Jugar una partida.
  * Ver las puntuaciones, tanto **GLOBALES** como **PERSONALES**. Las globales se delimitan en dos:
    * TOP 3
    * TOP 10
  * Consultar quién ha realizado la página web o juego.
  * Salir de la sesión (LOGOUT).

En caso de que el usuario quiera jugar, comienza la partida al pulsar 'PLAY'. Una vez que el usuario ha finalizado su partida se le ofrecen 3 opciones:

  * Jugar de nuevo, donde se refrescará la página.
  * Salir del juego y volver a la pantalla principal de usuario.
  * Mirar las puntuaciones globales.
  
Cuando el usuario finaliza el juego, se crea un registro en la base de datos. Para ello, mediante AJAX se envía la información como FECHA o VELOCIDAD al servlet "IndexServlet" mediante el método "POST".

El método **"POST"** realiza lo siguiente:

  1. Se recogen los parámetros de JavaScript, que son la fecha inicial, fecha final y velocidad.
  2. Se parsean las fechas a tipo **DATE**.
  3. Se añade el registro a la base de datos.

Entonces, el usuario ya podrá consultar las puntuaciones (al menos las personales) cuando haya realizado mínimo una partida.

Finalmente, si el usuario decide salir de la sesión (**LOGOUT**), se redirecciona a una web que informa al usuario de que ha salido de la sesión. Se hace mediante la redirección a "LogoutServlet", que eliminará todos los datos de cookies asociados a ese usuario mediante el método "GET".


IMPORTANTE
**Cuando se despliega la aplicación por primera vez, la base de datos es creada (es decir, las tablas correspondientes que manejarán los datos). De esto se encarga el PERSISTANCE en Java, dónde se puede elegir si crearlas, o eliminarlas.**

**Los services de JPA se puede generar automáticamente, suponiendo de esta manera un ahorro de tiempo y trabajo muy grande.**
