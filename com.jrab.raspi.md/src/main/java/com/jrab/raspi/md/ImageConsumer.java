package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ImageConsumer implements Runnable {
	
	private static final Logger logger = Logger.getLogger(ImageConsumer.class.getName());
	
	private final BlockingQueue<ImageData> queue;
	
	public ImageConsumer(){
		queue = new ArrayBlockingQueue<ImageData>(10);
	}
	
	/**
	 * @param fileName
	 * @param image image to add to queue
	 */
	public void addImage(String fileName,BufferedImage image){
		try {
			queue.put(new ImageData(fileName,image));
			logger.info("added image " + fileName);
		} catch (InterruptedException e) {
			logger.severe(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		long t1,t2; 
		ImageData imageData;
		while(true){
			try {
				logger.info("... waiting for next image!");
				imageData = queue.take();
				logger.info("took image " + imageData.getName());
				t1 = new Date().getTime();
				Util.bufferToFile(imageData.getImage(),imageData.getName());
				t2 = new Date().getTime();
				logger.info("Saving image " + imageData.getName() + ": " + (t2-t1) + " ms");
			} catch (Exception e) {
				logger.severe(e.getMessage());
			}
		}
	}
	
	class ImageData {
		
		private String name;
		private BufferedImage image;
		
		ImageData(String name,BufferedImage image){
			this.name = name;
			this.image = image;
		}
		
		String getName(){
			return name;
		}
		
		BufferedImage getImage(){
			return image;
		}
	}
}