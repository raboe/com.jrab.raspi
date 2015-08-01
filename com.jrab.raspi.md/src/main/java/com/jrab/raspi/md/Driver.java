package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver {
	private static final Logger logger = Logger.getLogger(Driver.class.getName());
	
	private static final String CONFIG_FILE = "md.cfg";
	
	private static final String CAPTURE_DELTA ="CAPTURE_DELTA"; 
	private static final String THRESHOLD = "THRESHOLD";
	private static final String IMAGE_COUNT = "IMAGE_COUNT";
	private static final String OUTPUT_FOLDER = "OUTPUT_FOLDER";
	private static final String RECORD_VID = "RECORD_VID";
	private static final String LOW_RES_CMD = "LOW_RES_CMD";
	private static final String HIGH_RES_CMD = "HIGH_RES_CMD";
	private static final String VID_CMD = "VID_CMD";
	
	
	private static Properties appProps;
	
	public static void main(String args[]) {
		appProps = loadProperties();
		prepareFilesFolder(getAppProp(OUTPUT_FOLDER));
		
		startupLog();
		
		int captureDelta = Integer.valueOf(getAppProp(CAPTURE_DELTA));
		int imageCounte = Integer.valueOf(getAppProp(IMAGE_COUNT));
		double threshold = Double.valueOf(getAppProp(THRESHOLD));
		boolean recordVids = Boolean.valueOf(getAppProp(RECORD_VID));
		
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
						logger.info("waiting\t [" + delta + " ms]");
						Thread.sleep(delta);
					}
				}
				
				Date d1 = new Date();
				fileName1 = Util.imageName(d1);
				t1 = d1.getTime();
				
				bi1 = PiCamCaputure.getInstance().captureImageToBuffer(getAppProp(LOW_RES_CMD));
				t_after_image1 = new Date().getTime();
				logger.info("capture\t [" + (t_after_image1-t1) + " ms]");
				
				if(bi2!=null){
					
					d1 = new Date();
					t1 = new Date().getTime();
					double deviation = ImageComparator.getInstance().compareRGBByArea(bi1,bi2,threshold);
					t2 = new Date().getTime();
					logger.info("comparing\t [" + (t2-t1) + " ms]");
					
					if(deviation >= threshold){
						logger.info("--> Motion detected: deviation " + deviation);
						if(recordVids){
							PiCamCaputure.getInstance().captureToFile(getAppProp(VID_CMD),Util.vidName());							
						}else{
							consumer.addImage(fileName1,bi1);						
							for(int i=0;i<imageCounte;i++){
								BufferedImage detailImage = PiCamCaputure.getInstance().captureImageToBuffer(getAppProp(HIGH_RES_CMD));
								consumer.addImage(Util.imageName(i),detailImage);
							}	
						}
					}else{
						logger.info("deviation\t " + deviation);	
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
		
		defaultProps.put(CAPTURE_DELTA,"1000");
		defaultProps.put(THRESHOLD,"10");
		defaultProps.put(IMAGE_COUNT,"3");
		defaultProps.put(OUTPUT_FOLDER,"./files/");
		defaultProps.put(RECORD_VID,"false");
		defaultProps.put(LOW_RES_CMD,"raspistill -n -t 1 -w 480 -h 360 -e jpg -o"); 
		defaultProps.put(HIGH_RES_CMD,"raspistill -n -t 1 -w 2000 -h 1500 -e jpg -o"); 
		defaultProps.put(VID_CMD,"raspivid --nopreview -vs -w 800 -h 600 -t 5000 -o");
		
		Properties appProps = new Properties(defaultProps);
		InputStream input = null;

		try {
			input = new FileInputStream(CONFIG_FILE);
			appProps.load(input);
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
	
	public static String getAppProp(String key){
		return appProps.getProperty(key);
	}
	
	static String getOutFolder(){
		return getAppProp(OUTPUT_FOLDER);
	}
	
	private static void startupLog(){
		logger.info("----------- PiCam Motion Detection -----------");
		logger.info("github https://github.com/raboe/com.jrab.raspi");
		logger.info("----------------------------------------------");
		
		logger.info(CAPTURE_DELTA + ":\t" + getAppProp(CAPTURE_DELTA) + " ms");
		logger.info(THRESHOLD + ":\t" + getAppProp(THRESHOLD) + " %");
		logger.info(IMAGE_COUNT  + ":\t" + getAppProp(IMAGE_COUNT));
		logger.info(OUTPUT_FOLDER  + ":\t" + getAppProp(OUTPUT_FOLDER));
		logger.info(RECORD_VID + ":\t" + getAppProp(RECORD_VID));
		
		logger.info("--------------- PiCam commands ---------------");		
		logger.info(HIGH_RES_CMD + ":\t" + getAppProp(HIGH_RES_CMD));		
		logger.info(LOW_RES_CMD + ":\t" + getAppProp(LOW_RES_CMD));
		logger.info(VID_CMD + ":\t\t" + getAppProp(VID_CMD));
		logger.info("----------------------------------------------");
	}
}