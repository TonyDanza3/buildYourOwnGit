package internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String getHashFromFile(File file) {
        byte[] hashBytes;
        StringBuilder hashString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            hashBytes = md.digest(Files.readAllBytes(Path.of(file.getPath())));
        } catch (NoSuchAlgorithmException | IOException e ) {
            throw new RuntimeException("Could not get hash of file " + file + " because of the following exception: " + e);
        }
        for(byte byt: hashBytes) {
            hashString.append(String.format("%02x", byt));
        }
        return hashString.toString();
    }
}
