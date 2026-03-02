# Informe de Proyecto: Mini-Ciclos

## CICLO 1: Implementación de la Estructura Base (LIFO)
En este primer ciclo nos enfocamos en que la torre funcione como una estructura de datos tipo pila, donde las operaciones de entrada y salida ocurren únicamente en el tope.

### 1. Requisitos
* Crear una estructura que almacene objetos `Cup` y `Lid`.
* Permitir el apilamiento básico basado en el tamaño del objeto inmediatamente anterior.
* Implementar una salida tipo LIFO (*Last In, First Out*).
* Visualizar los cambios en el canvas en tiempo real.

### 2. Análisis y Diseño (Abstracción)
* **`Tower()`**: Inicializar el contenedor de datos y definir las dimensiones del área de trabajo.
* **`pushCup(int i)` / `pushLid(int i)`**: Abstraen la entrada de nuevos elementos, encargándose de generar el ID y setear el estado inicial del objeto.
* **`popCup()` / `popLid()`**: Gestionan la salida controlada validando que el último elemento sea del tipo deseado.
* **`height()`**: Retornar la altura actual de la construcción.
* **`makeVisible()` / `makeInvisible()`**: Controlar el renderizado de todos los elementos presentes.
* **`ok()`**: Informar si la última operación fue exitosa.

### 3. Construcción (Implementación)
* **`Tower()`**: Se instancia el `ArrayList<StackItem>` y se definen valores por defecto para `width` y `maxHeight`.
* **`pushCup` / `pushLid`**: Utilizan `idCounter` para asegurar piezas únicas. Agregan el objeto al final y llaman a `refreshTower()`.
* **`popCup` / `popLid`**: Acceden al índice `items.size() - 1` y verifican mediante `instanceof` si el objeto es el correcto antes de eliminarlo.
* **`height()`**: En esta fase inicial, retorna el tamaño del `ArrayList`.
* **`makeVisible` / `makeInvisible`**: Utilizan un bucle *for-each* para invocar los métodos de dibujo de cada `StackItem`.

### 4. Pruebas (Objetivos)
* Verificar que al insertar una taza de tamaño 3 sobre una de 5, la de 3 se posicione dentro.
* Validar que el método `pop` no permita eliminar una taza si lo que hay en el tope es una tapa.

---

## CICLO 2: Gestión Dinámica y Reacomodo Físico
El sistema evoluciona para permitir la eliminación de piezas en cualquier posición, obligando a la torre a recalcular su física (gravedad).

### 1. Requisitos
* Poder eliminar cualquier pieza de la torre usando su ID.
* Garantizar que, tras una eliminación, la torre se reajuste físicamente.
* Diferenciar visualmente entre objetos anidados y objetos apoyados en bordes.
* Calcular la altura física real de la torre, no solo la cantidad de piezas.

### 2. Análisis y Diseño (Abstracción)
* **`removeCup(int i)` / `removeLid(int i)`**: Abstraen la búsqueda selectiva para extraer objetos de cualquier índice.
* **`findItem(int id)`**: Método auxiliar para iterar la lista y devolver la referencia del objeto buscado.
* **`refreshTower()`**: Actúa como el motor de física que re-escanea la torre cada vez que la lista cambia.
* **`calculateY()`**: Decide la posición vertical basándose en una búsqueda de contenedor real.

### 3. Construcción (Implementación)
* **`removeCup` / `removeLid`**: Llaman a `findItem(id)`. Si existe, lo eliminan y disparan `refreshTower()`.
* **`refreshTower()`**: Actualiza la variable `topPixel`. Distingue si el objeto es una tapa (+30px) o una taza (+size * 30).
* **`calculateY()`**: Reconstruido con búsqueda inversa para hallar la primera taza donde el objeto quepa y escaneo de altura interna para evitar solapamientos.
* **`height()`**: Implementa la lógica para devolver la altura física real en bloques.

