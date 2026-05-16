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
    }
    
    public static void crearPoblacionInicial() {
        for (int i = 0; i < numIndividuos; i++) {
            for (int j = 0; j < numGenes; j++) {
                matrizPoblacion[i][j] = (Math.random() < 0.5) ? 1 : 0;
            }
        }
    }

    public static void imprimirPoblacion() {
        for (int i = 0; i < numIndividuos; i++) {
            System.out.print("[ ");
            for (int j = 0; j < numGenes; j++) {
                System.out.print(matrizPoblacion[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println("");
    }
    
    
}
