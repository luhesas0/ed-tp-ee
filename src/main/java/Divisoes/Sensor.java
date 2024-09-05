package Divisoes;

import Pessoas.Pessoa;
import Program.HandlerJSON;
import Structures.Lists.LinkedOrderedList;
import Structures.Lists.OrderedListADT;

import java.io.File;

/**
 * This class represents a sensor in a system.
 * Each sensor is associated with a division and has a history of movements.
 */
public class Sensor {
    private final Divisao divisao;
    private final OrderedListADT<Movimento> historicoMovimentos;

    /**
     * Constructor for the Sensor class.
     * @param divisao The division associated with the sensor.
     */
    public Sensor(Divisao divisao) {
        this.divisao = divisao;
        this.historicoMovimentos = new LinkedOrderedList<>();
    }

    /**
     * This method is used to register the entry of a person in the division.
     * If the person is not already in the division, it adds the person to the division and creates a new movement.
     * The movement is then added to the history of movements and saved to a JSON file.
     * @param pessoa The person entering the division.
     */
    public void registarEntrada(Pessoa pessoa) {
        if (!divisao.getPessoas().contains(pessoa)) {
            divisao.getPessoas().addToRear(pessoa);
        } else {
            System.out.println("A pessoa já se encontra na divisão!");
            return;
        }
        Movimento movimento = new Movimento(this, pessoa);
        historicoMovimentos.add(movimento);

        System.out.println("Entrada de" + pessoa.getName() + " entrou na divisão " + divisao.getName());
        // save movement to json
        File file = new File("src/main/java/Program/movimentos.json");
        HandlerJSON.saveMovimento(movimento, file);
    }

    /**
     * This method is used to remove the entry of a person from the division.
     * If the division is not empty, it removes the person from the division.
     * @param pessoa The person leaving the division.
     */
    public void removerEntradaPessoa(Pessoa pessoa) {
        if (divisao.getPessoas().isEmpty()) {
            System.out.println("Não existem pessoas na divisão!");
            return;
        }
        divisao.getPessoas().remove(pessoa);
        System.out.println("A pessoa " + pessoa.getName() + " saiu da divisão " + divisao.getName());
    }

    /**
     * This method is used to get the history of movements detected by the sensor.
     * @return OrderedListADT<Movimento> This returns the history of movements detected by the sensor.
     */
    public OrderedListADT<Movimento> getHistoricoMovimentos() {
        return historicoMovimentos;
    }

    /**
     * This method is used to get the division associated with the sensor.
     * @return Divisao This returns the division associated with the sensor.
     */
    public Divisao getDivisao() {
        return divisao;
    }
}
