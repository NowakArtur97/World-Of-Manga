package com.NowakArtur97.WorldOfManga.converter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

@Component
public class ImageToByteConverterImpl implements ImageToByteConverter {

	@Override
	public byte[] convertImageToByte(URL url) throws IOException {

		InputStream inputStream = new BufferedInputStream(url.openStream());
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
