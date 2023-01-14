package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 6. ongelma tulee metodien id:tä kutsuessa.
 * Toinen säie on ehtiny poistaa lampun ja tästä seuraa NullPointerException, koska lamppua  ei ole enää olemassa.
 */
public class Hub implements Runnable {
    private Map<Integer, Light> lights = new ConcurrentHashMap<>();
    private Random rnd = new Random();
    // Mikäli terminaalisi ei osaa tulostaa lamppujen tilaa oikein, voit kokeilla asettaa tämän arvoon "true"
    private boolean ALTERNATE_OUTPUT = false;

    /**
     * Creates a new light
     *
     * @return The id of the newly-created light
     */
    public int addLight() {
        int id = rnd.nextInt(1000);
        lights.put(id, new Light(id));
        return id;
    }

    public void removeLight(int id) {
        // Täytyy tarkistaa, onko arvo vielä olemassa,
        // sillä joku saattoi lukulukosta kirjoituslukkoon vaihtamisen
        // aikana poistaa saman avaimen
        if (lights.containsKey(id)) {
            lights.remove(id);
        }

    }

    //6. Lisätään metodeihin, jossa parametrina id, if-ehto, jossa katsotaan onko null.
    public void toggleLight(int id) {
        Light l = lights.get(id);
        if (l != null) {
            l.toggle();
        }
    }

    /**
     * Turn the light with id of "id" on
     *
     * @param id The id of light to be turned on
     */
    public void turnOnLight(int id) {
        Light l = lights.get(id);
        if (l != null) {
            l.turnOn();
        }
    }

    /**
     * Turn the light with id of "id" off
     *
     * @param id The id of light to be turned off
     */
    public void turnOffLight(int id) {
        Light l = lights.get(id);
        if (l != null) {
            l.turnOff();
        }
    }

    /**
     * Get the id numbers of the currently available lights
     *
     * @return The set of ids
     */
    public Set<Integer> getLightIds() {
        return lights.keySet();
    }

    /**
     * Get the currently available lights
     *
     * @return The collection of currently available lights
     */
    public Collection<Light> getLights() {
        return lights.values();
    }

    /**
     * Turn off all the lights
     */
    public void turnOffAllLights() {
        for (var l : lights.values()) {
            l.turnOff();
        }
    }

    /**
     * Turn on all the lights
     */
    public void turnOnAllLights() {
        for (var l : lights.values()) {
            l.turnOn();
        }
    }

    /**
     * Get the string representation of the current state of the lights
     */
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        List<Integer> lightIds;
        // The lights need to be in a List since the Set is not ordered
        // (we want our lights to be in the same order on each invocation)
        lightIds = Collections.synchronizedList(new ArrayList<>(lights.keySet()));
        Collections.sort(lightIds);
        for (int id : lightIds) {
            if (lights.get(id) != null) {
                tmp.append(String.format("%s ", lights.get(id).isPowerOn() ? "ON" : "OF"));
            }
        }

        return tmp.toString();
    }

    /**
     * Status monitoring, should not require NO changes
     */
    public void run() {
        while (true) {
            final int LIMIT = 80;
            var str = this.toString();
            int padding = str.length() < LIMIT ? LIMIT - str.length() : 0;
            var output = str + " ".repeat(padding);
            if (!ALTERNATE_OUTPUT) {
                System.out.printf("\r %s", output.substring(0, LIMIT));
            } else {
                System.out.printf("%n %s", output.substring(0, LIMIT));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
