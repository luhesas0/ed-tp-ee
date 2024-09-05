package Program;

import Divisoes.Divisao;
import Divisoes.TipoDivisao;
import Pessoas.Pessoa;
import Pessoas.TipoPessoa;
import Structures.Graphs.Network;
import Structures.Graphs.NetworkADT;
import Structures.HashTables.HashMap;
import Structures.HashTables.MapADT;
import Structures.Lists.LinkedUnorderedList;
import Structures.Lists.UnorderedListADT;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class represents the main program of the system.
 * It contains collections of people, divisions, last locations of people, and contacts between people.
 */
public class Program {
    private final MapADT<String, Pessoa> pessoas; // A map to store people, with their id as the key.
    private final NetworkADT<Divisao> divisoesNetwork; // A network to store divisions and their connections.
    private final UnorderedListADT<Divisao> divisoes; // An unordered list to store divisions.
    private final MapADT<Pessoa, Divisao> lastLocationPessoa; // A map to store the last location of each person.
    private final MapADT<Pessoa, MapADT<Pessoa, LocalDateTime>> contactoPessoas; // A map to store the contacts between people and the time of contact.

    /**
     * Constructor for the Program class.
     * Initializes the collections.
     */
    public Program() {
        this.pessoas = new HashMap<>();
        this.divisoesNetwork = new Network<>();
        this.divisoes = new LinkedUnorderedList<>();
        this.contactoPessoas = new HashMap<>();
        this.lastLocationPessoa = new HashMap<>();
    }

    /**
     * This method is used to add a person to the system.
     * @param pessoa The person to be added.
     */
    public void addPessoa(Pessoa pessoa) {
        pessoas.put(pessoa.getId(), pessoa);
    }

    /**
     * This method is used to add a division to the system.
     * The division is added to the list of divisions and as a vertex in the network of divisions.
     * @param divisao The division to be added.
     */
    public void addDivisao(Divisao divisao) {
        divisoes.addToRear(divisao);
        divisoesNetwork.addVertex(divisao);
    }


   /**
 * This method allows a person to access a division.
 * It checks if the person exists in the system, if the person is already in the division, and adds the person to the division.
 * It also updates the last location of the person and adds contacts with all people in the division.
 * @param pessoa The person trying to access the division.
 * @param divisao The division the person is trying to access.
 */
public void pessoaAcederDivisao(Pessoa pessoa, Divisao divisao) {
    // verificar se a pessoa existe
    if (!pessoas.containsKey(pessoa.getId())) {
        System.out.println("Pessoa não existe!");
        return;
    }
    // check if the person has an entry in the lastLocationPessoa, if yes remove the person from the division
    if (lastLocationPessoa.containsKey(pessoa)) {
        Divisao lastDivisao = lastLocationPessoa.get(pessoa);
        lastDivisao.getSensor().removerEntradaPessoa(pessoa);
    }

    divisao.acederDivisao(pessoa);
    lastLocationPessoa.put(pessoa, divisao);
    //add contacto with all persons of the division based on X hours
    UnorderedListADT<Pessoa> pessoasDivisao = divisao.getPessoas();
    for (Pessoa obj : pessoasDivisao) {
        addContacto(pessoa, obj);
    }
}

/**
 * This method returns the last location of a person.
 * @param pessoa The person whose last location is to be returned.
 * @return Divisao The last location of the person.
 */
public Divisao lastLocationPessoa(Pessoa pessoa) {
    return lastLocationPessoa.get(pessoa);
}

/**
 * This method creates a connection between two divisions.
 * @param divisao1 The first division.
 * @param divisao2 The second division.
 */
public void addConexao(Divisao divisao1, Divisao divisao2) {
    divisoesNetwork.addEdge(divisao1, divisao2);
}

/**
 * This method prints all the divisions in the system and their connections.
 */
public void printDivisoes() {
    for (Divisao divisao : divisoes) {
        System.out.println(divisao);
    }
    System.out.println(divisoesNetwork);
}

