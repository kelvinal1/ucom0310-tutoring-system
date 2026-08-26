# Sistema de gestión de tutorías

Este proyecto lo hice para la materia de Diseño de Software. La idea fue modelar un sistema básico de tutorías aplicando orientación a objetos, UML, cohesión, bajo acoplamiento y algunos principios SOLID, pero sin volverlo innecesariamente complicado.

## Qué hace

- Maneja estudiantes y docentes.
- Permite registrar horarios disponibles.
- Crea reservas de tutoría.
- Confirma, cancela y reprograma reservas.
- Notifica cuando pasa algo importante con una reserva.
- Guarda la información por medio de una abstracción, sin amarrar la lógica a una tecnología específica.

## Estructura del proyecto

```text
src/main/java/edu/uees/tutorias/
├── domain/
├── service/
├── repository/
├── notification/
└── app/
```

## Clases principales

- `Usuario`: clase base con los datos comunes de un usuario.
- `Estudiante`: representa al estudiante que solicita la tutoría.
- `Docente`: administra sus horarios disponibles.
- `HorarioDisponible`: representa un horario que puede reservarse.
- `ReservaTutoria`: relaciona estudiante, docente, horario y estado de la reserva.
- `ServicioReservas`: coordina la creación, confirmación, cancelación y reprogramación.
- `RepositorioReservas`: abstracción para guardar reservas.
- `Notificador`: abstracción para enviar avisos.

## Decisiones de diseño

Usé herencia solo entre `Usuario`, `Estudiante` y `Docente` porque ahí sí tiene sentido. Tanto estudiante como docente son tipos de usuario y comparten datos básicos.

Para los horarios usé composición. Un `Docente` tiene horarios disponibles, pero un horario no es un tipo de docente, así que herencia ahí no tenía sentido.

También separé la coordinación de la lógica concreta. `ServicioReservas` trabaja con `RepositorioReservas` y `Notificador`, no con implementaciones amarradas. Eso hace más fácil cambiar la persistencia o la forma de notificar sin dañar la lógica principal.

## Cohesión y acoplamiento

`Docente` se encarga de sus horarios y nada más. `ReservaTutoria` se encarga de cuidar el estado de la reserva y sus cambios. `ServicioReservas` coordina el proceso completo, pero no guarda datos directamente ni manda notificaciones por su cuenta.

Eso ayuda a que cada clase tenga una responsabilidad clara y que el sistema no quede demasiado acoplado. Si más adelante se quisiera pasar de memoria a una base de datos real, o cambiar la notificación por consola a correo, el impacto fuerte estaría en nuevas implementaciones y no en toda la lógica.

## Principios SOLID aplicados

### SRP

Cada clase tiene una responsabilidad concreta. Por ejemplo, `Docente` administra horarios, `ReservaTutoria` controla el estado de la reserva y `ServicioReservas` coordina los casos de uso.

### DIP

`ServicioReservas` no depende directamente de una clase concreta para guardar datos ni para notificar. Depende de `RepositorioReservas` y `Notificador`, que son abstracciones.

### OCP

El diseño también permite crecer sin tener que modificar la coordinación principal. Por ejemplo, se podría crear otro notificador o otro repositorio manteniendo estable `ServicioReservas`.

## UML

El diagrama editable está en:

```text
docs/modelo-clases.puml
```

Ese archivo se puede abrir con PlantUML para verlo como gráfico. El diagrama muestra la herencia entre usuarios, la composición entre `Docente` y `HorarioDisponible`, las asociaciones de `ReservaTutoria` y las dependencias de `ServicioReservas` hacia las abstracciones.

## Requisitos

- Java 17
- Maven 3.8 o superior

## Compilación y pruebas

Para compilar:

```bash
mvn clean compile
```

Para ejecutar pruebas:

```bash
mvn clean test
```

Las pruebas cubren creación, confirmación, cancelación y reprogramación de reservas, además de una validación de reserva cancelada.

## Uso de inteligencia artificial

Durante el desarrollo utilicé inteligencia artificial como apoyo para revisar la estructura, ordenar ideas y mejorar la documentación. De todos modos, revisé el contenido, adapté lo necesario y puedo explicar las decisiones, clases y relaciones que aparecen en el proyecto.
