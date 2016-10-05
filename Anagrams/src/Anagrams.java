
import java.lang.*;
import java.util.*;

public class Anagrams {

    private static final Map<Integer, ArrayList> dictionary = new HashMap<>();
    private static final ArrayList<ArrayList<Integer>> wordLengthCombinations =
            new ArrayList<>();

    public static void main(String[] args) {
        if(args.length == 2){
            final ArrayList<Character> inputChars = new ArrayList<>();
            final Scanner sc = new Scanner(System.in);
            final ArrayList<ArrayList<String>> anagrams = new ArrayList<>();

            for(int i = 0; i < args[0].length(); i++){
                if(Character.isLetter(args[0].charAt(i))){
                    inputChars.add(Character.
                            toLowerCase(args[0].charAt(i)));
                }
            }

            while(sc.hasNextLine()){
                storeDictionary(sc);
            }

            partition(inputChars.size(), inputChars.size(), new ArrayList<>());

            for (ArrayList<Integer> combination : wordLengthCombinations){
                if (validCombination(combination, Integer.valueOf(args[1]))){
                    anagrams.add(anagramsFromCombination(
                            combination,
                            new ArrayList<>(),
                            new ArrayList<>(inputChars))
                    );
                }
            }

            for (ArrayList<String> anagram : anagrams) {
                System.out.println(anagram);
            }
        }
    }

    private static void partition(int n, int max, ArrayList<Integer> nums) {
        if (n == 0) {
            wordLengthCombinations.add(nums);
            return;
        }

        for (int i = Math.min(max, n); i >= 1; i--) {
            final ArrayList<Integer> arr = new ArrayList<>(nums);
            arr.add(i);
            partition(n-i, i, arr);
        }
    }

    private static ArrayList<String> anagramsFromCombination(
            ArrayList<Integer> combination,
            ArrayList<String> words,
            ArrayList<Character> avaliableChars){

        if (combination.size() == 0){
            return words;
        }

        for(Integer i : combination){
            ArrayList<String> wordsOfLenI = dictionary.get(i);
            for (String s : wordsOfLenI){
                boolean avaliable = true;
                for (char c : s.toCharArray()) {
                    if(!avaliableChars.contains(c)){
                        avaliable = false;
                    }
                }

                if (avaliable) {
                    combination.remove(i);
                    words.add(s);
                    for (char c : s.toCharArray()) {
                        avaliableChars.remove(c);
                    }
                    anagramsFromCombination(
                            combination, words, avaliableChars);
                }
            }
        }
        return null;
    }

    private static boolean validCombination(ArrayList<Integer> combination, int max){
        if (combination.size() > max){
            return false;
        }

        for (Integer i : combination){
            if (dictionary.get(i) == null){
                return false;
            }
        }
        return true;
    }

    private static void storeDictionary(Scanner sc){
        final String word = sc.nextLine();
        if(dictionary.get(word.length()) == null){
            final ArrayList<String> arr = new ArrayList<>();
            arr.add(word);
            dictionary.put(word.length(), arr);
        }else{
            final ArrayList<String> arr = dictionary.get(word.length());
            arr.add(word);
        }
    }
}
