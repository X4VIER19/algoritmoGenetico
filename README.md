# Optimización por Algoritmo Genético - Implementación en Java

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

Este repositorio contiene una implementación interactiva en consola de un **Algoritmo Genético (AG)** desarrollado en Java y gestionado con Apache Maven. El propósito principal del software es resolver un problema de optimización básico (Max-One): evolucionar una población aleatoria de cadenas binarias a lo largo de sucesivas generaciones utilizando operadores heurísticos hasta hallar al individuo óptimo.

---

## 🛠️ Mecánica del Algoritmo

La simulación de evolución biológica sigue el flujo estándar de los algoritmos evolutivos estructurado en los siguientes módulos lógicos:

1. **Población Inicial:** Generación de una matriz de 100 individuos con cromosomas compuestos por 10 genes binarios (0 o 1) con una distribución equiprobable (50%).
2. **Función de Aptitud (Fitness):** Calificación individual basada en la suma total de bits activos (`1`). El valor máximo a alcanzar es un fitness de 10.
3. **Selección por Ranking:** Los individuos se ordenan de mayor a menor aptitud. Las probabilidades se asignan de forma proporcional según su posición en la tabla clasificatoria, garantizando una presión selectiva equilibrada.
4. **Cruzamiento Uniforme:** Se eligen dos progenitores mediante selección de ruleta basada en el ranking y se combinan sus genes de manera independiente (lanzamiento de moneda al 50%) para dar origen a los descendientes.
5. **Mutación Flip-Bit:** Cada gen del nuevo individuo posee una probabilidad fija del 5% (`probMutacion = 0.05`) de sufrir una alteración accidental, invirtiendo su estado lógico (de 0 a 1 o viceversa).
6. **Estrategia de Reemplazo:** Los 20 nuevos hijos generados en cada ciclo sustituyen directamente a los 20 peores individuos de la generación anterior (reemplazo generacional elitista parcial).

---

## 📁 Estructura del Repositorio

La arquitectura del proyecto mantiene la organización estándar para despliegues bajo entornos Maven:

```text
algoritmoGenetico/
│
├── src/main/java/com/x4vier19/algoritmogenetico/
│   └── AlgoritmoGenetico.java  # Clase principal que orquesta el ciclo de vida y los operadores del AG.
│
├── .gitignore                  # Filtros de Git para prevenir el rastreo de binarios locales (/target).
└── pom.xml                     # Descriptor de construcción del ciclo de vida y dependencias de Apache Maven.
```

---

👥 Autores y Desarrollo

Proyecto académico diseñado e implementado por:

    Sergio Xavier Fernandez (X4VIER19) - Desarrollador Principal

    MaryuDmiksa - Co-desarrollador