    /**
 * This method is used to add a contact between two people.
 * It checks if the first person already has contacts, and if so, it adds the second person to their contacts.
 * If the first person does not have any contacts, it creates a new list of contacts and adds the second person to it.
 * The time of contact is set to the current time.
 * @param pessoa1 The first person.
 * @param pessoa2 The second person.
 */
public void addContacto(Pessoa pessoa1, Pessoa pessoa2) {
    LocalDateTime now = LocalDateTime.now();
    if (contactoPessoas.containsKey(pessoa1)) {
        MapADT<Pessoa, LocalDateTime> contactos = contactoPessoas.get(pessoa1);
        contactos.put(pessoa2, now);
    } else {
        MapADT<Pessoa, LocalDateTime> contactos = new HashMap<>();
        contactos.put(pessoa2, now);
        contactoPessoas.put(pessoa1, contactos);
    }
}

/**
 * This method is used to get all the people in the system.
 * @return MapADT<String, Pessoa> This returns a map of all the people in the system, with their id as the key.
 */
public MapADT<String, Pessoa> getPessoas() {
    return pessoas;
}

/**
 * This method is used to create a hospital with several divisions based on the enumerations.
 * It creates three divisions and adds them to the system.
 * It also creates connections between the divisions and sets access permissions.
 * It then creates six people and adds them to the system.
 * Finally, it allows two of the people to access one of the divisions.
 */
public void criarEstabelecimento() {
    Divisao divisao1 = new Divisao("1", "Armário", TipoDivisao.ARMARIO, 2);
    Divisao divisao2 = new Divisao("2", "Bloco Operatório", TipoDivisao.BLOCO_OPERATORIO, 5);
    Divisao divisao3 = new Divisao("3", "Cozinha", TipoDivisao.COZINHA, 3);

    addDivisao(divisao1);
    addDivisao(divisao2);
    addDivisao(divisao3);

    addConexao(divisao1, divisao2);
    addConexao(divisao2, divisao3);

    // definir permissoes de acesso
    divisao1.addPermissaoAcesso(TipoPessoa.AUXILIAR);
    divisao1.addPermissaoAcesso(TipoPessoa.ADMINISTRATIVO);
    divisao2.addPermissaoAcesso(TipoPessoa.MEDICO);
    divisao3.addPermissaoAcesso(TipoPessoa.AUXILIAR);


    // adicionar pessoas ao hospital de forma aleatória
    Pessoa pessoa1 = new Pessoa("1", "Pessoa 1", TipoPessoa.ADMINISTRATIVO);
    Pessoa pessoa2 = new Pessoa("2", "Pessoa 2", TipoPessoa.AUXILIAR);
    Pessoa pessoa3 = new Pessoa("3", "Pessoa 3", TipoPessoa.MEDICO);
    Pessoa pessoa4 = new Pessoa("4", "Pessoa 4", TipoPessoa.ENFERMEIRO);
    Pessoa pessoa5 = new Pessoa("5", "Pessoa 5", TipoPessoa.UTENTE);
    Pessoa pessoa6 = new Pessoa("6", "Pessoa 6", TipoPessoa.VISITANTE);

    addPessoa(pessoa1);
    addPessoa(pessoa2);
    addPessoa(pessoa3);
    addPessoa(pessoa4);
    addPessoa(pessoa5);
    addPessoa(pessoa6);

    // adicionar pessoas ao hospital
    pessoaAcederDivisao(pessoa1, divisao1);
    pessoaAcederDivisao(pessoa2, divisao1);
}

/**
 * This method is used to save all the people in the system to a JSON file.
 * It creates a JSON object for each person and adds it to a JSON array.
 * The JSON array is then written to the file.
 * @param file The file where the people will be saved.
 */
public void savePessoas(String file) {
    JSONArray jsonArray;
    //for without using the iterator
    for (String key : pessoas.getKeys()) {
        Pessoa pessoa = pessoas.get(key);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", pessoa.getId());
        jsonObject.put("nome", pessoa.getName());
        jsonObject.put("tipoPessoa", pessoa.getTipoPessoa().toString());


        JSONParser jsonParser = new JSONParser();
        File ficheiro = new File(file);
        if (ficheiro.exists()) {
            try (FileReader reader = new FileReader(file)) {
                if (file.isEmpty()) {
                    jsonArray = new JSONArray();
                } else {
                    Object obj = jsonParser.parse(reader);
                    jsonArray = (JSONArray) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
                jsonArray = new JSONArray();
            }
        } else {
            jsonArray = new JSONArray();
        }

        jsonArray.add(jsonObject);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    /**
 * This method returns the shortest path between a person and a division.
 * It uses the last known location of the person and the network of divisions to find the shortest path.
 * @param pessoa The person for whom the shortest path is to be found.
 * @param divisao The division to which the shortest path is to be found.
 * @return Iterator<Divisao> An iterator over the divisions in the shortest path.
 */
public Iterator<Divisao> caminhoMaisCurto(Pessoa pessoa, Divisao divisao) {
    Divisao divisaoPessoa = lastLocationPessoa(pessoa);
    return divisoesNetwork.iteratorShortestPath(divisaoPessoa, divisao);
}

/**
 * This method returns the shortest path between a person and a division.
 * It uses the id of the person and the id of the division to find the shortest path.
 * @param idPessoa The id of the person for whom the shortest path is to be found.
 * @param idDivisao The id of the division to which the shortest path is to be found.
 * @return Iterator<Divisao> An iterator over the divisions in the shortest path.
 */
public Iterator<Divisao> caminhoMaisCurto(String idPessoa, String idDivisao) {
    Pessoa pessoa = pessoas.get(idPessoa);
    Divisao divisao = null;
    for (Divisao d : divisoes) {
        if (d.getId().equals(idDivisao)) {
            divisao = d;
        }
    }
    if (divisao == null) {
        return null;
    }
    return caminhoMaisCurto(pessoa, divisao);
}

/**
 * This method saves the divisions to a JSON file.
 * It creates a JSON object for each division and adds it to a JSON array.
 * It also creates a JSON object for each connection between divisions and adds it to a JSON array.
 * The JSON arrays are then added to a main JSON object which is written to the file.
 * @param file The file where the divisions will be saved.
 */
public void saveMapa(String file) {
    JSONObject jsonPrincipal = new JSONObject();
    JSONArray jsonDivisoes = new JSONArray();
    //for without using the iterator
    for (Divisao divisao : divisoes) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", divisao.getId());
        jsonObject.put("nome", divisao.getName());
        jsonObject.put("tipoDivisao", divisao.getTipoDivisao().toString());
        jsonObject.put("capacidadeMaxima", divisao.getCapacidadeMaxima());

        jsonDivisoes.add(jsonObject);
    }
    jsonPrincipal.put("divisoes", jsonDivisoes);

    //same thing as before but for the connections between the divisions (edges of the graph)
    JSONArray jsonConexoes = new JSONArray();
    for (Divisao divisao : divisoes) {
        for (Iterator<Divisao> it = divisoesNetwork.iteratorBFS(divisao); it.hasNext(); ) {
            Divisao divisaoAdjacente = it.next();
            JSONObject jsonConexao = new JSONObject();
            jsonConexao.put("divisao1", divisao.getId());
            jsonConexao.put("divisao2", divisaoAdjacente.getId());
            jsonConexoes.add(jsonConexao);
        }
    }
    jsonPrincipal.put("conexoes", jsonConexoes);

    try (FileWriter fileWriter = new FileWriter(file)) {
        fileWriter.write(jsonPrincipal.toJSONString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}


/**
 * Moves a person to a specified division.
 *
 * @param pessoa The person to be moved.
 * @param divisao The division to which the person is to be moved.
 */
public void moverPessoa(Pessoa pessoa, Divisao divisao) {
    // Check if the person exists
    if (!pessoas.containsKey(pessoa.getId())) {
        System.out.println("Pessoa não existe!");
        return;
    }
    // Check if the division exists
    if (!divisoes.contains(divisao)) {
        System.out.println("Divisão não existe!");
        return;
    }
    // Check if the person is already in the division
    if (lastLocationPessoa.containsKey(pessoa)) {
        Divisao lastDivisao = lastLocationPessoa.get(pessoa);
        lastDivisao.getSensor().removerEntradaPessoa(pessoa);
    }
    divisao.acederDivisao(pessoa);
    lastLocationPessoa.put(pessoa, divisao);
}

/**
 * Gets the contacts of a person within the last specified hours.
 *
 * @param pessoa The person whose contacts are to be retrieved.
 * @param horas The number of hours within which the contacts are to be retrieved.
 * @return UnorderedListADT<Pessoa> A list of contacts within the specified hours.
 */
public UnorderedListADT<Pessoa> getContactosUltimasHoras(Pessoa pessoa, int horas) {
    UnorderedListADT<Pessoa> contactos = new LinkedUnorderedList<>();
    MapADT<Pessoa, LocalDateTime> contactosPessoa = contactoPessoas.get(pessoa);
    LocalDateTime now = LocalDateTime.now();
    for (Pessoa p : contactosPessoa.getKeys()) {
        LocalDateTime contacto = contactosPessoa.get(p);
        long diff = ChronoUnit.HOURS.between(contacto, now);
        if (diff <= horas) {
            contactos.addToRear(p);
        }
    }
    return contactos;
}

/**
 * This method provides a user interface for managing the structure of the system.
 * It provides options for registering a person, adding a division, creating a connection between divisions, loading a JSON file with the structure, listing entities, exporting entities to a JSON file, and finding the shortest path between a person and a division.
 */
public void gestaoEstrutural() {
    Scanner scanner = new Scanner(System.in);
    int opcao = -1;

    while (opcao != 0) {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Registar pessoa");
        System.out.println("2. Adicionar Divisão");
        System.out.println("3. Criar Conexão entre Divisões");
        System.out.println("4. Carregar JSON com estrutura");
        System.out.println("5. Listar entidades");
        System.out.println("6. Exportar entidades para JSON");
        System.out.println("7. Caminho mais curto entre pessoa e divisão");
        System.out.println("8. Movimentar pessoa");
        System.out.println("0. Sair");

        opcao = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        switch (opcao) {
            case 1:
                System.out.println("Indique um id para a pessoa:");
                String idPessoa = scanner.nextLine();
                System.out.println("Indique um nome para a pessoa:");
                String nomePessoa = scanner.nextLine();
                System.out.println("Indique um tipo para a pessoa da lista seguinte:");
                System.out.println(Arrays.toString(TipoPessoa.values()));
                TipoPessoa tipoPessoa = TipoPessoa.valueOf(scanner.nextLine());
                this.addPessoa(new Pessoa(idPessoa, nomePessoa, tipoPessoa));
                System.out.println("Registo efetuado com sucesso!");
                break;
            case 2:
                System.out.println("Indique um id para a divisão:");
                String id = scanner.nextLine();
                System.out.println("Indique um nome para a divisão:");
                String nome = scanner.nextLine();
                System.out.println("Indique um tipo para a divisão:");
                TipoDivisao tipoDivisao = TipoDivisao.valueOf(scanner.nextLine());
                System.out.println("Indique a capacidade máxima para a divisão:");
                int capacidadeMaxima = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                this.addDivisao(new Divisao(id, nome, tipoDivisao, capacidadeMaxima));
                System.out.println("Registo efetuado com sucesso!");
                break;
            case 3:
                System.out.println("Indique o id da primeira divisão:");
                String id1 = scanner.nextLine();
                System.out.println("Indique o id da segunda divisão:");
                String id2 = scanner.nextLine();
                Divisao divisao1 = null;
                Divisao divisao2 = null;
                for (Divisao divisao : divisoes) {
                    if (divisao.getId().equals(id1)) {
                        divisao1 = divisao;
                    }
                    if (divisao.getId().equals(id2)) {
                        divisao2 = divisao;
                    }
                }
                if (divisao1 != null && divisao2 != null) {
                    this.addConexao(divisao1, divisao2);
                } else {
                    System.out.println("Divisões não encontradas");
                }
                break;
            case 4:
                // utilizar funções do HandlerJSON para carregar divisões e pessoas
                System.out.println("Escolha uma opção:");
                System.out.println("1. Carregar pessoas.json");
                System.out.println("2. Carregar divisoes.json");
                System.out.println("3. TODO");
                System.out.println("0. Sair");
                System.out.println("Opção:");
                int opcao2 = scanner.nextInt();
                scanner.nextLine();
                switch (opcao2) {
                    case 1:
                        System.out.println("Indique o caminho para o ficheiro pessoas.json:");
                        String caminhoPessoas = scanner.nextLine();
                        //verificar se o caminho é válido
                        if (caminhoPessoas != null) {
                            JSONArray pessoasJson = HandlerJSON.recebeJSONArray(caminhoPessoas);
                            for (Object o : pessoasJson) {
                                JSONObject jsonObject = (JSONObject) o;
                                Pessoa pessoa = HandlerJSON.parsePessoa(jsonObject);
                                this.addPessoa(pessoa);
                            }
                        } else {
                            System.out.println("Caminho inválido");
                        }
                        break;
                    case 2:
                        System.out.println("Indique o caminho para o ficheiro divisoes.json:");
                        String caminhoDivisoes = scanner.nextLine();
                        //verificar se o caminho é válido
                        if (caminhoDivisoes != null) {
                            JSONObject divisoesJson = HandlerJSON.recebeJSONObject(caminhoDivisoes);
                            JSONArray divisoes = (JSONArray) divisoesJson.get("divisoes");
                            for (Object o : divisoes) {
                                JSONObject jsonObject = (JSONObject) o;
                                Divisao divisao = HandlerJSON.parseDivisao(jsonObject);
                                this.addDivisao(divisao);
                            }
                            JSONArray conexoes = (JSONArray) divisoesJson.get("conexoes");
                            for (Object o : conexoes) {
                                JSONObject jsonObject = (JSONObject) o;
                                String id3 = (String) jsonObject.get("divisao1");
                                String id4 = (String) jsonObject.get("divisao2");
                                Divisao divisao3 = null;
                                Divisao divisao4 = null;
                                for (Divisao divisao : this.divisoes) {
                                    if (divisao.getId().equals(id3)) {
                                        divisao3 = divisao;
                                    }
                                    if (divisao.getId().equals(id4)) {
                                        divisao4 = divisao;
                                    }
                                }
                                if (divisao3 != null && divisao4 != null) {
                                    this.addConexao(divisao3, divisao4);
                                } else {
                                    System.out.println("Divisões não encontradas");
                                }
                            }

                        } else {
                            System.out.println("Caminho inválido");
                        }
                        break;
                    case 3:
                        //TODO
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            case 5:
                System.out.println("Pessoas:");
                System.out.println(this.getPessoas());
                System.out.println("Divisões:");
                System.out.println(this.divisoesNetwork);
                break;
            case 6:
                System.out.println("Escolha uma opção:");
                System.out.println("1. Exportar pessoas para JSON");
                System.out.println("2. Exportar divisões para JSON");
                System.out.println("Opção:");
                int opcao3 = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer

                switch (opcao3) {
                    case 1:
                        System.out.println("Insira o nome do ficheiro para ser exportado:");
                        String nomeFicheiroPessoas = scanner.nextLine();
                        savePessoas(nomeFicheiroPessoas);
                        break;
                    case 2:
                        System.out.println("Insira o nome do ficheiro para ser exportado:");
                        String nomeFicheiroDivisoes = scanner.nextLine();
                        saveMapa(nomeFicheiroDivisoes);
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            break;
            case 7:
                System.out.println("Indique o id da pessoa:");
                String idPessoa2 = scanner.nextLine();
                System.out.println("Indique o id da divisão:");
                String idDivisao = scanner.nextLine();
                Iterator<Divisao> it = caminhoMaisCurto(idPessoa2, idDivisao);
                if (it != null) {
                    while (it.hasNext()) {
                        Divisao divisao = it.next();
                        System.out.println(divisao);
                    }
                } else {
                    System.out.println("Caminho não encontrado");
                }
                break;
            case 8:
                System.out.println("Indique o id da pessoa:");
                String idPessoa3 = scanner.nextLine();
                System.out.println("Indique o id da divisão:");
                String idDivisao2 = scanner.nextLine();
                Pessoa pessoa = pessoas.get(idPessoa3);
                Divisao divisao = null;
                for (Divisao d : divisoes) {
                    if (d.getId().equals(idDivisao2)) {
                        divisao = d;
                    }
                }
                if (divisao != null) {
                    moverPessoa(pessoa, divisao);
                } else {
                    System.out.println("Divisão não encontrada");
                }
                break;
        }
    }
}

}
