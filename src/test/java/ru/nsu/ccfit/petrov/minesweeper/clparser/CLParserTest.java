package ru.nsu.ccfit.petrov.minesweeper.clparser;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class CLParserTest {

    private final CLParser commandLineParser = new CLParser();

    private static Stream<Arguments> checkInputOutputArgs() {
        return Stream.of(Arguments.of(new String[]{}, null, null),
                         Arguments.of(new String[]{"-m", "text"}, "text"),
                         Arguments.of(new String[]{"--ui-mode", "graphic"}, "graphic"));
    }

    @ParameterizedTest
    @MethodSource("checkInputOutputArgs")
    void checkInputOutput(String[] args, String expected) {
        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertTrue(commandLineParser.parse(args));
            Assertions.assertEquals(commandLineParser.getUIMode(), expected);
        });
    }

    private static Stream<Arguments> checkHelpArgs() {
        return Stream.of(Arguments.of((Object) new String[]{"-h"}),
                         Arguments.of((Object) new String[]{"--help"}));
    }

    @ParameterizedTest
    @MethodSource("checkHelpArgs")
    void checkHelp(String[] args) {
        Assertions.assertDoesNotThrow(() -> Assertions.assertFalse(commandLineParser.parse(args)));
    }

    private static Stream<Arguments> checkExceptionArgs() {
        return Stream.of(Arguments.of(new String[]{"-c"}, UnrecognizedOptionException.class),
                         Arguments.of(new String[]{"--mode"}, UnrecognizedOptionException.class),
                         Arguments.of(new String[]{"--help", "--mode"},
                                      UnrecognizedOptionException.class),
                         Arguments.of(new String[]{"--ui-mode"}, MissingArgumentException.class),
                         Arguments.of(new String[]{"-m"}, MissingArgumentException.class),
                         Arguments.of(new String[]{"--help", "--ui-mode"},
                                      MissingArgumentException.class));
    }

    @ParameterizedTest
    @MethodSource("checkExceptionArgs")
    void checkException(String[] args, Class<? extends Throwable> exception) {
        Assertions.assertThrows(exception, () -> commandLineParser.parse(args));
    }
}