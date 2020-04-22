package ru.tinkoff.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class TokenUtils {
    private Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private String TOKEN_PREFIX = "Bearer ";

    private String tokenFileName;

    @Autowired
    public TokenUtils(@Value("${token.fileName}") String tokenFileName) {
        this.tokenFileName = tokenFileName;
    }

    private String readTokenFromFile() throws IOException {
        int size = 0;
        BufferedReader tokenReader = new BufferedReader(new FileReader(tokenFileName));
        final var firstLine = tokenReader.readLine();
        var line = firstLine;
        while (line != null) {
            line = tokenReader.readLine();
            ++size;
        }

        if (size != 1) {
            log.error("Token file " + tokenFileName + " should have one line, but it has " + size + " lines. Will try to use the first line");
        }
        return firstLine;
    }

    public String getToken() throws InterruptedException {
        try {
            return TOKEN_PREFIX + readTokenFromFile();
        } catch (IOException e) {
            log.error("Not able to read token file " + tokenFileName, e);
            throw new InterruptedException();
        }
    }
}
