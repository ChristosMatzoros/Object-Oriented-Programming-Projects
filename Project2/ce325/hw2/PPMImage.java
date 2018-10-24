package ce325.hw2;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

//an image with PPM format
public class PPMImage extends RGBImage{

	//create a PPMImage from a file
	public PPMImage(java.io.File file) throws UnsupportedFileFormatException{
		super(0,0,0);

		try{
			Scanner sc = new Scanner(file);
			//check the magic number
			if(!sc.next().equals("P3")){
				throw new UnsupportedFileFormatException("Wrong Magic Number");
			}

			setWidth(Integer.parseInt(sc.next()));
			if(getWidth() <= 0){
				throw new UnsupportedFileFormatException("Image Width smaller or equal to zero");
			}

			setHeight(Integer.parseInt(sc.next()));
			if(getHeight() <= 0){
				throw new UnsupportedFileFormatException("Image Height smaller or equal to zero");
			}

			setColorDepth(Integer.parseInt(sc.next()));
			if(getColorDepth() < 0){
				throw new UnsupportedFileFormatException("Image Colordepth smaller than zero");
			}

			pixelArray = new RGBPixel[getHeight()][getWidth()];
			for(int i = 0; i < getHeight(); i++){
				for(int j = 0; j < getWidth(); j++){
					pixelArray[i][j] = new RGBPixel((short)0, (short)0, (short)0);
				}
			}

			//check if the RGB values needed to describe the image exist in the file
			for(int i = 0; i < getHeight(); i++){
				for(int j = 0; j < getWidth(); j++){
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of RGB Values");
					}
					pixelArray[i][j].setRed((short)Integer.parseInt(sc.next()));
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of RGB Values");
					}
					pixelArray[i][j].setGreen((short)Integer.parseInt(sc.next()));
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of RGB Values");
					}
					pixelArray[i][j].setBlue((short)Integer.parseInt(sc.next()));
				}
			}
		}
		catch(java.io.FileNotFoundException ex) {
			System.out.println("A FileNotFoundException occured");
			System.out.println(ex.toString());
		}
	}

	//create a PPMImage out of an RGB image
	public PPMImage(RGBImage img){
		super(img.getWidth(), img.getHeight(), img.getColorDepth());
		this.pixelArray = new RGBPixel[this.getHeight()][this.getWidth()];
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++){
				this.pixelArray[i][j] = new RGBPixel(img.pixelArray[i][j]);
			}
		}
	}

	//create a PPMImage out of a YUV image
	public PPMImage(YUVImage img){
		super(img.getWidth(), img.getHeight(), MAX_COLOR);
		this.pixelArray = new RGBPixel[this.getHeight()][this.getWidth()];
		for(int i = 0; i < this.getHeight(); i++){
			for(int j = 0; j < this.getWidth(); j++){
				this.pixelArray[i][j] = new RGBPixel(img.pixelArray[i][j]);
			}
		}
	}

	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append("P3\n" + getWidth() + "\n" + getHeight() + "\n" + getColorDepth() + "\n");
		for(int i = 0; i < getHeight(); i++){
			for(int j = 0; j < getWidth(); j++){
				str.append(" " + pixelArray[i][j].getRed());
				str.append(" " + pixelArray[i][j].getGreen());
				str.append(" " + pixelArray[i][j].getBlue());
			}
			str.append("\n");
		}
		return str.toString();
	}

	//save the PPMImage to a file
	public void toFile(java.io.File file){
		try{
			PrintWriter pfile = new PrintWriter(file);
			pfile.println("P3");
			pfile.println(getWidth());
			pfile.println(getHeight());
			pfile.println(getColorDepth());
			for(int i = 0; i < getHeight(); i++){
				for(int j = 0; j < getWidth(); j++){
					pfile.print(" " + pixelArray[i][j].getRed());
					pfile.print(" " + pixelArray[i][j].getGreen());
					pfile.print(" " + pixelArray[i][j].getBlue());
				}
				pfile.print("\n");
			}
			pfile.close();
		}
		catch(java.io.FileNotFoundException ex) {
			System.out.println("A FileNotFoundException occured");
			System.out.println(ex.toString());
		}
	}
}
