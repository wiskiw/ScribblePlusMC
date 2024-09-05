package me.chrr.scribble;

import me.chrr.scribble.tool.AdvancedTextHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvancedMoveCursorByWordsTest {

    @Test
    public void testMoveCursorNowhere() {
        String text = "Hello world";
        int cursor = text.indexOf("world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 0, cursor);

        assertEquals(cursor, newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase1() {
        String text = "Hello world";
        int cursor = text.indexOf("world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(0, newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase2() {
        String text = "Hello world";
        int cursor = text.indexOf(" world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(0, newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase3() {
        String text = "Hello world";
        int cursor = text.length();

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("world"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase4() {
        String text = "Hello world!!";
        int cursor = text.length();

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("!!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase5() {
        String text = "Hello ?! world";
        int cursor = text.indexOf(" world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("?!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase6() {
        String text = "Hello ! world";
        int cursor = text.indexOf("world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("! "), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase7() {
        String text = "Hello !! world";
        int cursor = text.indexOf(" world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("!! "), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase8() {
        String text = "Hello my world";
        int cursor = text.indexOf("rld");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("world"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase9() {
        String text = "Hello my world";
        int cursor = text.indexOf("rld");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -2, cursor);

        assertEquals(text.indexOf("my world"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase10() {
        String text = "Hello my world";
        int cursor = text.indexOf("rld");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -Integer.MAX_VALUE, cursor);

        assertEquals(0, newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase11() {
        String text = "Hello ⁘⁘⁘rld!!!"; // testing unicode chars
        int cursor = text.indexOf("!!!");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("rld!!!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheLeftCase12() {
        String text = "   \n\n    Hello world!!!    \n\n    ";
        int cursor = text.indexOf("!!!");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, -1, cursor);

        assertEquals(text.indexOf("world!!!"), newPosition);
    }

    // ==========================================================================================================================

    @Test
    public void testMoveCursorToTheRightCase1() {
        String text = "Hello world";
        int cursor = 0;

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.indexOf(" world"), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase2() {
        String text = "Hello world";
        int cursor = text.indexOf(" world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.length(), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase3() {
        String text = "Hello world";
        int cursor = text.indexOf("world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.length(), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase4() {
        String text = "Hello world!!!";
        int cursor = text.indexOf("world");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.indexOf("!!!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase5() {
        String text = "Hello !?. world!";
        int cursor = text.indexOf("!?.");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.indexOf(" world"), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase6() {
        String text = "Hello world!";
        int cursor = text.indexOf("llo");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.indexOf(" world!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase7() {
        String text = "Hello world!";
        int cursor = text.length();

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.length(), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase8() {
        String text = "Hello my world";
        int cursor = text.indexOf("rld");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, Integer.MAX_VALUE, cursor);

        assertEquals(text.length(), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase9() {
        String text = "Hello₽₽⁘⁘⁘, rld!!!"; // testing unicode chars
        int cursor = text.indexOf("llo⁘⁘⁘, rld!!!");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, +1, cursor);

        assertEquals(text.indexOf("₽₽⁘⁘⁘, rld!!!"), newPosition);
    }

    @Test
    public void testMoveCursorToTheRightCase12() {
        String text = "     \n\n     Hello world!!!     \n\n     ";
        int cursor = text.indexOf("Hello");

        int newPosition = AdvancedTextHandler.moveCursorByWords(text, 1, cursor);

        assertEquals(text.indexOf(" world!!!"), newPosition);
    }
}
