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

	private static final String TO_BUFFER = "-";

	 enum PiCamCmd {
		 
		 
		LOW_RES("raspistill -n -vf -hf -t 1 -w 480 -h 360 -e jpg -o "), 
		HIGH_RES("raspistill -n -vf -hf -t 1 -w 2000 -h 1500 -e jpg -o "), 
		VID("raspivid --nopreview -vs -vf -hf -w 800 -h 600 -t 5000 -o ");

		private final String cmd;

		private PiCamCmd(String cmd) {
			this.cmd = cmd;
		}

		String getCmd() {
			return cmd;
		}
	}

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
	public BufferedImage captureImageToBuffer(PiCamCmd cmd) throws IOException,InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd.getCmd() + TO_BUFFER);

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
	public void captureToFile(PiCamCmd cmd, String fileName)
			throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();

		Process p = rt.exec(cmd.getCmd() + fileName);

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