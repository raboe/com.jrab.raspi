package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ImageComparator {
	private static final Logger logger = Logger.getLogger(ImageComparator.class.getName());
	
	private static final int _0XFF = 0xff;
	private static final BigDecimal AREA_LENGTH_FACTOR = new BigDecimal(new Double(10d/110d)) ;
	
	private List<AreaPoint> points = null;	
	
    private static class LazyHolder {
        private static final ImageComparator INSTANCE = new ImageComparator();
    }
 
    public static ImageComparator getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    private ImageComparator(){
    	super();
    }
	
	
	/**
	 * @param im1
	 * @param im2
	 * @return difference of green channel in percent 
	 */
	public double compareGreen(File im1,File im2)throws Exception{
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		try {
			img1 = ImageIO.read(im1);
			img2 = ImageIO.read(im2);
		} catch (IOException e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
		return compareGreen(img1, img2);
	}
	
	/**
	 * @param im1
	 * @param im2
	 * @return difference of green channel in percent
	 * @throws Exception
	 */
	public double compareGreen(BufferedImage im1,BufferedImage im2)throws Exception{
		checkSize(im1,im2);
		long diffGreen = 0;
		for (int y = 0; y < im1.getHeight(); y++) {
			for (int x = 0; x < im1.getWidth(); x++) {
				int rgb1 = im1.getRGB(x, y);
				int rgb2 = im2.getRGB(x, y);

				int green1 = (rgb1 >> 8) & _0XFF;
				int green2 = (rgb2 >> 8) & _0XFF;
				diffGreen += Math.abs(green1 - green2);
			}
		}
		double pGreen = 100 * diffGreen/(im1.getWidth() * im1.getHeight())/255.0;
		return Math.floor(pGreen);
	}
	
	public double compareRGB(BufferedImage im1,BufferedImage im2)throws Exception{
		checkSize(im1,im2);
		return compareRGBInternal(im1, im2);
	}
	
	public double compareRGBByArea(BufferedImage im1,BufferedImage im2,double threshold)throws Exception{
		checkSize(im1,im2);
		double deviation = 0;
		
		if(points==null){
			points = findAreaPoints(im1.getWidth(),im1.getHeight());	
		}
		
		if(points.isEmpty()){
			deviation = compareRGBInternal(im1,im2);
		}else{
			for(int i=0;i<points.size();i++){
				AreaPoint point = points.get(i);
				double cDeviation = compareRGBByArea(im1,im2,point);
				deviation = deviation>cDeviation?deviation:cDeviation;
				if(deviation>=threshold){
					logger.info("Deviation for " + point.toString() + ": " + cDeviation);
					break;
				}
			}
		}
		return Math.floor(deviation);
	}
	
	private double compareRGBInternal(BufferedImage im1,BufferedImage im2)throws Exception{
		int width = im1.getWidth();
		int height = im1.getHeight();
		long diff = 0;
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				try{
				int rgb1 = im1.getRGB(x, y);
				int rgb2 = im2.getRGB(x, y);

				int green1 = (rgb1 >> 8) & _0XFF;
				int green2 = (rgb2 >> 8) & _0XFF;

				int red1 = (rgb1 >> 16) & _0XFF;
		        int red2 = (rgb2 >> 16) & _0XFF;
		        
		        int blue1 = (rgb1) & _0XFF;
		        int blue2 = (rgb2) & _0XFF;
		        diff += Math.abs(red1 - red2);
		        diff += Math.abs(green1 - green2);
		        diff += Math.abs(blue1 - blue2);
				}catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		double n = width * height * 3;
		double p = 100 * diff/n/255.0;
		return Math.floor(p);
	}
	
	
	private double compareRGBByArea(BufferedImage im1,BufferedImage im2,AreaPoint area)throws Exception{
		int areaLength = new BigDecimal(im1.getWidth()).multiply(AREA_LENGTH_FACTOR).intValue();
		
		int wStart = area.xs;
		int hStart = area.ys;
		
		long diff = 0;
		for (int y = wStart; y < (wStart+areaLength); y++) {
			for (int x = hStart; x < (hStart+areaLength); x++) {
				try{
					int rgb1 = im1.getRGB(x, y);
					int rgb2 = im2.getRGB(x, y);
					
					int green1 = (rgb1 >> 8) & _0XFF;
					int green2 = (rgb2 >> 8) & _0XFF;

					int red1 = (rgb1 >> 16) & _0XFF;
			        int red2 = (rgb2 >> 16) & _0XFF;
			        
			        int blue1 = (rgb1) & _0XFF;
			        int blue2 = (rgb2) & _0XFF;
			        diff += Math.abs(red1 - red2);
			        diff += Math.abs(green1 - green2);
			        diff += Math.abs(blue1 - blue2);
				}catch(ArrayIndexOutOfBoundsException e){}
			}
		}
		double n = areaLength * areaLength * 3;
		double p = 100 * diff/n/255.0;
		return Math.floor(p);
	}
	
	private List<AreaPoint> findAreaPoints(int imageWidth,int imageHeight){
		int deltaX = imageWidth/10;
		int deltaY = imageHeight/10;
		logger.info("------------- setting up sensors -------------");
		logger.info("single size: [" + deltaX + "," + deltaY +"]");
		
		List<AreaPoint> sensors = new ArrayList<AreaPoint>();
		
		int ws = deltaX;
		int hs = deltaY;
		
		int counter = 0;
		while((ws+deltaX) <= imageWidth-deltaX){
			while((hs+deltaY) <= imageHeight-deltaY){
				AreaPoint cArea = new AreaPoint(ws,hs);
				if(!sensors.contains(cArea)){
					logger.info("at: [" + ws + "," + hs + "]");
					sensors.add(cArea);
				}
				hs = hs + 2*deltaY;
			}
			if(counter%2!=0){
				hs = deltaY;
			}else{
				hs = 2*deltaY;
			}
			ws = ws + deltaX;
			counter++;
		}
		logger.info(sensors.size() + " senor areas created");
		logger.info("----------------------------------------------");
		
		return sensors;
	}
	
	private void checkSize(BufferedImage im1,BufferedImage im2)throws Exception{
		int width1 = im1.getWidth(null);
		int width2 = im2.getWidth(null);
		int height1 = im1.getHeight(null);
		int height2 = im1.getHeight(null);
		if ((width1 != width2) || (height1 != height2)) {
			throw new Exception("Sizes of images to be compared don´t match!");
		}
	}
	
	class AreaPoint{
		int xs,ys;
		private AreaPoint(int xs,int ys){
			this.xs = xs;
			this.ys = ys;
		}
		
		@Override
		public boolean equals(Object other){
			if(other==null)return false;
			AreaPoint otherArea = (AreaPoint)other;
			return this.xs==otherArea.xs&&this.ys==otherArea.ys;
		}
		
		@Override
		public String toString(){
			return xs+"-"+ys;
		}
	}
}