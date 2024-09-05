package Program;

import java.io.*;
import java.time.format.DateTimeFormatter;

import Divisoes.Divisao;
import Divisoes.Movimento;
import Divisoes.TipoDivisao;
import Pessoas.Pessoa;
import Pessoas.TipoPessoa;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HandlerJSON {
    //parse pessoa from json object
   /**
 * This method is used to parse a JSONObject into a Pessoa object.
 * @param jsonObject The JSONObject to be parsed.
 * @return Pessoa This returns a Pessoa object parsed from the JSONObject.
 */
public static Pessoa parsePessoa(JSONObject jsonObject) {
    String id = (String) jsonObject.get("id");
    String nome = (String) jsonObject.get("nome");
    String tipoPessoa = (String) jsonObject.get("tipoPessoa");
    TipoPessoa tipo = TipoPessoa.valueOf(tipoPessoa.toUpperCase());
    return new Pessoa(id, nome, tipo);
}

/**
 * This method is used to parse a JSONObject into a Divisao object.
 * @param jsonObject The JSONObject to be parsed.
 * @return Divisao This returns a Divisao object parsed from the JSONObject.
 */
public static Divisao parseDivisao(JSONObject jsonObject) {
    String id = (String) jsonObject.get("id");
    String nome = (String) jsonObject.get("nome");
    String tipoDivisao = (String) jsonObject.get("tipoDivisao");
    TipoDivisao tipo = TipoDivisao.valueOf(tipoDivisao.toUpperCase());
    int capacidade = ((Long) jsonObject.get("capacidadeMaxima")).intValue();
    Divisao divisao = new Divisao(id, nome, tipo, capacidade);
    return divisao;
}

/**
 * This method is used to save a Movimento object to a JSON file.
 * @param movimento The Movimento object to be saved.
 * @param file The file where the Movimento object will be saved.
 */
public static void saveMovimento(Movimento movimento, File file) {
    // Define the date format to use for the JSON output
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Create a new JSON object for the movement
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Divisao", movimento.getSensor().getDivisao().getName());
    jsonObject.put("pessoa", movimento.getPessoa().getName());
    jsonObject.put("data", movimento.getData().format(formatter)); // Format the date

    JSONArray jsonArray;
    JSONParser jsonParser = new JSONParser();

    // Check if the file exists and is valid JSON
    if (file.exists()) {
        try (FileReader reader = new FileReader(file)) {
            //check if the file is empty
            if (file.length() == 0) {
                jsonArray = new JSONArray();
            } else {
            Object obj = jsonParser.parse(reader);
            jsonArray = (JSONArray) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonArray = new JSONArray(); // If error reading the file, initialize a new JSON array
        }
    } else {
        jsonArray = new JSONArray();
    }

    // Add the new movement to the JSON array
    jsonArray.add(jsonObject);

    // Write the updated JSON array back to the file
    try (FileWriter fileWriter = new FileWriter(file)) {
        fileWriter.write(jsonArray.toJSONString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    /**
     * This method is used to parse a JSON file into a JSONObject.
     * @param path The path of the JSON file to be parsed.
     * @return JSONObject This returns a JSONObject parsed from the JSON file.
     */
    public static JSONObject recebeJSONObject(String path) {
        try(FileReader reader = new FileReader(path)) {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(reader);
            return jsonObject;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

   /**
    * This method is used to parse a JSON file into a JSONArray.
    * @param path The path of the JSON file to be parsed.
    * @return JSONArray This returns a JSONArray parsed from the JSON file.
    * @throws RuntimeException if an error occurs during file reading or parsing.
    */
    public static JSONArray recebeJSONArray(String path) {
        try(FileReader reader = new FileReader(path)) {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(reader);
            return jsonArray;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
