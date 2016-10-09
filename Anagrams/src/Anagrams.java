
import java.lang.*;
import java.util.*;

public class Anagrams {

    private static final Map<Integer, ArrayList<String>> dictionary = new HashMap<>();
    private static final List<ArrayList<Integer>> wordLengthCombinations =
            new ArrayList<>();
    private static final ArrayList<ArrayList<String>> anagrams = new ArrayList<>();


    public static void main(String[] args) {
        if(args.length == 2){
            final ArrayList<Character> inputChars = new ArrayList<>();
            final Scanner sc = new Scanner(System.in);

            for(int i = 0; i < args[0].length(); i++){
                if(Character.isLetter(args[0].charAt(i))){
                    inputChars.add(Character.
                            toLowerCase(args[0].charAt(i)));
                }
            }

            while(sc.hasNextLine()){
                storeDictionary(sc);
            }

            getWordLengthCombs(inputChars.size(), inputChars.size(), new ArrayList<>());

            for (ArrayList<Integer> combination : wordLengthCombinations){
                if (validCombination(combination, Integer.valueOf(args[1]))){
                    anagramsFromCombination(
                            combination,
                            new ArrayList<>(),
                            new ArrayList<>(inputChars));
                }
            }

            sortAnagrams();

            for (ArrayList<String> anagram : anagrams){
                for (String word : anagram) {
                    System.out.print(word + " ");
                }
                System.out.println();
            }
        }
    }

    private static void sortAnagrams() {
        Collections.sort(anagrams, (o1, o2) -> {
            Collections.sort(o1);
            Collections.reverse(o1);
            Collections.sort(o2);
            Collections.reverse(o2);
            if (o1.size() == o2.size()) {
                for (int i = 0; i < o1.size(); i++){
                    if (o1.get(i).length() < o2.get(i).length()){
                        return 1;
                    } else if (o1.get(i).length() >
                            o2.get(i).length()){
                        return -1;
                    }
                }
                return 0;
            } else if (o1.size() < o2.size()) {
                return 1;
            } else {
                return -1;
            }
        });
    }


    private static void getWordLengthCombs(
            int n, int max, ArrayList<Integer> nums) {
        if (n == 0) {
            wordLengthCombinations.add(nums);
            return;
        }

        for (int i = Math.min(max, n); i >= 1; i--) {
            final ArrayList<Integer> arr = new ArrayList<>(nums);
            arr.add(i);
            getWordLengthCombs(n-i, i, arr);
        }
    }

    private static void anagramsFromCombination(
            final ArrayList<Integer> combination,
            final ArrayList<String> words,
            final ArrayList<Character> avaliableChars){


        if (combination.size() == 0 && avaliableChars.size() == 0){
            anagrams.add(words);
            return;
        }

        for(Integer i : combination){
            ArrayList<String> wordsOfLenI = dictionary.get(i);
            for (String s : wordsOfLenI){
                boolean avaliable = true;
                for (char c : s.toCharArray()) {
                    if(!avaliableChars.contains(c)){
                        avaliable = false;
                        break;
                    }
                }

                if (avaliable) {
                    ArrayList<Integer> newCom =
                            new ArrayList<>(combination);
                    ArrayList<String> newWords =
                            new ArrayList<>(words);
                    ArrayList<Character> newChars =
                            new ArrayList<>(avaliableChars);

                    newCom.remove(i);
                    newWords.add(s);
                    for (char c : s.toCharArray()) {
                        newChars.remove(Character.valueOf(c));
                    }
                    anagramsFromCombination(
                            newCom, newWords, newChars);
                }
            }
        }
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