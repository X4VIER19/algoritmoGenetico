/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.x4vier19.algoritmogenetico;

/**
 * @author MaryuDmiksa
 * @author XAV
 */

// https://prod.liveshare.vsengsaas.visualstudio.com/join?CA9B5AFBC6239D62A4A22674735E9BEBDB9C

import java.util.Scanner;

public class AlgoritmoGenetico {

    static int numIndividuos = 100;
    static int numGenes = 10;
    static double probMutacion = 0.05;
    static int numHijos = 20;

    static int[][] matrizPoblacion = new int[numIndividuos][numGenes];
    static int[] fitnessPoblacion = new int[numIndividuos];
    static double[] probabilidades = new double[numIndividuos];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el número de épocas (iteraciones de evolución): ");
        int epocas = scanner.nextInt();

        crearPoblacionInicial();
        System.out.println("=== Población Inicial Generada ===");

        // Bucle de evolución (Épocas)
        for (int epoca = 1; epoca <= epocas; epoca++) {
            calcularFitness(); 
            organizarPoblacion(); // Ordena los individuos de mejor a peor
            calcularProbabilidades(); // Asigna probabilidades según la nueva organización

            // Imprimimos el avance de manera resumida para no saturar la pantalla
            System.out.println("Época " + epoca + " | Mejor fitness de la generación: " + fitnessPoblacion[0]);
            
            
            imprimirPoblacionOrganizada();//<-- Imprime la población de cada epoca


            // Criterio de parada anticipada: si el mejor tiene todos los genes correctos, terminamos.
            if (fitnessPoblacion[0] == numGenes) {
                System.out.println("Individuo ideal encontrado en la epoca " + epoca + "!");
                break;
            }

            // Realizamos el cruce, mutación y reemplazo para la siguiente generación
            cruzamientoYMutacion();
        }

        System.out.println("\n=== Evolución Finalizada ===");
        // Volvemos a evaluar y ordenar a la última generación para mostrarla correctamente
        calcularFitness();
        organizarPoblacion();
        imprimirPoblacionOrganizada();

        scanner.close();
    }

    public static void crearPoblacionInicial() {
        for (int i = 0; i < numIndividuos; i++) {
            for (int j = 0; j < numGenes; j++) {
                matrizPoblacion[i][j] = (Math.random() < 0.5) ? 1 : 0;
            }
        }
    }

    public static void imprimirPoblacion() {
        System.out.println("=== Población ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.print(i + ". [ ");
            for (int j = 0; j < numGenes; j++) {
                System.out.print(matrizPoblacion[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println("");
    }

    public static void calcularFitness() {
        for (int i = 0; i < numIndividuos; i++) {
            int suma = 0;
            for (int j = 0; j < numGenes; j++) {
                suma += matrizPoblacion[i][j];
            }
            fitnessPoblacion[i] = suma;
        }
    }

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

    public static void imprimirPoblacionOrganizada() {
        System.out.println("=== Población Organizada por Fitness ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.print(i + ". [ ");
            for (int j = 0; j < numGenes; j++) {
                System.out.print(matrizPoblacion[i][j] + " ");
            }
            System.out.println("] Fitness: " + fitnessPoblacion[i]);
        }
        System.out.println("");
    }

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

    public static void imprimirProbabilidades() {
        float sumaProbabilidades = 0.0f;
        System.out.println("=== Probabilidades ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.println(i + ". Fitness: " + fitnessPoblacion[i] + ", Probabilidad: " + probabilidades[i]);
            sumaProbabilidades += probabilidades[i];
        }
        System.out.println("Suma de probabilidades: " + sumaProbabilidades);
    }

   
    // Método para seleccionar un padre usando el método de la ruleta basado en probabilidades
    public static int seleccionarPadre() {
        double aleatorio = Math.random();
        double sumaAcumulada = 0.0;
        
        for (int i = 0; i < numIndividuos; i++) {
            sumaAcumulada += probabilidades[i];
            if (aleatorio <= sumaAcumulada) {
                return i; // Retorna el índice del individuo seleccionado
            }
        }
        return 0; // Por seguridad, si hay un error de redondeo, retorna el mejor individuo
    }

    // Método que crea a los hijos y reemplaza a los peores individuos
    public static void cruzamientoYMutacion() {
        
        int[][] nuevosHijos = new int[numHijos][numGenes]; // Arreglo temporal

        // 1. Generar los 50 hijos
        for (int i = 0; i < numHijos; i++) {
            int padre1 = seleccionarPadre();
            int padre2 = seleccionarPadre();

            // Evitamos que un padre se cruce consigo mismo (opcional pero recomendado)
            while(padre1 == padre2) {
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

                // --- MUTACIÓN INDEPENDIENTE POR GEN ---
                if (Math.random() < probMutacion) {
                    // Si muta, invertimos el gen (de 0 a 1, o de 1 a 0)
                    nuevosHijos[i][j] = (nuevosHijos[i][j] == 0) ? 1 : 0;
                }
            }
        }

        // 2. Reemplazo: Sustituir a los peores 50 con los nuevos hijos
        int inicioReemplazo = numIndividuos - numHijos; // Empezará en el índice 50
        for (int i = 0; i < numHijos; i++) {
            matrizPoblacion[inicioReemplazo + i] = nuevosHijos[i];
        }
    }
}