package ru.nsu.ccfit.petrov.minesweeper.clparser;

import org.apache.commons.cli.*;

/**
 * The type {@code ClParser} is class for parsing command line argument
 *
 * @author ptrvsrg
 */
public class CLParser
{
    private CommandLine commandLine = null;

    /**
     * Parses command line arguments.
     *
     * @param args command line args
     * @return {@code true} - arguments successfully parsed, {@code false} - help option found, help printed
     */
    public boolean parse(String[] args) {
        // Add available options
        Options opts = new Options();
        opts.addOption(Option.builder()
                             .option("h")
                             .longOpt("help")
                             .desc("Print command help")
                             .build())
            .addOption(Option.builder()
                             .option("m")
                             .longOpt("ui-mode")
                             .desc("User interface mode")
                             .hasArg()
                             .argName("mode")
                             .type(String.class)
                             .build());

        // Create default command parser
        org.apache.commons.cli.CommandLineParser clParser = new DefaultParser();

        // Parse args
        try{
            commandLine = clParser.parse(opts, args);
        } catch (ParseException e) {
            System.out.println(e.getLocalizedMessage());
            printHelp(opts);
        }

        // Print help
        if (commandLine == null || commandLine.hasOption("help")) {
            // Removing parsed option values
            commandLine = null;
            printHelp(opts);
            return false;
        }

        return true;
    }

    private void printHelp(Options opts) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("minesweeper", opts, true);
    }

    /**
     * Gets value of {@code ui-mode} option
     *
     * @return UI mode name
     */
    public String getUIMode() {
        if (commandLine == null) {
            return null;
        }

        return commandLine.getOptionValue("ui-mode");
    }
}
