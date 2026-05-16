/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.x4vier19.algoritmogenetico;

/**
 *
 * @author XAV
 */
public class AlgoritmoGenetico {

    static int numIndividuos = 100;
    static int numGenes = 10;
    static double probMutacion = 0.05;

    static int[][] matrizPoblacion = new int[numIndividuos][numGenes];
    static double[] fitnessPoblacion = new double[numIndividuos];

    public static void main(String[] args) {
        crearPoblacionInicial();
        imprimirPoblacion();
        evaluarFitness();
        imprimirFitness();
        int[] ranking = rankear();
        imprimirRanked(ranking);
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

    public static void evaluarFitness() {
        for (int i = 0; i < numIndividuos; i++) {
            int suma = 0;
            for (int j = 0; j < numGenes; j++) {
                suma += matrizPoblacion[i][j];
            }
            fitnessPoblacion[i] = suma;
        }
    }

    public static void imprimirFitness() {
        System.out.println("=== Fitness de la Población ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.println(i + ". [" + fitnessPoblacion[i] + "]");
        }
    }

    public static int[] rankear() {
        int[] ranking = new int[numIndividuos];
        for (int i = 0; i < numIndividuos; i++) {
            ranking[i] = i;
        }

        // Ordenar ese arreglo basándose en el fitness
        // De peor a mejor
        for (int i = 0; i < numIndividuos; i++) {
            for (int j = i + 1; j < numIndividuos; j++) {
                if (fitnessPoblacion[ranking[i]] > fitnessPoblacion[ranking[j]]) {
                    // Intercambiar
                    int temp = ranking[i];
                    ranking[i] = ranking[j];
                    ranking[j] = temp;
                }
            }
        }

        return ranking;
    }

    public static void imprimirRanked(int[] ranking) {
        System.out.println("=== Ranked ===");
        for (int i = 0; i < numIndividuos; i++) {
            System.out.println(i + ". Individuo " + ranking[i] + " (fitness: " + fitnessPoblacion[ranking[i]] + ")");
        }
    }
}
