*[ENGLISH](https://github.com/Danipiza/PEV/blob/main/README_ENG.md) ∙ [ESPAÑOL](README.md)* <img align="right" src="https://visitor-badge.laobi.icu/badge?page_id=danipiza.PEV" />

<hr>

<h1 align="center">
    PEV: Evolutionary Programming </h1>
</h1>


**Evolutionary Programming,** subject from the Department of Computer Systems and Computing, part of the general optatives courses for the Computer Science degree at the Complutense University of Madrid.

In this subject, we explore various ways to solve problems using evolutionary algorithms. We create our own classes to implement the methods of Evaluation, Selection, Crossover, and Mutation, as well as the Individuals with their respective Representations (binary or real arrays, trees, or grammars).



---


## [PR1.](https://github.com/Danipiza/PEV/files/15047439/PR1.pdf) Introduction

In this practice, we aim to maximize or minimize a mathematical function.

[Paper](https://github.com/Danipiza/PEV/files/15047587/Memoria.pdf)

### Representation of the individuals
The representation is simple, with binary and real arrays for the last function. The first three functions have 2 values per individual, while in the last two, the number of variables can be fixed. The precision can also be set, thus increasing the size of the binary arrays. The values fluctuate within intervals, different for each function.

### Funciones

- Funcion 1: Calibration and testing

- Funcion 2: Mishra Bird

- Funcion 3: Holder table
  
- Funcion 4: Michalewicz with binary codification 

- Funcion 5: Michalewicz con real codification

The input values can be modified in the left JPanel. Execution is done by pressing the button at the bottom.

![TorneoDeterminista](https://github.com/Danipiza/PEV/assets/98972125/a3331053-55ed-4d3d-8c4e-9e23afb9a27b)


---

## [PR2.](https://github.com/Danipiza/PEV/files/15047437/PR2.pdf) Air Traffic Control

The optimization problem involves an Airport with n airplanes and m airstrips (n>m).

We need to assign an airstrip to each of the n airplanes, reducing the waiting time in the air for each plane. This is a **combinatorial optimization** problem, where the order of the planes matters.

The input data consists of:

- TEL: A matrix of estimated landing times for each airplane (n columns) on each airstrip (m rows). This data is found in TELX.txt (where X represents the Airport type).
- SEP: A separation times matrix. Each time depends on the type of airplane arriving at the airstrip and the type of airplane leaving it free. There are 3 types of planes:
  - Heavy (W)
  - Large (G)
  - Small (P)
- vuelos: An array with the identifiers and type of each airplane. This data is found in flightsX.txt (where X represents the Airport type).

[Paper](https://github.com/Danipiza/PEV/files/15047586/Memoria.pdf)

### Representation of the individuals
In this practice, the representation is done with real numbers that do not repeat, as each one corresponds to a flight.

### Functions
The fitness of each individual is always calculated as follows.

```java
fitness=0;
for each avion do
    // Calculate TLA for each runway
    for each pista do
        TLA = maximo(TLA(vuelo_anterior) + SEP[vuelo_anterior][vuelo_actual], TEL);
    // Assign the current flight to the runway with the minimum TLA calculated
    fitness+=(menor_TLA-menor_TEL)^2
    // menor_TEL: smallest TEL for that flight with all runways
```

- Airport 1: flights=12, airstrips=3

- Airport 2: flights=25, airstrips=5

- Airport 3: flights=100, airstrips=10

The input values can be modified in the left JPanel. Execution is done by pressing the button at the bottom.

![EjecucionAer1](https://github.com/Danipiza/PEV/assets/98972125/bf338add-de6b-4c78-a5bd-0741fc4fad76)

---

## [PR3.](https://github.com/Danipiza/PEV/files/15047438/PR3.pdf) Programación genética/Gramáticas evolutivas

The optimization problem is grass cutting in a toroidal matrix of N rows by M columns. Since the matrix is toroidal, you cannot go off the board. If you move off the bottom and exit its limit, you re-enter from the top, meaning each row and column can be understood as integer vectors modulo N and M, respectively.

The agent (a lawnmower) starts at cell (4, 4). We need to find a series of operations that repeat until a termination condition, maximizing the number of mowed cells in the matrix. For this, we have a series of operators (we'll discuss the optional ones later):
- Forward: Moves one step and mows the destination cell.
- Left: Turns 90º to the left (does not mow the current cell).
- Jump: Jumps to a determined point, mowing the destination cell.

The agent finishes its task after performing 100 operations (ticks). This number can be optionally modified.

Terminals:
- FORWARD: Moves one cell in the current direction and mows the destination cell. Returns (0, 0)
- LEFT: Turns the lawnmower 90º to the left. Returns (0, 0)
- CONSTANT(X, Y): Generates two random numbers. Returns (X, Y)

Functions:
- SUM (E1, E2): Executes its two arguments sequentially. Returns the vector sum modulo N and M of its two variables.
- PROGN(E1, E2): Executes its two arguments sequentially. Returns the result of its second expression.
- JUMP(E): Jumps E.x and E.y cells in its current direction and mows the destination cell. Returns the coordinates of the jump.  

We will solve this problem with:

### Genetic Programming (Mandatory part):
Each individual is a tree, but to optimize the implementation, in addition to storing the entire tree with its respective pointers, we store a list of strings. This way, we know the operation cycle in constant O(1) time, significantly improving the evaluation function’s execution time.

### Optional part: Evolutionary Grammars.
This time, individuals are vectors of integers randomly generated with values between [0-255]. Each integer is called a codon and is used to determine which node of the tree it represents. Thus, the tree can be represented by the vector. We also apply the same technique of storing the operations as in the previous part.

We will explain these implementations in greater depth later.

[Paper](https://github.com/Danipiza/PEV/files/15047588/Memoria.pdf)

### Representation of the individuals
In this practice, the representation is done with real numbers that do not repeat, as each one corresponds to a flight.

### Functions
Fitness is calculated by executing the operations of each individual on the matrix up to a certain number of ticks (this number can be modified). Mowing a cell adds 1 to its fitness and marks the matrix cell as mowed.

### Modifiable Parameters

- Number of rows
- Number of columns
- Ticks
- Bloationg control
- Optional Button
	- Add obstacles
	- New operations
	- Resets


Genetic Programming
- Initialization: Full, Grow, or Ramped & Half
- Tree depth
- Mutation method

Evolutionary Grammars
- Chromosome size (Number of codons)

Each part has its own execution button. The bottom one is for viewing the animation of the best individual and seeing how it cuts the cells. You can set the time it takes to animate each operation.
![readme](https://github.com/Danipiza/PEV/assets/98972125/07deaebc-6383-4ff0-b9a8-56c151e8235a)

