# Sistema de gestión de tutorías

Proyecto realizado para modelar un sistema básico de tutorías usando programación orientada a objetos.

La idea principal es separar las responsabilidades para que el sistema no dependa directamente de una base de datos o de un servicio de correo específico.

## Qué permite hacer

- Registrar estudiantes y docentes.
- Publicar horarios disponibles de docentes.
- Crear reservas.
- Confirmar, cancelar y reprogramar reservas.
- Enviar notificaciones cuando cambia una reserva.
- Trabajar con repositorios por medio de interfaces.

## Estructura

```text
src/main/java/edu/uees/tutorias/
├── domain/
├── service/
├── repository/
├── notification/
└── app/
```

## Clases principales

- `User`: clase base para los usuarios.
- `Student`: representa al estudiante que solicita tutorías.
- `Teacher`: administra horarios disponibles.
- `AvailabilitySlot`: representa un horario disponible.
- `TutoringReservation`: contiene la información y estado de una reserva.
- `ReservationService`: coordina creación, confirmación, cancelación y reprogramación.
- `ReservationRepository`: abstracción para guardar reservas.
- `Notifier`: abstracción para enviar notificaciones.

## Decisiones de diseño

Se utilizó herencia solamente entre `User`, `Student` y `Teacher`, porque ambos tipos representan usuarios y comparten información general.

Para los horarios se utilizó composición. Un docente contiene horarios disponibles, pero un horario no es un tipo de docente.

También se decidió que `ReservationService` trabaje con las interfaces `ReservationRepository` y `Notifier`. De esta forma se puede cambiar una implementación en memoria por una base de datos, o cambiar la notificación por consola por correo, sin modificar la lógica principal.

## Principios SOLID

### SRP - Single Responsibility Principle

Cada clase tiene una responsabilidad específica.

Por ejemplo, `Teacher` administra sus horarios, `TutoringReservation` protege el estado de la reserva y `ReservationService` coordina el proceso entre objetos.

### DIP - Dependency Inversion Principle

`ReservationService` no depende directamente de una base de datos o de una clase concreta de correo.

Depende de las interfaces:

- `ReservationRepository`
- `Notifier`

Con esto se reduce el acoplamiento.

## UML

El archivo editable se encuentra en:

```text
docs/modelo-clases.puml
```

Puede abrirse con PlantUML.

## Compilación

Requisito:

- Java 17
- Maven 3.8 o superior

Ejecutar:

```bash
mvn clean compile
```

Para ejecutar las pruebas:

```bash
mvn clean test
```

## Uso de inteligencia artificial

Durante el desarrollo de esta actividad utilicé herramientas de inteligencia artificial como apoyo para redactar documentación, revisar la estructura del proyecto y proponer ejemplos de implementación.

Revisé y adapté el contenido generado, y puedo explicar las clases, relaciones y decisiones presentadas.
