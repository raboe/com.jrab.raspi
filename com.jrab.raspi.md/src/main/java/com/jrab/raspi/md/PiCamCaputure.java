package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class PiCamCaputure {
	
	private static final Logger logger = Logger.getLogger(PiCamCaputure.class.getName());

	private static final String TO_BUFFER = "-";
	private static final String RASPISTILL = "raspistill -n -vf -hf -t 1 -w 480 -h 360 -e jpg -o ";
	private static final String RASPISTILL_DETAIL = "raspistill -n -vf -hf -t 1 -w 2000 -h 1500 -e jpg -o ";
//	private static final String RASPIVID = "raspivid --nopreview -vf -hf -w 1280 -h 720 -t 5000 -o ";
//	private static final String RASPISTILL = "raspistill -n --nopreview -vf -hf -q 100 -t 10 -w 400 -h 300 -e jpg -o ";
	private static final String RASPIVID = "raspivid --nopreview -vs -vf -hf -w 800 -h 600 -t 5000 -o ";	
	
	
    private static class LazyHolder {
        private static final PiCamCaputure INSTANCE = new PiCamCaputure();
    }
 
    public static PiCamCaputure getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    private PiCamCaputure(){
    	super();
    }

	public BufferedImage captureImageToBuffer() throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(RASPISTILL + TO_BUFFER);
		
	    BufferedImage bufferedImage = ImageIO.read(p.getInputStream());
	    p.waitFor(5000, TimeUnit.MILLISECONDS);
	    
		return bufferedImage;
	}
	
	public String captureImageToFile(int number) throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
		
		String fileName = Util.imageName(new Date(),number);
		Process p = rt.exec(RASPISTILL_DETAIL + fileName);
		
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
	    
	    return fileName;
	}
	
	public void captureDetailSeries(int number) throws IOException, InterruptedException{
		for(int i=0;i<number;i++){
			captureImageToFile(i);
		}
	}
	
	public void captureVidToFile(Date date) throws IOException, InterruptedException{
		Runtime rt = Runtime.getRuntime();
		
		Process p = rt.exec(RASPIVID + Util.vidName(date));

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