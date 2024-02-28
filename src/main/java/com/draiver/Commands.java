package com.draiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@ShellComponent
public class Commands {

    private static final Logger logger = LoggerFactory.getLogger(Commands.class);

    private final KinesisService kinesisService;

    public Commands(KinesisService kinesisService) {
        this.kinesisService = kinesisService;
    }

    @ShellMethod("Send contents file to a specific topic.")
    public void send(@ShellOption({"-f", "--file"}) String fileName,
                     @ShellOption({"-t", "--topic"}) String topic) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            logger.info("Sending contents file name '" + fileName + "' to '" + topic + "'.");
            kinesisService.sendEvent(content, topic);
        } catch (Exception e) {
            logger.error("Error while sending message: " + e.getMessage());
            logger.error("Stack:" + Arrays.toString(e.getStackTrace()));
        }
    }

    @ShellMethod("Show commands info.")
    public void info() {
        logger.info("Usage:");
        logger.info("  send --file <fileName> --topic <topic> - Send contents file to a specific topic.");
        logger.info("  info - Show commands info.");
    }
}
