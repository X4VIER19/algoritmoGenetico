/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.x4vier19.algoritmogenetico;

import java.util.Scanner;

/**
 * Clase principal.
 * El objetivo es encontrar al individuo perfecto
 * a traves de seleccion, cruza y mutacion a lo largo de varias generaciones.
 * 
 * @author X4VIER19
 * @author MaryuDmiksa
 */
public class AlgoritmoGenetico {

    static int numIndividuos = 100;
    static int numGenes = 10;
    static double probMutacion = 0.05;
    static int numHijos = 20;

    static int[][] matrizPoblacion = new int[numIndividuos][numGenes];
    static int[] fitnessPoblacion = new int[numIndividuos];
    static double[] probabilidades = new double[numIndividuos];

    /**
     * El motor principal del programa.
     * Pide al usuario cuantas vueltas (epocas) dar y controla todo el ciclo de vida:
     * Evaluar, ordenar, cruzar y reemplazar hasta que se acaben las iteraciones o encontremos al individuo perfecto.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el numero de epocas (iteraciones de evolucion): ");
        int epocas = scanner.nextInt();

        crearPoblacionInicial();
        System.out.println("=== Poblacion Inicial Generada ===");

        // Bucle de evolucion (Epocas)
        for (int epoca = 1; epoca <= epocas; epoca++) {
            calcularFitness();
            organizarPoblacion(); // Ordena los individuos de mejor a peor
            calcularProbabilidades(); // Asigna probabilidades segun la nueva organizacion(rakeo)

            // Imprimimos el avance de manera resumida.
            System.out.println("Epoca " + epoca + " | Mejor fitness de la generacion: " + fitnessPoblacion[0]);

            imprimirPoblacionOrganizada(); //<-- Imprime la poblacion de cada epoca

            // Criterio de parada anticipada: si el mejor tiene todos los genes correctos, terminamos.
            if (fitnessPoblacion[0] == numGenes) {
                System.out.println("Individuo ideal encontrado en la epoca " + epoca);
                break;
            }

            // Realizamos el cruce, mutacion y reemplazo para la siguiente generacion
            cruzamientoYMutacion();
        }

        System.out.println("\n=== Evolucion Finalizada ===");
        // Volvemos a evaluar y ordenar a la ultima generacion para mostrarla
        calcularFitness();
        organizarPoblacion();
        imprimirPoblacionOrganizada();

    }

    /**
     * Crea nuestra primera generacion desde cero.
     * Llena los genes de los 100 individuos tirando una moneda al aire:
     * 50% de probabilidad de ser 1 y 50% de ser 0.
     */
    public static void crearPoblacionInicial() {
        for (int i = 0; i < numIndividuos; i++) {
            for (int j = 0; j < numGenes; j++) {
                matrizPoblacion[i][j] = (Math.random() < 0.5) ? 1 : 0;
            }
        }
    }

