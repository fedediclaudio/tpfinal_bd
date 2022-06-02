package com.bd.tpfinal.utils;

import java.util.ArrayList;
import java.util.Random;

public class RandomSinRepetir
{
    private Random index = new Random();
    private int minimo;
    private int maximo;
    private int cantidad = maximo - minimo;
    private int cant_usados = 0;

    ArrayList<Integer> usados;

    public RandomSinRepetir(int minimo, int maximo)
    {
        this.minimo = minimo;
        this.maximo = maximo;
        cantidad = maximo - minimo;
        usados = new ArrayList<Integer>();
        System.out.println("cantidad de numeros aleatorios "+ cantidad);
    }

    public int siguiente() throws NoMasRandomException
    {
        int valor =  minimo + index.nextInt(maximo - minimo);
        while(usados.contains(valor) && cantidad > cant_usados)
        {
            valor = minimo + index.nextInt(maximo - minimo);
            System.out.println("valor ya usado "+valor);
        }
        if(cant_usados > cantidad)
            throw new NoMasRandomException();
        usados.add(valor);
        cant_usados ++;
        return valor;
    }


}
