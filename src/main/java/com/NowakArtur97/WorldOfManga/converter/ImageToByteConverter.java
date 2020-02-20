package com.NowakArtur97.WorldOfManga.converter;

import java.io.IOException;
import java.net.URL;

public interface ImageToByteConverter {

	byte[] convertImageToByte(URL url) throws IOException;
}
