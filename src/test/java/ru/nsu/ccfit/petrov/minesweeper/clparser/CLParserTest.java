package ru.nsu.ccfit.petrov.minesweeper.clparser;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CLParserTest {

    private CLParser clParser;

    @BeforeMethod
    public void setUp() {
        clParser = new CLParser();
    }

    @DataProvider(name = "create args and return value")
    public static Object[][] createArgsAndReturnValue() {
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

                              new Object[]{new String[]{"--help", "value"}, false},
                              new Object[]{new String[]{"--help", "--option"}, false},
                              new Object[]{new String[]{"-m"}, false},
                              new Object[]{new String[]{"--ui-mode"}, false},
                              new Object[]{new String[]{"--ui-mode", "GUI", "value"}, false},
                              new Object[]{new String[]{"--ui-mode", "GUI", "--option"}, false},
                              new Object[]{new String[]{"-o"}, false},
                              new Object[]{new String[]{"--option"}, false}};
    }

    @Test(dataProvider = "create args and return value", description = "check return value")
    public void checkReturnValue(String[] args, boolean returnValue) {
        Assert.assertEquals(clParser.parse(args), returnValue);
    }

    @DataProvider(name = "create args and option value")
    public static Object[][] createArgsAndOptionValue() {
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

    @Test(dataProvider = "create args and option value", description = "check option value")
    public void checkOptionValue(String[] args, String optionValue) {
        clParser.parse(args);
        String actual = clParser.getUIMode();
        Assert.assertEquals(actual, optionValue);
    }
}