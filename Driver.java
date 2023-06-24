import java.util.*;
import java.io.*;
import java.util.Set;

class Words{
    public String wordName;
    public String definition;

    Words(String wordName, String definition){
        this.wordName = wordName;
        this.definition = definition;
    }

    @Override
    public String toString() {
        return wordName + ": " + definition;
    }

    public boolean contains(String substring) {
        return definition.contains(substring);
    }
}

class Dictionary{
    public HashMap<String, ArrayList<Words>> map = new HashMap<String,ArrayList<Words>>();


    public void print(){
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key);
            ArrayList<Words> list = map.get(key);
            for(Words words : list){
                System.out.println(words.wordName);
                System.out.println(words.definition);
            }
        }
    }
    public void searchByKey(String key){
        ArrayList<Words> list = map.get(key);
        if(list != null){
            for(Words words : list){
                System.out.println(words.wordName);
                System.out.println(words.definition);
            }
        }else{
            System.out.println("<NOT FOUND> To be considered for the next release. Thank you.");
        }
    }
    public void searchByKeyAndPartOfSpeech(String key, String partOfSpeech){
        ArrayList<Words> list = map.get(key);
        if(list != null){
            boolean validFound = false;
            for(Words words : list){

                if(words.definition.contains(partOfSpeech)){
                    validFound = true;
                    System.out.println(words.wordName);
                    System.out.println(words.definition);
                }
            }
            if(!(validFound)){
                System.out.println("<The entered **** '"+ partOfSpeech +"' is NOT a part of speech.>");
            }
        }else{
            System.out.println("<NOT FOUND> To be considered for the next release. Thank you.");
        }
    }
    public void searchByKeyAndDistinct(String key){
        HashMap<String,Words> distinctWords = new HashMap<>();
        System.out.println(key);
        ArrayList<Words> list = map.get(key);
        for(Words words : list){
            String combinedKey = words.wordName + words.definition;
            distinctWords.put(combinedKey, words);
        }
        Set<String> distinctKeys = distinctWords.keySet();
        for (String distinctKey : distinctKeys) {
            Words words = distinctWords.get(distinctKey);
            System.out.println(words.wordName);
            System.out.println(words.definition);
        }
    }
    public void searchByKeyAndReverse(String key){
        ArrayList<Words> list = map.get(key);
        Collections.reverse(list);
        for(Words words : list){
            System.out.println(words.wordName);
            System.out.println(words.definition);
        }
    }


    public void loadWords() throws Exception {
        System.out.println("! Loading data...");
        int numDefinitions = 0;
        HashMap<String, ArrayList<Words>> tempMap = new HashMap<String, ArrayList<Words>>();
        BufferedReader br = new BufferedReader(new FileReader("./dictionary.txt"));
        for(String line = br.readLine(); line != null; line = br.readLine()){
            String[] parsedLine = line.split(",");
            String wordName = parsedLine[0];
            String definition = parsedLine[1];
            Words words = new Words(wordName,definition);

            if (!tempMap.containsKey(wordName)) {
                tempMap.put(wordName, new ArrayList<Words>());
            }
            tempMap.get(wordName).add(words);
            numDefinitions++;
        }
        br.close();
        for (String word : tempMap.keySet()) {
            if (!map.containsKey(word)) {
                map.put(word, new ArrayList<Words>());
            }
            map.get(word).addAll(tempMap.get(word));
        }
        System.out.println("! Loading completed...");
        System.out.println("===== DICTIONARY 340 JAVA =====");
        System.out.println("----- Keywords: " + map.size() );
        System.out.println("----- Definitions: " + numDefinitions);
    }

    public String findWord(String word){ // CHANGE I J AND WORD PARAMERTERS
        for (String i: map.keySet()){
            if(i.equals(word)){
                ArrayList<Words> definitions = map.get(i);
                for (int j = 0; j < definitions.size(); j++) {
                    System.out.println(definitions.get(j).toString());
                }
            }
        }
        return word;
    }

//    public ArrayList<Words> findList(String key){ // CHANGE I J AND WORD PARAMERTERS
//        return map.get(key);
//    }

    public void distinct(String word) {
        if (map.containsKey(word)) {
            ArrayList<Words> definitions = map.get(word);
            HashMap<String, Boolean> distinctDefinitions = new HashMap<>();
            for (Words definition : definitions) {
                distinctDefinitions.put(definition.definition, true);
            }
            for (String definition : distinctDefinitions.keySet()) {
                System.out.println(definition);
            }
        }
    }

    public void reverse(String word) {
        if (map.containsKey(word)) {
            ArrayList<Words> definitions = map.get(word);
            System.out.println("Reversed definitions for '" + word + "':");
            for (int i = definitions.size() - 1; i >= 0; i--) {
                System.out.println(definitions.get(i).toString());
            }
        }
    }

    public Boolean containsKey(String word) {
        return map.containsKey(word);
    }

    public Boolean containsPartOfSpeech(String word) {
        String[] listNouns = {"adjective", "noun", "verb", "conjunction", "preposition", "pronoun", "adverb"};
        for (String element : listNouns) {
            if (element.equals(word)) {
                return true;
            }
        }
        return false;
    }
    public void filterValuesByKeyAndWord(String key, String word) {
        if (map.containsKey(key)) {
            ArrayList<Words> definitions = map.get(key);
            for (Words definition : definitions) {
                if (definition.definition.contains(word)) {
                    System.out.println(definition.toString());
                }
            }
        }
    }
}

public class Driver {
    public static void main(String[] args) throws Exception {
            Dictionary obj = new Dictionary();
            obj.loadWords();
            Scanner scanner = new Scanner(System.in);
            int searchCounter = 1;
            String input;
            String[] inputs;
            do {
                System.out.println("Search [" + searchCounter + "]:");
                input = scanner.nextLine();
                inputs = input.split(" ");
                System.out.println(inputs.length);
                if(inputs.length == 1){
                    if (inputs[0].equals("!q")) {
                        System.out.println("-----THANK YOU-----");
                        break;
                    }
                    obj.searchByKey(inputs[0]);
                }else if (inputs.length == 2){
                    if(inputs[1].equalsIgnoreCase("reverse")){
                        obj.searchByKeyAndReverse(inputs[0]);
                    } else if (inputs[1].equalsIgnoreCase("distinct")) {
                        obj.searchByKeyAndDistinct(inputs[0]);
                    }else{
                        obj.searchByKeyAndPartOfSpeech(inputs[0],inputs[1]);
                    }
                }

                searchCounter++;
                System.out.println("|");
            } while (!inputs[0].equals("!q"));

            scanner.close();
        }
}


