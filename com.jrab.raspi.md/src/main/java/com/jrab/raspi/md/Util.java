package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.jrab.raspi.md.Driver.AppKey;

public class Util {

	private static final String _H264 = ".h264";
	private static final String _JPG = ".jpg";
	private static final String JPEG = "JPEG";
	private static final String SEP = "_";
	private static final String DATE_FORMAT = "ddMMyy_HH_mm_ss";
	private static final String OUT = Driver.getAppProp(AppKey.OUTPUT_FOLDER); 
	
	/**
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_JPG} suffix
	 */
	public static String imageName(){
		return imageName(new Date());
	}
	
	/**
	 * @param date
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_JPG} suffix.
	 */
	public static String imageName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return OUT+sdf.format(date)+_JPG;
	}
	
	/**
	 * @param date
	 * @param number number used in file name
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_JPG} suffix.
	 */
	public static String imageName(Date date,int number){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return OUT+sdf.format(date)+SEP+number+_JPG;
	}
	
	/**
	 * @param date
	 * @return {@value #FILES}{@value #DATE_FORMAT} formatted with {@value #_H264} suffix.	 */
	public static String vidName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return OUT+sdf.format(date)+_H264;
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

