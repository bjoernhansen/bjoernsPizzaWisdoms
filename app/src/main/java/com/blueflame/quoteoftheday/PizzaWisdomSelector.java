package com.blueflame.quoteoftheday;

import java.util.ArrayList;
import java.util.List;

public class PizzaWisdomSelector {

    private final List<String> wisdomList = new ArrayList<>();
    private int currentIndex;
    private static final PizzaWisdomSelector pizzaWisdomSelector = new PizzaWisdomSelector();

    private PizzaWisdomSelector() {
        wisdomList.add("Viele Wege führen zur Pizza!");
        wisdomList.add("Jeder ist seiner Pizza Bäcker!");
        wisdomList.add("Wer anderen eine Pizza backt, isst sie selber auf.");
        wisdomList.add("A pizza a day keeps the doctor away!");
        wisdomList.add("Eine Pizza kommt selten allein.");
        wisdomList.add("Jede Pizza findet ihren Teller.");
        wisdomList.add("Eine Pizza am Morgen vertreibt Kummer und Sorgen.");
        wisdomList.add("Geteilte Pizza ist halbe Pizza.");
        wisdomList.add("Es ist nicht alles Pizza, was mit Salami belegt ist.");
        wisdomList.add("Die Pizza fällt nicht weit vom Ofen.");
        wisdomList.add("Viele Pizza-Bäcker verderben die Pizza.");
        wisdomList.add("Wenn zwei sich streiten, isst der Dritte die Pizza.");
        wisdomList.add("Keine Hände, keine Pizza.");
        wisdomList.add("Pasta ist silber, Pizza ist gold.");
        wisdomList.add("Lieber die Pizza in der Hand als die Spaghetti auf dem Teppich.");
        wisdomList.add("Man soll die Pizza nicht vor dem Backen loben.");
        wisdomList.add("Bei einer geschenkten Pizza riecht man nicht an der Salami.");
        wisdomList.add("Mini-Pizzen hinterlassen auch Krümel.");
        wisdomList.add("Wer im Steinofen sitzt, sollte nicht mit Pizzateig werfen.");
        wisdomList.add("Andere Pizzarias, andere Pizzen.");
        wisdomList.add("Kleiner Hunger, kleine Pizza.");
        wisdomList.add("Keine Pizza in Nizza.");
        wisdomList.add("In der allergrößten Not schmeckt die Pizza auch ohne Belag.");
        wisdomList.add("Nach der Pizza ist vor der Pizza.");
        wisdomList.add("Liebe geht durch den Pizzabauch.");
    }

    private String getWisdom(){
        currentIndex = (++currentIndex)%wisdomList.size();
        return wisdomList.get(currentIndex);
    }

    public static String getNextWisdom(){
        return pizzaWisdomSelector.getWisdom();
    }
}
