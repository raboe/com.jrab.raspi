package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class PiCamCaputure {

	private static final Logger logger = Logger.getLogger(PiCamCaputure.class.getName());

	private static final String TO_BUFFER = " -";

	private static class LazyHolder {
		private static final PiCamCaputure INSTANCE = new PiCamCaputure();
	}

	public static PiCamCaputure getInstance() {
		return LazyHolder.INSTANCE;
	}

	private PiCamCaputure() {
		super();
	}

	/**
	 * @param cmd
	 * @return image
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public BufferedImage captureImageToBuffer(String cmd) throws IOException,InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd + TO_BUFFER);

		BufferedImage bufferedImage = ImageIO.read(p.getInputStream());
		p.waitFor(5000, TimeUnit.MILLISECONDS);

		return bufferedImage;
	}

	/**
	 * @param cmd
	 * @param fileName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void captureToFile(String cmd, String fileName)
			throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();

		Process p = rt.exec(cmd + fileName);

		BufferedReader inputReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = inputReader.readLine()) != null) {
			logger.info(line);
		}
		inputReader.close();

		BufferedReader errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		while ((line = errorReader.readLine()) != null) {
			logger.severe(line);
		}
		errorReader.close();

		p.waitFor(5000, TimeUnit.MILLISECONDS);
	}
}