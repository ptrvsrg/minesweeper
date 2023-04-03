package ru.nsu.ccfit.petrov.minesweeper.clparser;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CLParserTest
    extends Assertions {

    private CLParser clParser;

    @BeforeClass
    public void beforeClass() {
        clParser = new CLParser();
    }

    @DataProvider(name = "Create args and return value")
    private Object[][] createArgsAndReturnValue() {
        return new Object[][]{new Object[]{new String[]{"-m", "GUI"}, true},
                              new Object[]{new String[]{"-m", "text"}, true},
                              new Object[]{new String[]{"-m", "invisible"}, true},

                              new Object[]{new String[]{"-m=GUI"}, true},
                              new Object[]{new String[]{"-m=text"}, true},
                              new Object[]{new String[]{"-m=invisible"}, true},

                              new Object[]{new String[]{"-mGUI"}, true},
                              new Object[]{new String[]{"-mtext"}, true},
                              new Object[]{new String[]{"-minvisible"}, true},

                              new Object[]{new String[]{"-m=GUI"}, true},
                              new Object[]{new String[]{"-m=text"}, true},
                              new Object[]{new String[]{"-m=invisible"}, true},

                              new Object[]{new String[]{"--ui-mode", "GUI"}, true},
                              new Object[]{new String[]{"--ui-mode", "text"}, true},
                              new Object[]{new String[]{"--ui-mode", "invisible"}, true},

                              new Object[]{new String[]{"--ui-mode=GUI"}, true},
                              new Object[]{new String[]{"--ui-mode=text"}, true},
                              new Object[]{new String[]{"--ui-mode=invisible"}, true},

                              new Object[]{new String[]{"-h"}, false},
                              new Object[]{new String[]{"--help"}, false},
                              new Object[]{new String[]{"-h"}, false},
                              new Object[]{new String[]{"--help"}, false},
                              new Object[]{new String[]{"--help", "--ui-mode", "GUI"}, false},

                              new Object[]{new String[]{"--help", "--option"}, false},
                              new Object[]{new String[]{"-m"}, false},
                              new Object[]{new String[]{"--ui-mode"}, false},
                              new Object[]{new String[]{"--ui-mode", "GUI", "--option"}, false},
                              new Object[]{new String[]{"-o"}, false},
                              new Object[]{new String[]{"--option"}, false}};
    }

    @Test(dataProvider = "Create args and return value", description = "Check return value")
    public void checkReturnValue(String[] args, boolean returnValue) {
        // do
        boolean actual = clParser.parse(args);

        // check
        assertThat(actual).isEqualTo(returnValue);
    }

    @DataProvider(name = "Create args and option value")
    private Object[][] createArgsAndOptionValue() {
        return new Object[][]{new Object[]{new String[]{"-m", "GUI"}, "GUI"},
                              new Object[]{new String[]{"-m", "text"}, "text"},
                              new Object[]{new String[]{"-m", "invisible"}, "invisible"},

                              new Object[]{new String[]{"-m=GUI"}, "GUI"},
                              new Object[]{new String[]{"-m=text"}, "text"},
                              new Object[]{new String[]{"-m=invisible"}, "invisible"},

                              new Object[]{new String[]{"-mGUI"}, "GUI"},
                              new Object[]{new String[]{"-mtext"}, "text"},
                              new Object[]{new String[]{"-minvisible"}, "invisible"},

                              new Object[]{new String[]{"-m=GUI"}, "GUI"},
                              new Object[]{new String[]{"-m=text"}, "text"},
                              new Object[]{new String[]{"-m=invisible"}, "invisible"},

                              new Object[]{new String[]{"--ui-mode", "GUI"}, "GUI"},
                              new Object[]{new String[]{"--ui-mode", "text"}, "text"},
                              new Object[]{new String[]{"--ui-mode", "invisible"}, "invisible"},

                              new Object[]{new String[]{"--ui-mode=GUI"}, "GUI"},
                              new Object[]{new String[]{"--ui-mode=text"}, "text"},
                              new Object[]{new String[]{"--ui-mode=invisible"}, "invisible"},

                              new Object[]{new String[]{"-h"}, null},
                              new Object[]{new String[]{"--help"}, null},
                              new Object[]{new String[]{"--help", "--ui-mode", "GUI"}, null},

                              new Object[]{new String[]{"--help", "value"}, null},
                              new Object[]{new String[]{"--help", "--option"}, null},
                              new Object[]{new String[]{"-m"}, null},
                              new Object[]{new String[]{"--ui-mode"}, null},
                              new Object[]{new String[]{"--ui-mode", "GUI", "--option"}, null},
                              new Object[]{new String[]{"-o"}, null},
                              new Object[]{new String[]{"--option"}, null}};
    }

    @Test(dataProvider = "Create args and option value", description = "Check option value")
    public void checkOptionValue(String[] args, String optionValue) {
        // do
        clParser.parse(args);
        String actual = clParser.getUIMode();

        // check
        assertThat(actual).isEqualTo(optionValue);
    }
}