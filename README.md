# Trabajo-Practico-Integrador---Programacion-2

# Food Store - Sistema de Gestión de Pedidos (Consola)

## Aplicación de consola desarrollada en Java orientada a la gestión integral de un negocio de comidas. Implementa Programación Orientada a Objetos (POO) y una arquitectura multicapas conectada a una base de datos relacional mediante JDBC.

## Características (Features)

* **Gestión de Entidades (CRUD):** Permite listar, crear, editar y eliminar de forma lógica (soft-delete) Categorías, Productos, Usuarios y Pedidos.
* **Arquitectura en Capas:** Código modularizado separando el modelo de dominio (`Entities`), la persistencia (`DAO`), la lógica de negocio (`Service`) y la vista (`Main/Menu`).
* **Transacciones Seguras:** Implementación de `commit` y `rollback` en JDBC para garantizar la consistencia de los datos al guardar pedidos y sus detalles correspondientes.
* **Validaciones de Negocio:** Controles estrictos de stock, correos únicos, precios válidos y prevención de borrado físico.
* **Interfaz de Consola Interactiva:** Menús dinámicos con captura de errores para una navegación fluida sin interrupciones por excepciones no controladas.

## Requisitos previos (Prerequisites)

Antes de instalar y ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

* **Java Development Kit (JDK):** Versión 21.
* **Base de Datos:** MySQL Server (recomendado).
* **Driver JDBC:** Conector oficial de MySQL para Java (`mysql-connector-j`).
* **IDE o Terminal:** IntelliJ IDEA, Eclipse, NetBeans o cualquier entorno compatible con Java.
* **Git:** Para clonar el repositorio.

## Instalación

Sigue estos pasos exactos para inicializar el proyecto desde cero:

**1. Clonar el repositorio**
Abre tu terminal y ejecuta:
`git clone <https://github.com/FacundoChacon/Trabajo-Practico-Integrador---Programacion-2/tree/main>`

**2. Preparar la Base de Datos**
Abre tu gestor de MySQL (como MySQL Workbench o phpMyAdmin) y ejecuta los scripts ubicados en la raíz del proyecto en el siguiente orden:
* Ejecuta `INICIADOR_DE_DB_FOODSTORE.sql` para crear la base de datos (`food_store_db`).
* Ejecuta `CREACION_DE_TABLAS.sql` para generar la estructura de tablas relacionales.
* Ejecuta `INSERSION_DE_REGISTROS.sql` para cargar los datos de prueba iniciales (Categorías y Usuarios).

**3. Configurar la Conexión**
En el proyecto Java, dirígete al archivo `src/config/ConexionDB.java`. Asegúrate de que las credenciales (`user` y `pass`) coincidan con las de tu entorno local de MySQL:
```java
String url = "";
String user = "root"; 
String pass = ""; 