    /**
     * Imprime en consola como se ve la poblacion actual sin ordenarla.
     */
    public static void imprimirPoblacion() {
        System.out.println("=== Poblacion ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.print(i + ". [ ");
            for (int j = 0; j < numGenes; j++) {
                System.out.print(matrizPoblacion[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println("");
    }

    /**
     * Le pone calificacion a cada individuo.
     * Suma cuantos '1' tiene en sus genes. Entre mas tenga,
     * mejor es su puntaje (fitness).
     */
    public static void calcularFitness() {
        for (int i = 0; i < numIndividuos; i++) {
            int suma = 0;
            for (int j = 0; j < numGenes; j++) {
                suma += matrizPoblacion[i][j];
            }
            fitnessPoblacion[i] = suma;
        }
    }

    /**
     * Acomoda a todos los individuos de mejor a peor usando el metodo de burbuja.
     * El campeon absoluto siempre quedara en la posicion 0 de nuestras listas.
     */
    public static void organizarPoblacion() {
        for (int i = 0; i < numIndividuos - 1; i++) {
            for (int j = i + 1; j < numIndividuos; j++) {
                if (fitnessPoblacion[j] > fitnessPoblacion[i]) {

                    // Intercambiar fitness
                    int tempFitness = fitnessPoblacion[i];
                    fitnessPoblacion[i] = fitnessPoblacion[j];
                    fitnessPoblacion[j] = tempFitness;

                    // Intercambiar individuo completo
                    int[] tempIndividuo = matrizPoblacion[i];
                    matrizPoblacion[i] = matrizPoblacion[j];
                    matrizPoblacion[j] = tempIndividuo;
                }
            }
        }
    }

    /**
     * Imprime a los individuos ya ordenados junto con la nota que sacaron.
     */
    public static void imprimirPoblacionOrganizada() {
        System.out.println("=== Poblacion Organizada por Fitness ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.print(i + ". [ ");
            for (int j = 0; j < numGenes; j++) {
                System.out.print(matrizPoblacion[i][j] + " ");
            }
            System.out.println("] Fitness: " + fitnessPoblacion[i]);
        }
        System.out.println("");
    }

    /**
     * Determinamos quien se reproduce (Hacemos la seleccion por Ranking).
     * Los que quedaron en los primeros lugares reciben una probabilidad mas
     * grande que los que quedaron al final.
     */
    public static void calcularProbabilidades() {
        int sumaPesos = 0;

        for (int i = 0; i < numIndividuos; i++) {
            sumaPesos += (numIndividuos - i);
        }

        for (int i = 0; i < numIndividuos; i++) {
            int peso = (numIndividuos - i);
            probabilidades[i] = (double) peso / sumaPesos;
        }
    }

    /**
     * Muestra en pantalla el puntaje de cada individuo y que porcentaje de
     * probabilidad tiene de ser elegido como padre.
     */
    public static void imprimirProbabilidades() {
        float sumaProbabilidades = 0.0f;

        System.out.println("=== Probabilidades ===");

        for (int i = 0; i < numIndividuos; i++) {
            System.out.println(i + ". Fitness: " + fitnessPoblacion[i] + ", Probabilidad: " + probabilidades[i]);
            sumaProbabilidades += probabilidades[i];
        }

        System.out.println("Suma de probabilidades: " + sumaProbabilidades);
    }

    /**
     * "Gira la ruleta" para escoger a un papa o mama.
     * Gracias a las probabilidades que calculamos antes, la ruleta se detiene
     * casi siempre en los individuos con mejores calificaciones.
     * 
     * @return El numero de lista (indice) del individuo suertudo que fue seleccionado.
     */
    public static int seleccionarPadre() {
        double aleatorio = Math.random();
        double sumaAcumulada = 0.0;

        for (int i = 0; i < numIndividuos; i++) {
            sumaAcumulada += probabilidades[i];

            if (aleatorio <= sumaAcumulada) {
                return i; // Retorna el indice del individuo seleccionado
            }
        }

        return 0; // Por seguridad, si hay un error de redondeo, retorna el mejor individuo
    }

    /**
     * Crear a la nueva generacion.
     * Se escoge a dos padres, mezcla sus genes uno por uno lanzando una moneda
     * (Cruzamiento Uniforme) y de repente, invierte algun gen por accidente (Mutacion).
     * Al final, los nuevos hijos eliminan a los peores individuos de la poblacion original.
     */
    public static void cruzamientoYMutacion() {

        int[][] nuevosHijos = new int[numHijos][numGenes]; // Arreglo temporal

        // 1. Generar los hijos (la cantidad depende de numHijos)
        for (int i = 0; i < numHijos; i++) {

            int padre1 = seleccionarPadre();
            int padre2 = seleccionarPadre();

            // Evitamos que un padre se cruce consigo mismo
            while (padre1 == padre2) {
                padre2 = seleccionarPadre();
            }

            // Crear los genes del hijo
            for (int j = 0; j < numGenes; j++) {

                // --- CRUZAMIENTO UNIFORME ---
                if (Math.random() < 0.5) {
                    nuevosHijos[i][j] = matrizPoblacion[padre1][j]; // Hereda del padre 1
                } else {
                    nuevosHijos[i][j] = matrizPoblacion[padre2][j]; // Hereda del padre 2
                }

                // --- MUTACION INDEPENDIENTE POR GEN ---
                if (Math.random() < probMutacion) {

                    // Si muta, invertimos el gen (de 0 a 1, o de 1 a 0)
                    nuevosHijos[i][j] = (nuevosHijos[i][j] == 0) ? 1 : 0;
                }
            }
        }

        // Reemplazo: Los peores individuos dejan su lugar a los nuevos hijos.
        int inicioReemplazo = numIndividuos - numHijos;

        for (int i = 0; i < numHijos; i++) {
            matrizPoblacion[inicioReemplazo + i] = nuevosHijos[i];
        }
    }
}