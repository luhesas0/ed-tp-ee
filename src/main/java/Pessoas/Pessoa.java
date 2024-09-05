package Pessoas;

/**
 * This class represents a person in a system.
 * Each person has an id, name, and type.
 */
public class Pessoa {
    private String id;
    private String name;
    private TipoPessoa tipoPessoa;

    /**
     * Constructor for the Pessoa class.
     * @param id The id of the person.
     * @param name The name of the person.
     * @param tipoPessoa The type of the person.
     */
    public Pessoa(String id, String name, TipoPessoa tipoPessoa) {
        this.id = id;
        this.name = name;
        this.tipoPessoa = tipoPessoa;
    }

    /**
     * This method is used to get the id of the person.
     * @return String This returns the id of the person.
     */
    public String getId() {
        return id;
    }

    /**
     * This method is used to set the id of the person.
     * @param id This is the new id of the person.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method is used to get the name of the person.
     * @return String This returns the name of the person.
     */
    public String getName() {
        return name;
    }

   /**
     * This method is used to set the name of the person.
     * @param name This is the new name of the person.
     */
   public void setName(String name) {
        this.name = name;
   }

    /**
     * This method is used to get the type of the person.
     * @return TipoPessoa This returns the type of the person.
     */
    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    /**
     * This method is used to set the type of the person.
     * @param tipoPessoa This is the new type of the person.
     */
    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    /**
     * This method is used to get a string representation of the person.
     * @return String This returns a string representation of the person.
     */
    @Override
    public String toString() {
        return "Pessoa{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tipoPessoa=" + tipoPessoa +
                '}';
    }

}
