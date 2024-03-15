def calcular_retraso_minimo(vuelos, pistas, matriz_tiempo, matriz_tipo):
    n = len(vuelos)
    m = len(pistas)
    
    # Inicializar matriz de tiempos de aterrizaje
    tiempos_aterrizaje = [[0] * m for _ in range(n)]
    
    # Inicializar matriz de disponibilidad de pistas
    disponibilidad_pistas = [0] * m
    
    # Ordenar vuelos según el tiempo de aterrizaje mínimo
    vuelos_ordenados = sorted(range(n), key=lambda x: min(matriz_tiempo[x]))
    
    # Calcular tiempos de aterrizaje y retrasos
    retraso_total = 0
    for vuelo_idx in vuelos_ordenados:
        tipo_avion = matriz_tipo[vuelo_idx]
        tiempos_vuelo = matriz_tiempo[vuelo_idx]
        pista_disponible = None
        
        # Encontrar la primera pista disponible para el tipo de avión
        for pista_idx, tiempo_pista in enumerate(tiempos_vuelo):
            if disponibilidad_pistas[pista_idx] <= tiempo_pista:
                pista_disponible = pista_idx
                break
        
        # Si no hay pista disponible, calcular el retraso
        if pista_disponible is None:
            retraso = max(disponibilidad_pistas) - min(tiempos_vuelo)
            retraso_total += retraso
        
        # Actualizar disponibilidad de la pista
        disponibilidad_pistas[pista_disponible] = max(disponibilidad_pistas[pista_disponible], min(tiempos_vuelo)) + tipo_avion
        
        # Guardar tiempo de aterrizaje
        tiempos_aterrizaje[vuelo_idx][pista_disponible] = disponibilidad_pistas[pista_disponible]
    
    return retraso_total, tiempos_aterrizaje

# Ejemplo con 12 aviones y 3 pistas
vuelos = list(range(12))
pistas = list(range(3))
matriz_tiempo = [[11, 10, 9], [15, 17, 19], [6, 7, 8], [6, 7, 8], [9, 12, 15], [7, 6, 5],
                           [15, 17, 19], [6, 7, 8], [6, 7, 8], [9, 12, 15], [7, 6, 5], [9, 7, 5]]
matriz_tipo = [0, 1, 0, 0, 2, 0, 1, 0, 0, 2, 0, 1]


retraso_total, tiempos_aterrizaje = calcular_retraso_minimo(vuelos, pistas, matriz_tiempo, matriz_tipo)
print("Retraso total mínimo:", retraso_total)
print("Tiempos de aterrizaje:")
for row in tiempos_aterrizaje:
    print(row)




