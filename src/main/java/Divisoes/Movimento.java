package Divisoes;

import Pessoas.Pessoa;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

/**
 * This class represents a movement in a system.
 * Each movement has a sensor, a person and a date.
 */
public class Movimento implements Comparable<Movimento> {
    private Sensor sensor;
    private Pessoa pessoa;
    private LocalDateTime data;

    /**
     * Constructor for the Movimento class.
     * @param sensor The sensor that detected the movement.
     * @param pessoa The person who made the movement.
     */
    public Movimento(Sensor sensor, Pessoa pessoa) {
        setPessoa(pessoa);
        setSensor(sensor);
        setData(LocalDateTime.now());
    }

    /**
     * This method is used to get the person who made the movement.
     * @return Pessoa This returns the person who made the movement.
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * This method is used to set the person who made the movement.
     * @param pessoa This is the person who made the movement.
     */
    private void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    /**
 * This method is used to get the sensor that detected the movement.
 * @return Sensor This returns the sensor that detected the movement.
 */
public Sensor getSensor() {
    return sensor;
}

/**
 * This method is used to set the sensor that detected the movement.
 * @param sensor This is the sensor that detected the movement.
 */
private void setSensor(Sensor sensor) {
    this.sensor = sensor;
}

/**
 * This method is used to get the date of the movement.
 * @return LocalDateTime This returns the date of the movement.
 */
public LocalDateTime getData() {
    return data;
}

/**
 * This method is used to set the date of the movement.
 * @param data This is the date of the movement.
 */
private void setData(LocalDateTime data) { this.data = data; }

    /**
 * This method is used to convert a Movimento object into a JSON object.
 * @param movimento The Movimento object to be converted.
 * @return JSONObject This returns the JSON representation of the Movimento object.
 */
public static JSONObject toJson(Movimento movimento) {
    JSONObject json = new JSONObject();
    json.put("pessoa", movimento.getPessoa().getId());
    json.put("sensor", movimento.getSensor().getDivisao().getId());
    json.put("data", movimento.getData().toString());
    return json;
}

/**
 * This method is used to get a string representation of the Movimento object.
 * @return String This returns a string representation of the Movimento object.
 */
@Override
public String toString() {
    return "RegistoSensor{" +
            "pessoa=" + pessoa +
            ", sensor=" + sensor +
            ", data=" + data +
            '}';
}

/**
 * This method is used to compare two Movimento objects based on their date.
 * @param o The Movimento object to be compared.
 * @return int This returns the result of the comparison. A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
 */
@Override
public int compareTo(Movimento o) {
    return this.getData().compareTo(o.getData());
}
}
