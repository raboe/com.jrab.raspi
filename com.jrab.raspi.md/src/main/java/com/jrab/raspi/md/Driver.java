package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import com.jrab.raspi.md.PiCamCaputure.PiCamCmd;

public class Driver {
	private static final Logger logger = Logger.getLogger(Driver.class.getName());
	
	private static final String CONFIG_FILE = "md.cfg";
	
	enum AppKey {
		 CAPTURE_DELTA, 
		 THRESHOLD, 
		 IMAGE_COUNT,
		 OUTPUT_FOLDER,
		 RECORD_VID;
	}
	
	private static Properties appProps;
	
	public static void main(String args[]) {
		logger.info("Visit: https://github.com/raboe/com.jrab.raspi");
		
		appProps = loadProperties();
		prepareFilesFolder(appProps.getProperty(AppKey.OUTPUT_FOLDER.name()));
		
		int captureDelta = Integer.valueOf(appProps.getProperty(AppKey.CAPTURE_DELTA.name()));
		int imageCounte = Integer.valueOf(appProps.getProperty(AppKey.IMAGE_COUNT.name()));
		double threshold = Double.valueOf(appProps.getProperty(AppKey.THRESHOLD.name()));
		boolean recordVids = Boolean.valueOf(appProps.getProperty(AppKey.RECORD_VID.name()));
		
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
					long delta = captureDelta - (t1-t_after_image1);
					if(delta>0){
						logger.info("... waiting " + delta + " ms");
						Thread.sleep(delta);
					}
				}
				Date d1 = new Date();
				fileName1 = Util.imageName(d1);
				t1 = d1.getTime();
				bi1 = PiCamCaputure.getInstance().captureImageToBuffer(PiCamCmd.LOW_RES);
				t_after_image1 = new Date().getTime();
				logger.info("Capture image: " + (t_after_image1-t1) + " ms with resolution: " + bi1.getWidth() + "-" + bi1.getHeight());
				
				if(bi2!=null){
					
					d1 = new Date();
					t1 = new Date().getTime();
					double deviation = ImageComparator.getInstance().compareRGBByArea(bi1,bi2,threshold);
					t2 = new Date().getTime();
					logger.info("Comparing images: " + (t2-t1) + " ms");
					
					if(deviation > threshold){
						logger.info("Motion detected with deviation of " + deviation);
						if(recordVids){
							PiCamCaputure.getInstance().captureToFile(PiCamCmd.VID,Util.vidName());							
						}else{
							consumer.addImage(fileName1,bi1);						
							for(int i=0;i<imageCounte;i++){
								BufferedImage detailImage = PiCamCaputure.getInstance().captureImageToBuffer(PiCamCmd.HIGH_RES);
								consumer.addImage(Util.imageName(i),detailImage);
							}	
						}
					}else{
						logger.info("Current deviation is " + deviation);	
					}
					bi2 = null;
				}
			} catch (Exception e) {
				logger.severe(e.getMessage());
			} finally{
				bi2 = bi1;
			}
		}
	}
	
	private static Properties loadProperties() {
		Properties defaultProps = new Properties();
		
		defaultProps.put(AppKey.CAPTURE_DELTA.name(),"1000");
		defaultProps.put(AppKey.THRESHOLD.name(),"10");
		defaultProps.put(AppKey.IMAGE_COUNT.name(),"3");
		defaultProps.put(AppKey.OUTPUT_FOLDER.name(),"./files/");
		defaultProps.put(AppKey.RECORD_VID.name(),"false");
		
		Properties appProps = new Properties(defaultProps);
		InputStream input = null;

		try {
			input = new FileInputStream(CONFIG_FILE);
			appProps.load(input);

			logger.info(AppKey.CAPTURE_DELTA + " set to " + appProps.getProperty(AppKey.CAPTURE_DELTA.name()) + " ms");
			logger.info(AppKey.THRESHOLD + " set to " + appProps.getProperty(AppKey.THRESHOLD.name()) + " %");
			logger.info(AppKey.IMAGE_COUNT  + " set to " + appProps.getProperty(AppKey.IMAGE_COUNT.name()));
			logger.info(AppKey.OUTPUT_FOLDER  + " set to " + appProps.getProperty(AppKey.OUTPUT_FOLDER.name()));
			logger.info(AppKey.RECORD_VID + " set to " + appProps.getProperty(AppKey.RECORD_VID.name()));

		} catch (IOException ex) {
			logger.severe(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.severe(e.getMessage());
				}
			}
		}
		return appProps;
	}
	
	private static void prepareFilesFolder(String outputFolder){
		File subFolder = new File(outputFolder);
		if(!subFolder.exists()){
			subFolder.mkdir();
		}
	}
	
	public static String getAppProp(AppKey key){
		return appProps.get(key.name()).toString();
	}
}