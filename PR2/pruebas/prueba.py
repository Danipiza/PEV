"""import random

# Función para generar un número aleatorio entre 0 y 23
def generar_valor():
    return random.randint(0, 23)

# Generar la matriz con 10 filas y 100 columnas
matriz = [[generar_valor() for _ in range(100)] for _ in range(10)]

# Abrir el archivo vuelo3.txt en modo escritura
with open("vuelo3.txt", "w") as file:
    # Imprimir la matriz en el formato requerido y escribirlo en el archivo
    for fila in matriz:
        print("\t".join(map(str, fila)), file=file)
"""

"""
import random

# Lista de identificadores de vuelo
identificadores = ["UA", "NW", "AA"]
# Lista de tipos de vuelo
tipos_vuelo = ["W", "G", "P"]

# Función para generar un identificador de vuelo aleatorio
def generar_identificador():
    return random.choice(identificadores) + str(random.randint(100, 999))

# Función para generar una fila de salida
def generar_fila():
    return f"{generar_identificador()}\t{random.choice(tipos_vuelo)}\n"

# Generar 100 filas y escribir en el archivo
with open("vuelos3.txt", "w") as archivo:
    for _ in range(100):
        fila = generar_fila()
        archivo.write(fila)

print("Archivo 'vuelos3.txt' generado correctamente.")"""
def verificar_palabras_iniciales_unicas(archivo):
    palabras_iniciales = set()
    duplicados = set()

    with open(archivo, 'r') as f:
        for linea in f:
            palabra_inicial = linea.strip().split()[0]
            if palabra_inicial in palabras_iniciales:
                duplicados.add(palabra_inicial)
            else:
                palabras_iniciales.add(palabra_inicial)

    if duplicados:
        print("Se encontraron palabras iniciales duplicadas en el archivo:")
        for palabra in duplicados:
            print(palabra)
    else:
        print("Todas las palabras iniciales son únicas en el archivo.")


archivo_txt = "vuelos3.txt"  # Cambiar por el nombre de tu archivo
verificar_palabras_iniciales_unicas(archivo_txt)
