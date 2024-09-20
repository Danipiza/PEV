*[ENGLISH](https://github.com/Danipiza/TFG/blob/main/README_ENG.md) ∙ [ESPAÑOL](README.md)* <img align="right" src="https://visitor-badge.laobi.icu/badge?page_id=danipiza.TFG" />

<hr>

<h1 align="center">
    PEV: Programación EVolutiva
</h1>

**Programacion Evolutiva,** asignatura del Departamento de Sistemas Informaticos y ́Computación, perteneciente al bloque de asignaturas optativas generales grado de ingeniería informática en la Universidad Complutense de Madrid.

En esta asignatura vemos diferentes formas de resolver problemas mediante algoritmos evolutivos. Creamos nuestras propias clases para implementar los metodos de Evaluacion, Selección, Cruce y Mutacion, así como los Individuos con sus respectivas Representaciónes (array binarios o reales, arboles o gramáticas).

---


## [PR1.](https://github.com/Danipiza/PEV/files/15047439/PR1.pdf) Introducción

En esta práctica vamos a buscar maximizar o minimizar una función matemática. 

[Memoria](https://github.com/Danipiza/PEV/files/15047587/Memoria.pdf)

### Representación de los individuos 
La representaion es simple, con array binarios y reales para la ultima función, las tres primeras funciones tiene 2 valores por individuo, en las ultimas dos se puede fija el numero de variables. También se puede fijar la precisión, asi aumentando el tamaño de los arrays binarios. Los valores oscilan en intervalos, diferentes para cada función.

### Funciones

- Funcion 1: Calibración y prueba

- Funcion 2: Mishra Bird

- Funcion 3: Holder table
  
- Funcion 4: Michalewicz con codificación binaria

- Funcion 5: Michalewicz con codificación real

Se puede modificar los valores de entrada en el JPanel izquierdo. Se ejecuta pulsando el boton de la parte inferior.

![TorneoDeterminista](https://github.com/Danipiza/PEV/assets/98972125/a3331053-55ed-4d3d-8c4e-9e23afb9a27b)


---

## [PR2.](https://github.com/Danipiza/PEV/files/15047437/PR2.pdf) Control de tráfico aéreo 

El problema que vamos a optimizar consiste en un Aeropuerto con n aviones y m pistas (n>m).

Tenemos que asignar a los n aviones una pista, y reducir el tiempo de espera en el aire de cada avión. Es un problema de **optimización combinatoria**, por lo que el orden de los aviones importa.

Como datos de entrada tenemos:

- TEL: Matriz de tiempos estimados de llegada de cada avión (n columnas) a cada una de las pistas (m filas). Estos datos se encuentran en TELX.txt (siendo X: el tipo de Aeropuerto)
- SEP: Matriz de tiempos de separación. Cada tiempo depende del tipo de avión que llega a la pista y el tipo de avión que se va para dejarla libre.. Hay 3 tipos de aviones:
  - Pesado (W)
  - Grande (G)
  - Pequeño (P)
- vuelos: array con los identificadores y tipo de cada avión. Estos datos se encuentran en vuelosX.txt (siendo X: el tipo de Aeropuerto)

[Memoria](https://github.com/Danipiza/PEV/files/15047586/Memoria.pdf)

### Representación de los individuos 
En esta práctica es con números reales que no se repiten, puesto que cada uno es un vuelo.

### Funciones
El fitness de cada individuo siempre se calcula de la siguiente forma.

```
fitness=0;
for each avion do
    // Calculamos TLA para cada pista
    for each pista do
        TLA = maximo(TLA(vuelo_anterior) + SEP[vuelo_anterior][vuelo_actual], TEL);
    // Se asigna el vuelo actual a la pista con minimo TLA calculado
    fitness+=(menor_TLA-menor_TEL)^2
    // menor_TEL: menor TEL de ese vuelo con todas las pistas
```

- Aeropuerto 1: vuelos=12, pistas=3

- Aeropuerto 2: vuelos=25, pistas=5

- Aeropuerto 3: vuelos=100, pistas=10

Se puede modificar los valores de entrada en el JPanel izquierdo. Se ejecuta pulsando el boton de la parte inferior.

![EjecucionAer1](https://github.com/Danipiza/PEV/assets/98972125/bf338add-de6b-4c78-a5bd-0741fc4fad76)

---

## [PR3.](https://github.com/Danipiza/PEV/files/15047438/PR3.pdf) Programación genética/Gramáticas evolutivas

El problema que vamos a optimizar es la poda de césped en una matriz toroidal de N filas por M columnas. Como la matriz es toroidal, no se puede salir del tablero. Si avanzas por la parte inferior, y sales de su límite, vuelves por la parte superior, es decir, cada fila y columna se puede entender por vectores de enteros modulo N y M, respectivamente.

El agente (un corta césped) empieza en la celda (4, 4). Tenemos que encontrar una serie de operaciones que se repite hasta una condición de finalización, que maximice el máximo número de celdas podadas en la 
 atriz. Para ello tenemos una serie de operadores (más adelante hablaremos de los opcionales):
- Adelante: Avanza un paso y poda la casilla destino.
- Izquierda: Gira 90º a la izquierda. (No poda la casilla actual)
- Salta: Salta hasta un punto determinado, podando la casilla destino.

El agente termina su trabajo una vez ha realizado 100 operaciones (ticks). Este número se puede modificar opcionalmente.

Terminales:
- AVANZA: Avanza una casilla en la dirección actual, y poda la celda destino. Devuelve (0, 0)
- IZQUIERDA: Gira el cortacésped 90º a la izquierda. Devuelve (0, 0)
- CONSTANTE(X, Y): Genera dos números aleatorios. Devuelve (X, Y)

Funciones:
- SUMA (E1, E2): Ejecuta sus dos argumentos secuencialmente. Devuelve la suma vectorial en modulo N y M, de los resultados de sus dos variables.
- PROGN(E1, E2): Ejecuta sus dos argumentos secuencialmente. Devuelve el resultado de su segunda expresión.
- SALTA(E): Salta E.x y E.y casillas respecto a su dirección actual y poda la celda destino. Devuelve las coordenadas del salto.
  
Vamos a resolver este problema con:

### Programación Genética (Parte obligatoria):
Cada individuo es un Árbol, pero para optimizar la implementación, además de almacenar el árbol entero con sus respectivos punteros, almacenamos una lista de strings. Así sabemos con coste constante O(1) el ciclo de operaciones, mejorando bastante el tiempo de ejecución de la función de evaluación.

### Parte opcional: Gramáticas Evolutivas.
Esta vez los individuos son vectores de enteros generados aleatoriamente con valores de [0-255]. Cada entero se llama codón, y sirve para saber que nodo del árbol, así se puede representar el árbol con el vector. También aplicamos la misma técnica de almacenar las operaciones como en la parte anterior. 

Más adelante explicaremos en mayor profundidad las características de estasimplementaciones.

[Memoria](https://github.com/Danipiza/PEV/files/15047588/Memoria.pdf)

### Representación de los individuos 
En esta práctica es con números reales que no se repiten, puesto que cada uno es un vuelo.

### Funciones
El fitness se calcula al ejecutar las operaciones de cada individuo en la matriz, hasta cierto número de ticks (este número se puede modificar), al podar una celda suma 1 a su fitness y marca la celda de la matriz como podada.

### Parámetros modificables
- Número de filas
- Número de columnas
- Ticks
- Control de bloationg
- Boton Opcional
  - Añadir obstáculos
  - Operaciones nuevas
  - Reinicios

Progamacion Genética
- Inicializacion: Completa, Creciente o Ramped & Half
- Profundidad de los árboles
- Método de Mutación

Grmáticas Evolutivas
- Tamaño del cromosoma (Número de codones)

Cada parte tiene su propio botón de ejecución. El de la parte inferior es para ver la animación del mejor individuo y ver como corta las celdas. Se puede marcar el tiempo que tarda en animar cada operación-
![readme](https://github.com/Danipiza/PEV/assets/98972125/07deaebc-6383-4ff0-b9a8-56c151e8235a)

