# PEV

## Tema 1
Los Algoritmos Evolutivos (AEs) recogen un conjunto de modelos basados en la evolución de los seres vivos. Inspirados en la Naturaleza.

Un modelo de evolución biológica natural que fue propuesto por primera vez por Charles Darwin. El cambio adaptativo de las especies por el principio de la selección natural:
- Favorece la supervivencia y evolución de aquellas especies que están mejor adaptadas a las condiciones de su entorno.
- Aparición de variaciones pequeñas, aparentemente aleatorias.





--- 

**Genoma** es el conjunto total de cromosomas.

**Individuo** (cromosoma) a un solo miembro de una población.

**Población** a un grupo de individuos que pueden interactuar juntos.

**Cromosoma** Cadenas de ADN

**Gen** Sección del ADN, (celula)

**Alelo** Posibles formas del gen

Un AE trabaja con una **POBLACIÓN DE INDIVIDUOS**, que representan soluciones candidatas a un problema (Posibles soluciones). 

Esta población se somete a ciertas **TRANSFORMACIONES** (Construir nuevos individuos a partir de los anteriores) y después a un **proceso de SELECCION**, que favorece a los mejores, según su fitness=aptitud.

Cada ciclo de selección y transformación constituye una **generación**.


## Esquema General
![Esquema general](https://github.com/Danipiza/PEV/assets/98972125/0d53b878-9586-44dd-80b2-083d55dbf1b1)

## Iteración
![Iteracion](https://github.com/Danipiza/PEV/assets/98972125/a15fd933-3b99-4747-90fe-3efc350bd8ba)

Una **población** de individuos coexiste en un determinado entorno con recursos limitados. 

Se aplica **selección** para ir obteniendo los individuos que están **mejor adaptados** al entorno. 

Estos individuos se convierten en los progenitores de nuevos individuos a través de procesos de **mutación y cruce**. 

Los nuevos individuos pasan a competir por su supervivencia. Con el paso del tiempo, esta **selección natural** provoca el incremento en la calidad de los individuos de la población 

Los operadores de mutación y cruce generan **diversidad** facilitando la aparición de **novedad** en la población

### PSEUDO-CÓDIGO
```
i = 0; //i es la generación actual
population[i] = initializePopulation(populationSize); // Inicializar poblacion
evaluatePopulation(population[i]); // Evaluar

while isTerminationConditionMet() == false do
  parents = selectParents(population[i]); // Selección
  population[i+1] = crossover(parents); // Cruce
  population[i+1] = mutate(population[i+1]); // Mutación
  evaluatePopulation(population[i]); // Evaluar
  i++; // Nueva generaciçon
End loop;
```
Vamos a ver formas de mejorar el código, con paralelización y con diferentes algoritmos de selección, cruce, mutación.

---

### Genotipo 
Composición genética de un organismo. **Codificación** (p.ej, binaria) de los parámetros que representan una solución del problema a resolver.

### Fenotipo 
Los rasgos (observables) específicos de un individuo. **Decodificación** del cromosoma: el valor obtenido al pasar de la representación binaria a la usada por la función objetivo

## Tipos de Algoritmos
- Genéticos: trabajan con una **población de cadenas binarias**.
- Estrategias Evolutivas: trabajan con **números reales** que codifican las posibles soluciones de problemas numéricos.
- Programas o algoritmos Evolutivos: los individuos pueden ser **cualquier estructura de datos**.
- Programación Genética: los individuos son programas o autómatas, generalmente representados en **forma de árbol**.

De Jong dice que todas las variantes son realmente instancias concretas de un Sistema Evolutivo General.
Una o más **poblaciones de individuos compitiendo** por recursos limitados. **Poblaciones cambiantes**. El **concepto de fitness** capacidad de un individuo de sobrevivir. El concepto de **herencia modificada**



## Tema 2
