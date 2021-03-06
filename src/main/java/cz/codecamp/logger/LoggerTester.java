package cz.codecamp.logger;

import cz.codecamp.logger.loggers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoggerTester {

    private static final Map<String, LogLevelEnum> LEVEL_MAP;

    static {
        Map<String, LogLevelEnum> levelMap = new HashMap<>();
        levelMap.put("d", LogLevelEnum.DEBUG);
        levelMap.put("i", LogLevelEnum.INFO);
        levelMap.put("w", LogLevelEnum.WARNING);
        levelMap.put("e", LogLevelEnum.ERROR);
        LEVEL_MAP = Collections.unmodifiableMap(levelMap);
    }

    public static void main(String[] args) {
        /*System.out.println("begin");
        PrintStream stdout = System.out;
        StringBuilder sb = new StringBuilder();
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                sb.append((char) b);
            }

            @Override
            public String toString() {
                return sb.toString();
            }
        };
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        System.out.println("middle");
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println("end");*/



        AbstractLogger logger = new MultiLogger(new JsonLogger(new FileLogger()), new StdoutLogger(), new BunnyLogger(new StdoutLogger(), LogLevelEnum.INFO));
        logger.error("Error");

        for (Scanner scanner = new Scanner(System.in); ; ) {
            System.out.print("> ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String line = scanner.nextLine();
            String[] parts = line.split("\\s+", 2);

            if (parts.length < 2) {
                System.err.println("not enough arguments");
                continue;
            }

            LogLevelEnum level = LEVEL_MAP.get(parts[0].substring(0, 1).toLowerCase());
            if (level == null) {
                System.err.println("unknown level [" + parts[0] + "]");
                continue;
            }

            logger.log(level, parts[1]);
        }
    }

}
