package com.jrab.raspi.md;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestImageCompare{

    @Test
//    @Ignore
    public void compareSameImage(){
    	System.out.println("-------------------- compareSameImage");
    	try {
    		File im1 = new File(getClass().getResource("image1.jpg").toURI());
    		File im2 = new File(getClass().getResource("image1.jpg").toURI());
    		
    		double result = ImageComparator.getInstance().compareGreen(im1, im2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be no differences!",0d == result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareSameImageAsBuffer(){
    	System.out.println("-------------------- compareSameImageAsBuffer");
    	try {
    		File im1 = new File(getClass().getResource("image1.jpg").toURI());
    		File im2 = new File(getClass().getResource("image1.jpg").toURI());
    		
    		BufferedImage bi1 = ImageIO.read(new FileInputStream(im1));
    		BufferedImage bi2 = ImageIO.read(new FileInputStream(im2));
    		
    		double result = ImageComparator.getInstance().compareGreen(bi1, bi2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be no differences!",0d == result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent1(){
    	System.out.println("-------------------- compareDifferent1");
    	try {
    		File im1 = new File(getClass().getResource("image3.jpg").toURI());
    		File im2 = new File(getClass().getResource("image2.jpg").toURI());
   		
    		double result = ImageComparator.getInstance().compareGreen(im1, im2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent1AsBuffer(){
    	System.out.println("-------------------- compareDifferent1AsBuffer");
    	try {
    		File im1 = new File(getClass().getResource("image3.jpg").toURI());
    		File im2 = new File(getClass().getResource("image2.jpg").toURI());
   		
    		BufferedImage bi1 = ImageIO.read(new FileInputStream(im1));
    		BufferedImage bi2 = ImageIO.read(new FileInputStream(im2));
    		
    		double result = ImageComparator.getInstance().compareGreen(bi1, bi2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent2(){
    	System.out.println("-------------------- compareDifferent2");
    	try {
    		File im1 = new File(getClass().getResource("image3.jpg").toURI());
    		File im2 = new File(getClass().getResource("image4.jpg").toURI());
    		
    		double result = ImageComparator.getInstance().compareGreen(im1, im2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent2AsBuffer(){
    	System.out.println("-------------------- compareDifferent2AsBuffer");
    	try {
    		File im1 = new File(getClass().getResource("image3.jpg").toURI());
    		File im2 = new File(getClass().getResource("image4.jpg").toURI());
    		
    		BufferedImage bi1 = ImageIO.read(new FileInputStream(im1));
    		BufferedImage bi2 = ImageIO.read(new FileInputStream(im2));
    		
    		double result = ImageComparator.getInstance().compareGreen(bi1, bi2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent3(){
    	System.out.println("-------------------- compareDifferent3");
    	try {
    		File im1 = new File(getClass().getResource("image5.jpg").toURI());
    		File im2 = new File(getClass().getResource("image4.jpg").toURI());
    		
    		double result = ImageComparator.getInstance().compareGreen(im1, im2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
    
    @Test
//    @Ignore
    public void compareDifferent3AsBuffer(){
    	System.out.println("-------------------- compareDifferent3AsBuffer");
    	try {
    		File im1 = new File(getClass().getResource("image5.jpg").toURI());
    		File im2 = new File(getClass().getResource("image4.jpg").toURI());
    		
    		BufferedImage bi1 = ImageIO.read(new FileInputStream(im1));
    		BufferedImage bi2 = ImageIO.read(new FileInputStream(im2));
    		
    		double result = ImageComparator.getInstance().compareGreen(bi1, bi2);
	    	System.out.println("----> " + result + " %");
	    	assertTrue("There should be differences!",0d < result);
		} catch (Exception e) {}
    }
}