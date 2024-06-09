package wordle.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import wordle.exception.WordInputNotValidException;
import wordle.fixture.ResultFixture;

public class WordTest {

    @Test
    void Word를_생성한다() {
        assertDoesNotThrow(() -> new Word("apple"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "testss"})
    void Word를_생성할_때_다섯글자가_아니면_실패한다(String input) {
        assertThatThrownBy(() -> new Word(input))
                .isInstanceOf(WordInputNotValidException.class);
    }

    @Test
    void 같은_Word를_비교하면_초록_결과들을_반환한다(){
        Word baseWord = new Word("abcde");
        Word targetWord = new Word("abcde");

        Results results = baseWord.compare(targetWord);

        assertThat(results).containsExactly(
                ResultFixture.createGreenResult(0),
                ResultFixture.createGreenResult(1),
                ResultFixture.createGreenResult(2),
                ResultFixture.createGreenResult(3),
                ResultFixture.createGreenResult(4));
    }
}
