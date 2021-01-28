package com.NowakArtur97.WorldOfManga.converter;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageToByteConverter {

    public byte[] convertImageToByte(String fileName) throws IOException {

        ClassPathResource imageFile = new ClassPathResource("data/images/" + fileName + ".jpg");
        InputStream inputStream = new BufferedInputStream(imageFile.getInputStream());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int n = 0;

        while (-1 != (n = inputStream.read(buf))) {
            byteArrayOutputStream.write(buf, 0, n);
        }

        byteArrayOutputStream.close();
        inputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
