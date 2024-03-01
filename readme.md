# Kinesis Terminal Tool

## Overview

This project is a command-line interface (CLI) tool developed with Spring Boot and Spring Shell, designed to send events to an AWS Kinesis topic. It allows users to send the contents of a file located in the `resources` folder to a specified Kinesis topic, making it a versatile tool for testing or interacting with Kinesis streams.

## Features

- **Send Command**: Send the contents of a specified file to an AWS Kinesis topic.
- **Info Command**: Display usage information about the tool.

## Prerequisites

- Java JDK 17 or later.
- Gradle (if building from source).
- An AWS account and AWS CLI configured with access to Kinesis.

## Setup

1. **Clone the Repository**

   ```bash
   git clone https://your-repository-url.git
   cd KinesisTerminalTool

2. **Build the Application**
    ```
   ./gradlew build 
   ```
3. **Running the Application**

To start the application, use the following command:
```bash
java -jar build/libs/kinesis-terminal-tool-0.0.1-SNAPSHOT.jar
```
Once the application is running, you will be presented with a shell prompt where you can type commands.

**Available Commands**
### Send File to Kinesis Topic
```bash
send --file <fileName> --topic <topicName>
```
Replace <fileName> with the name of the file in the resources folder you wish to send, and <topicName> with the name of your Kinesis topic.

### Listen Kinesis Topic
```bash
listen --topic <topicName>
```

### Display Information
```bash
info
```

