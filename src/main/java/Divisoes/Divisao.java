package Divisoes;

import Pessoas.Pessoa;
import Pessoas.TipoPessoa;
import Structures.Lists.LinkedUnorderedList;
import Structures.Lists.UnorderedListADT;

/**
 * This class represents a division in a system.
 * Each division has an id, name, type, sensor, access permission, maximum capacity and a list of people.
 */
public class Divisao {
    private String id;
    private String name;
    private TipoDivisao tipoDivisao;
    private final Sensor sensor;
    private final UnorderedListADT<TipoPessoa> permissaoAcesso;
    private int capacidadeMaxima;
    private UnorderedListADT<Pessoa> pessoas;

    /**
     * Constructor for the Divisao class.
     * @param id The id of the division.
     * @param name The name of the division.
     * @param tipoDivisao The type of the division.
     * @param capacidadeMaxima The maximum capacity of the division.
     */
    public Divisao(String id, String name, TipoDivisao tipoDivisao, int capacidadeMaxima) {
        this.id = id;
        this.name = name;
        this.tipoDivisao = tipoDivisao;
        this.pessoas = new LinkedUnorderedList<>();
        this.sensor = new Sensor(this);
        this.permissaoAcesso = new LinkedUnorderedList<>();
        this.capacidadeMaxima = capacidadeMaxima;
    }


    /**
 * This method is used to get the id of the division.
 * @return String This returns the id of the division.
 */
public String getId() {
    return id;
}

/**
 * This method is used to set the id of the division.
 * @param id This is the new id of the division.
 */
public void setId(String id) {
    this.id = id;
}

/**
 * This method is used to get the name of the division.
 * @return String This returns the name of the division.
 */
public String getName() {
    return name;
}

/**
 * This method is used to set the name of the division.
 * @param name This is the new name of the division.
 */
public void setName(String name) {
    this.name = name;
}

/**
 * This method is used to get the type of the division.
 * @return TipoDivisao This returns the type of the division.
 */
public TipoDivisao getTipoDivisao() {
    return tipoDivisao;
}
   /**
 * This method is used to set the type of the division.
 * @param tipoDivisao This is the new type of the division.
 */
public void setTipoDivisao(TipoDivisao tipoDivisao) {
    this.tipoDivisao = tipoDivisao;
}

/**
 * This method is used to get the sensor of the division.
 * @return Sensor This returns the sensor of the division.
 */
public Sensor getSensor() {
    return sensor;
}

/**
 * This method is used to get the access permissions of the division.
 * @return UnorderedListADT<TipoPessoa> This returns the list of access permissions of the division.
 */
public UnorderedListADT<TipoPessoa> getPermissaoAcesso() {
    return permissaoAcesso;
}

/**
 * This method is used to add a new access permission to the division.
 * @param tipoPessoa This is the new access permission to be added.
 */
public void addPermissaoAcesso(TipoPessoa tipoPessoa) {
    this.permissaoAcesso.addToRear(tipoPessoa);
}

    /**
 * This method is used to get the maximum capacity of the division.
 * @return int This returns the maximum capacity of the division.
 */
public int getCapacidadeMaxima() {
    return capacidadeMaxima;
}

/**
 * This method is used to set the maximum capacity of the division.
 * @param capacidadeMaxima This is the new maximum capacity of the division.
 */
public void setCapacidadeMaxima(int capacidadeMaxima) {
    this.capacidadeMaxima = capacidadeMaxima;
}

/**
 * This method is used to get the list of people in the division.
 * @return UnorderedListADT<Pessoa> This returns the list of people in the division.
 */
public UnorderedListADT<Pessoa> getPessoas() {
    return pessoas;
}

/**
 * This method is used to set the list of people in the division.
 * @param pessoas This is the new list of people in the division.
 */
public void setPessoas(UnorderedListADT<Pessoa> pessoas) {
    this.pessoas = pessoas;
}

    /**
     * This method allows a person to access the division.
     * It registers the entry of the person and checks if the division is full or if the person has permission to access.
     * @param pessoa The person trying to access the division.
     */
    public void acederDivisao(Pessoa pessoa) {
        sensor.registarEntrada(pessoa);

        if (pessoas.size() >= capacidadeMaxima) {
            System.out.println("A pessoa" + pessoa.getId() + " com o nome" + pessoa.getName() + " acedeu à uma divisão" + this.getName() + " cheia!");
        }
        if (!permissaoAcesso.contains(pessoa.getTipoPessoa())) {
            System.out.println("A pessoa" + pessoa.getId() + " com o nome" + pessoa.getName() + " acedeu a uma divisão sem permissão!");
        }

    }

    /**
     * This method checks if two divisions are equal by comparing their ids.
     * @param divisao The division to compare with.
     * @return True if the ids are equal, false otherwise.
     */
    public boolean equals(Divisao divisao) {
        return this.id.equals(divisao.getId());
    }

    /**
     * This method returns a string representation of the division.
     * @return A string representation of the division.
     */
    @Override
    public String toString() {
        return "Divisao{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tipoDivisao=" + tipoDivisao +
                '}';
    }
}