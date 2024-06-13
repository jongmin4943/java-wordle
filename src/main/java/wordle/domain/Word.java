package wordle.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import wordle.exception.WordInputNotValidException;

public class Word implements Iterable<Letter> {

    public static final int WORD_LENGTH = 5;
    private List<Letter> letters;

    public Word(String input) {
        if (input.length() != WORD_LENGTH) {
            throw new WordInputNotValidException();
        }

        this.letters = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            Letter letter = new Letter(input.charAt(i), i);
            this.letters.add(letter);
        }
    }

    public Results compare(Word inputWord) {
        boolean[] visit = new boolean[WORD_LENGTH];

        Results results = new Results();
        for (Letter letter : inputWord) {
            Result green = findGreen(letter, visit);
            if (green != null) {
                results.add(green);
            }
        }

        for (Letter letter : inputWord) {
            Result yellow = findYellow(letter, visit);
            if (yellow != null) {
                results.add(yellow);
            } else {
                results.add(new Result(Tile.GRAY, letter.getPosition()));
            }
        }

        return results;
    }

    private Result findYellow(Letter targetLetter, boolean[] visit) {
        for (int i = 0; i < this.letters.size(); i++) {
            Letter letter = this.letters.get(i);
            if(visit[i]){
                continue;
            }
            if (letter.isSameAlphabet(targetLetter)) {
                visit[i] = true;
                return new Result(Tile.YELLOW, targetLetter.getPosition());
            }
        }
        return null;
    }

    private Result findGreen(Letter targetLetter, boolean[] visit) {
        for (int i = 0; i < this.letters.size(); i++) {
            Letter letter = this.letters.get(i);
            if (letter.equals(targetLetter)) {
                visit[i] = true;
                return new Result(Tile.GREEN, targetLetter.getPosition());
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        return Objects.equals(letters, word.letters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letters);
    }

    @Override
    public Iterator<Letter> iterator() {
        return this.letters.iterator();
    }
}