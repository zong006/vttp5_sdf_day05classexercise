package src;

import java.util.*;
import java.io.*;

public class nextWordProb {

    private static Map<String , Map<String, Integer>> d;

    public static void main (String[] args) throws IOException{

        String fileName = args[0];
        String initialWord = args[1];
        int lenOfSentence = Integer.parseInt(args[2]);

        String textFileDir = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        String filePath = textFileDir + fileName;
        File f = new File(filePath);
        if (f.exists()){
            System.out.println("File exists");
        }
        else {
            throw new IOException("File not found.");
        }
        
        List<String> listOfWords = new ArrayList<>();

        try (FileReader fr = new FileReader(f)) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String lineRead = "";

                while ((lineRead = br.readLine())!=null){
                    
                    String[] words = lineRead.split(" ");
                    for (String word:words){
                        if (word.length()>0){
                            String processedWord = removePunctuation(word);
                        processedWord = processedWord.toLowerCase();
                        listOfWords.add(processedWord);
                        }
                        else{
                            continue;
                        }
                    }   
                    
                }

            }
        }        
        
        d = generateMap(listOfWords);

        String sentence = generateSentence(initialWord, lenOfSentence);
        System.out.println(sentence);
        // wordDist(listOfWords);

}

    public static void wordDist(List<String> listOfWords){

        for (String word : listOfWords){
            
            System.out.println(word);
            int totalCount = 0;
            Map<String, Integer> map = d.get(word);
            for (String w : map.keySet()){
                totalCount += map.get(w);
            }

            for (String w : map.keySet()){
                float prob = (float)map.get(w)/totalCount;
                if (prob >= 0.1){
                    
                    System.out.printf("          %s     %.2f \n", w, prob);
                }
            }
            System.out.println("================================");
        }
        
    }



    public static String generateSentence(String initialWord, int lenOfSentence){

        String sentenceGenerated = initialWord;
        
        String currentWord = initialWord;

        for (int i = 0 ; i < lenOfSentence ; i++){
            String nextWord = generateWord(currentWord);
            sentenceGenerated += " " + nextWord;
            currentWord = nextWord;
        }

        return sentenceGenerated;
    }


    public static String generateWord(String currentWord){
        
        Map<String, Integer> possibleNextWords = d.get(currentWord);
        // System.out.println();
        int maxCount = 0 ;
        String nextWord = "";

        for(String word : possibleNextWords.keySet() ){

            int wordCount = possibleNextWords.get(word);
            if (wordCount > maxCount){
                nextWord = word;
                maxCount = wordCount;
            }
            else{
                continue;
            }
        }
        return nextWord;
    }

    public static Map<String , Map<String, Integer>> generateMap(List<String> words){

        Map<String , Map<String, Integer>> map = new HashMap<>();
        
        for (int i = 0 ; i < words.size()-1 ; i ++){
            
            String currentWord = removePunctuation(words.get(i));
            String followingWord = removePunctuation(words.get(i+1));

            Integer counter = 1;
            Map<String, Integer> mapOfNextWords = new HashMap<>();

            if (!(map.containsKey(currentWord))){ 
                mapOfNextWords.put(followingWord, counter);
                map.put(currentWord, mapOfNextWords);
            }

            else {
                mapOfNextWords = map.get(words.get(i));
                // check if following word is inside mapOfNextWords
                if (mapOfNextWords.containsKey(followingWord)){
                    counter = mapOfNextWords.get(followingWord) + 1;
                    mapOfNextWords.put(followingWord, counter);
                }
                else {
                    mapOfNextWords.put(followingWord, counter);
                }
                map.put(currentWord, mapOfNextWords);
            }
        }
        return map;
    }

    public static String removePunctuation(String word){
        
        List<String> punctuations = Arrays.asList(".", ",", ":", ";", "!", "-", "(", ")", "{", "}", "'", "\"", "?");
    
        for (String punctuation : punctuations){
            word = word.replace(punctuation, "");
        }
        return word;
    }

}
