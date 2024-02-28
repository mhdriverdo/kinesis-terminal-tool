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

    @ShellMethod("Envía el contenido de un archivo a un tópico de Kinesis.")
    public void send(@ShellOption({"-f", "--file"}) String fileName,
                     @ShellOption({"-t", "--topic"}) String topic) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            logger.info("Enviando contenido del archivo '" + fileName + "' al tópico '" + topic + "'.");
            kinesisService.sendEvent(content, topic);
        } catch (Exception e) {
            logger.error("Error al enviar el evento: " + e.getMessage());
            logger.error("Stack:" + Arrays.toString(e.getStackTrace()));
        }
    }

    @ShellMethod("Muestra información de uso.")
    public void info() {
        logger.info("Uso:");
        logger.info("  send --file <nombreArchivo> --topic <topic> - Envia el contenido del archivo al tópico especificado.");
        logger.info("  info - Muestra esta información de uso.");
    }
}
