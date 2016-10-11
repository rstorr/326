
import java.lang.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Anagrams class finds anagrams of a string. Input is the maximum number of words an
 * anagram may have, the string (anagrams only will use alphabetical characters of the
 * string) and a list of words that can be used as part of an anagram; a dictionary.
 *
 * @author Reuben Storr
 */

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

            for(ArrayList<Integer> combination : wordLengthCombinations){
                if (validCombination(combination, Integer.valueOf(args[1]))) {
                    anagramsFromCombination(
                            combination,
                            new ArrayList<>(),
                            new ArrayList<>(inputChars));
                }
            }

            for (ArrayList<String> anagram : sortAnagrams()){
                for (String word : anagram) {
                    System.out.print(word + " ");
                }
                System.out.println();
            }
        }
    }

    private static ArrayList<ArrayList<String>> sortAnagrams() {
        final ArrayList<ArrayList<String>> sortedArr = new ArrayList<>();

        Collections.sort(anagrams, (o1, o2) -> {
            sortIndividualAnagram(o1);
            sortIndividualAnagram(o2);

            if (o1.size() == o2.size()) {
                for (int i = 0; i < o2.size(); i++){
                    if (o1.get(i).length() != o2.get(i).length()) {
                        if (o1.get(i).length() > o2.get(i).length()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            } else if (o1.size() < o2.size()) {
                return 1;
            } else {
                return -1;
            }
        });

        sortedArr.addAll(anagrams.stream().distinct().collect(Collectors.toList()));
        Collections.reverse(sortedArr);

        return sortedArr;
    }

    private static void sortIndividualAnagram(ArrayList<String> anagram){
        Collections.sort(anagram, (o1, o2) -> {
            if (o1.length() > o2.length()){
                return -1;
            } else if (o1.length() < o2.length()){
                return 1;
            } else {
                return o1.compareTo(o2);
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
            final ArrayList<String> wordsOfLenI = dictionary.get(i);
            for (String s : wordsOfLenI){
                boolean avaliable = true;
                for (char c : s.toCharArray()) {
                    if(!avaliableChars.contains(c)){
                        avaliable = false;
                        break;
                    }
                }

                if (avaliable) {
                    final ArrayList<Integer> newCom =
                            new ArrayList<>(combination);
                    final ArrayList<String> newWords =
                            new ArrayList<>(words);
                    final ArrayList<Character> newChars =
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