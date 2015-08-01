package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Util {

	private static final String _H264 = ".h264";
	private static final String _JPG = ".jpg";
	private static final String JPEG = "JPEG";
	private static final String SEP = "_";
	private static final String DATE_FORMAT = "ddMMyy_HH_mm_ss";
	
	/**
	 * @param date
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_JPG} suffix.
	 */
	public static String imageName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return Driver.getOutFolder()+sdf.format(date)+_JPG;
	}
	
	/**
	 * @param date
	 * @param number number used in file name
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_JPG} suffix.
	 */
	public static String imageName(int number){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return Driver.getOutFolder()+sdf.format(new Date())+SEP+number+_JPG;
	}
	
	/**
	 * @param date
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_H264} suffix.	 */
	private static String vidName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return Driver.getOutFolder()+sdf.format(date)+_H264;
	}
	
	/**
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_H264} suffix.
	 */
	public static String vidName(){
		return vidName(new Date());
	}
	
	/**
	 * @param img buffered imgae
	 * @param fileName name of file
	 * @throws IOException
	 */
	public static void bufferToFile(BufferedImage img,String fileName) throws IOException{
		ImageIO.setUseCache(true);
		ImageIO.write(img, JPEG, new File(fileName));
	}
}

