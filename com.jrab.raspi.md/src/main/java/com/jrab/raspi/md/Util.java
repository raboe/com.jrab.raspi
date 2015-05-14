package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Util {

	private static final String FILES = "./files/";
	private static final String _H264 = ".h264";
	private static final String _JPG = ".jpg";
	private static final String JPEG = "JPEG";
	private static final String SEP = "_";
	private static final String DATE_FORMAT = "ddMMyy_HH_mm_ss";
	
	public static String imageName(){
		return imageName(new Date());
	}
	
	public static String imageName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return FILES+sdf.format(date)+_JPG;
	}
	
	public static String imageName(Date date,int number){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return FILES+sdf.format(date)+SEP+number+_JPG;
	}
	
	public static String vidName(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return FILES+sdf.format(date)+_H264;
	}
	
	public static String vidName(){
		return vidName(new Date());
	}
	
	public static void bufferToFile(BufferedImage img,String fileName) throws IOException{
		ImageIO.setUseCache(true);
		ImageIO.write(img, JPEG, new File(fileName));
	}
}