### 4. Pruebas (Objetivos)
* **Prueba de Gravedad**: Eliminar la base y verificar que los objetos superiores caigan correctamente.
* **Prueba de Altura Real**: Validar que tazas anidadas no sumen altura extra a la torre.

---

## CICLO 3: Reordenamiento y Reversión
Manipulación de la posición de los elementos para cambiar la configuración total de la torre y unificación de objetos.

### 1. Requisitos
* Permitir el ordenamiento masivo (`orderTower`) y la inversión (`reverseTower`).
* Garantizar que las piezas selladas se mantengan unidas.
* Recalcular automáticamente la física tras alterar el orden del `ArrayList`.

### 2. Análisis y Diseño (Abstracción)
* **Persistencia de Vínculo**: Un objeto con `partnerId != -1` se considera una unidad.
* **Motor de Re-evaluación**: `refreshTower` mueve los objetos y dispara `updatePartnerships` para validar sellos.

### 3. Construcción (Implementación)
* **`orderTower()`**: Utiliza `Collections.sort` con un comparador de tamaño.
* **`reverseTower()`**: Invierte la lista y recalcula coordenadas $Y$ para evitar objetos flotando.

### 4. Pruebas (Objetivos)
* **Prueba de Bloque**: Verificar que un par sellado (Cup + Lid) se mueva como unidad durante el ordenamiento.
* **Prueba de Estabilidad en Reversa**: Verificar la actualización de `partnerId` tras invertir la torre.

---

## CICLO 4: Consultas y Reportes de Estado
Enfoque en la observabilidad del sistema mediante la lectura de datos.

### 1. Requisitos
* Identificación de sellos (tazas unificadas con tapas).
* Inventario detallado (matriz de base a punta).
* Integridad de datos post-operación.

### 2. Análisis y Diseño (Abstracción)
* **Filtrado de Colecciones**: `lidedCups` filtra objetos de tipo `Cup` con `partnerId` válido.
* **Transformación de Estructuras**: `stackingItems` mapea la lista a una matriz de `Strings`.

### 3. Construcción (Implementación)
* **`lidedCups()`**: Bucle que recolecta IDs basado en la lógica de unificación.
* **`stackingItems()`**: Mapea la lista `items` a una matriz bidimensional.

---

## Autoevaluación del Proyecto

**2. ¿Cuál es el estado actual del proyecto?**
El proyecto presenta un avance consolidado y formal. El Ciclo 1 se ha completado exitosamente, logrando una estructura robusta que integra una escala métrica precisa y un sistema dinámico de colores. El Ciclo 2 se encuentra actualmente en fase de planificación inicial para su próxima ejecución.

**3. Tiempo invertido**
* **Yeray:** 16 horas aproximadamente.
* **Andrés:** 12 horas aproximadamente.

**4. ¿Cuál consideran fue el mayor logro?**
El mayor logro fue completar el ciclo satisfactoriamente y asegurar el correcto funcionamiento de la aplicación de los objetos según los requerimientos establecidos.

**5. ¿Cuál consideran que fue el mayor problema técnico?**
El reto más complejo fue el algoritmo de apilamiento y la unificación de los objetos. Lograr que tazas y tapas se reconocieran y respetaran los espacios físicos requirió una investigación profunda en foros técnicos, tutoriales y apoyo en herramientas de IA para optimizar el manejo de colecciones y coordenadas.

---

## Referencias
* **OpenJDK (JEP 394):** OpenJDK. (2021). *JEP 394: Pattern Matching for instanceof*. https://openjdk.org/jeps/394
* **Tutorial de Oracle (Herencia):** Oracle. (s. f.). *Inheritance*. The Java™ Tutorials. https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
* **Video de YouTube:** Code Riverside. (8 de agosto de 2024). *Interfaces Comparable y Comparator en Java* [Video]. YouTube. https://www.youtube.com/watch?v=BJ4qQQ__QUE