package com.NowakArtur97.WorldOfManga.eventListener;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
class ImageToByteConverter {

    @Value("${world-of-manga.images.path:data/images/}")
    private String imagesFolderPath;

    @Value("${world-of-manga.images.extension:.jpg}")
    private String imagesExtension;

    public byte[] convertImageToByte(String fileName) throws IOException {

        ClassPathResource imageFile = new ClassPathResource(imagesFolderPath + fileName + imagesExtension);
        InputStream inputStream = new BufferedInputStream(imageFile.getInputStream());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];
        int n;

        while (-1 != (n = inputStream.read(buf))) {
            byteArrayOutputStream.write(buf, 0, n);
        }

        byteArrayOutputStream.close();
        inputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
