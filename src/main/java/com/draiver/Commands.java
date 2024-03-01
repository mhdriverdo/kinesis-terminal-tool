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

    private final KinesisService kinesisService;

    public Commands(KinesisService kinesisService) {
        this.kinesisService = kinesisService;
    }

    @ShellMethod("Send contents file to a specific topic.")
    public void send(@ShellOption({"-f", "--file"}) String fileName,
                     @ShellOption({"-t", "--topic"}) String topic) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            System.out.println("Sending contents file name '" + fileName + "' to '" + topic + "'.");
            kinesisService.sendEvent(content, topic);
        } catch (Exception e) {
            System.out.println("Error while sending message: " + e.getMessage());
            System.out.println("Stack:" + Arrays.toString(e.getStackTrace()));
        }
    }

    @ShellMethod("Show commands info.")
    public void info() {
        System.out.println("Usage:");
        System.out.println("  send --file <fileName> --topic <topic> - Send contents file to a specific topic.");
        System.out.println("  listen --topic <topic> - Subscribe and listen to a topic.");
        System.out.println("  info - Show commands info.");
    }

    @ShellMethod("Subscribe and Listen to a specific topic.")
    public void listen(@ShellOption({"-t", "--topic"}) String topicName) {
        System.out.println("Listening topic (stream): " + topicName);
        new Thread(() -> kinesisService.listenToKinesisStream(topicName)).start();
    }

}
