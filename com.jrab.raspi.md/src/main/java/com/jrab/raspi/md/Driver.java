package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {
	private static final Logger logger = Logger.getLogger(Driver.class.getName());

	private static final int DEFAULT_CAPTURE_DELTA = 500;
	private static final double DEFAULT_THRESHOLD = 1d;//5d;

	public static void main(String args[]) {
		
		File subFolder = new File("./files");
		if(!subFolder.exists()){
			subFolder.mkdir();
		}
		
		int captureDelta;
		double threshold;

		try {
			threshold = Double.valueOf(args[0]);
			captureDelta = Integer.valueOf(args[1]);
			logger.info("Starting with - THRESHOLD(args[0])= " + threshold  + " and CAPTURE_DELTA(args[1])= " + captureDelta + " [ms]!");
		} catch (NumberFormatException e) {
			logger.info("Wrong parameters for THRESHOLD(args[0]) and CAPTURE_DELTA(args[1]) [ms]!");
			logger.info("Using default for THRESHOLD(args[0])= " + DEFAULT_THRESHOLD + " and CAPTURE_DELTA(args[1])= "+DEFAULT_CAPTURE_DELTA + " [ms]!");
			captureDelta = DEFAULT_CAPTURE_DELTA;
			threshold = DEFAULT_THRESHOLD;
		}
		
		ImageConsumer consumer = new ImageConsumer();
		new Thread(consumer).start();
		
		BufferedImage bi1 = null,bi2 = null;
		String fileName1 = null;
		long t1 = 0;
		long t2 = 0; 
		long t_after_image1 = 0;
		while(true){
			try {
				if(bi2!=null){
					t1 = new Date().getTime();
					long delta = DEFAULT_CAPTURE_DELTA - (t1-t_after_image1);
					if(delta>0){
						logger.info("... waiting " + delta + " ms");
						Thread.sleep(delta);
					}
				}
				Date d1 = new Date();
				fileName1 = Util.imageName(d1);
				t1 = d1.getTime();
				bi1 = PiCamCaputure.getInstance().captureImageToBuffer();
				t_after_image1 = new Date().getTime();
				logger.info("Capture image: " + (t_after_image1-t1) + " ms with resolution: " + bi1.getWidth() + "-" + bi1.getHeight());
				
				if(bi2!=null){
					
					d1 = new Date();
					t1 = new Date().getTime();
					double deviation = ImageComparator.getInstance().compareRGBByArea(bi1,bi2,threshold);
					t2 = new Date().getTime();
					logger.info("Comparing images: " + (t2-t1) + " ms");
					
//					
//					d1 = new Date();
//					t1 = new Date().getTime();
//					deviation = ImageComparator.getInstance().compareRGB(bi1,bi2);
//					t2 = new Date().getTime();
//					logger.info("Compare images: " + (t2-t1) + " ms");					

					if(deviation > threshold){
						logger.info("Motion detected with deviation of " + deviation);
						d1 = new Date();
						t1 = new Date().getTime();
						//PiCamCaputure.getInstance().captureVidToFile(d1);
						PiCamCaputure.getInstance().captureDetailSeries(3);
						t2 = new Date().getTime();
//						logger.info("Capture vid: " + (t2-t1) + " ms");
						
						consumer.addImage(fileName1,bi1);
					}else{
						logger.info("Current deviation is " + deviation);	
					}
					bi2 = null;
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE,e.getMessage());
			} finally{
				bi2 = bi1;
			}
		}
	}
}