package ce325.hw2;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

//an image that consists of YUV Pixels
public class YUVImage implements Image{
	private int width;	//indicates the width of th image
	private int height;	//indicates the height of the image
	protected YUVPixel [][] pixelArray;	//array that contains the pixels of the image

	//create an initially black YUV image using width, height values
	public YUVImage(int width, int height){
		this.width = width;
		this.height = height;
		pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelArray[i][j] = new YUVPixel((short)16, (short)128, (short)128);
			}
		}
	}

	//create a YUV image out of another YUV image
	public YUVImage(YUVImage copyImg){
		this.width = copyImg.width;
		this.height = copyImg.height;
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(copyImg.pixelArray[i][j]);
			}
		}
	}
	//create a YUV image out of an RGB image
	public YUVImage(RGBImage RGBImg){
		this.width = RGBImg.getWidth();
		this.height = RGBImg.getHeight();
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
			}
		}
	}

	//create a YUVImage from a file
	public YUVImage(java.io.File file) throws UnsupportedFileFormatException{

		try{
			Scanner sc = new Scanner(file);
			//check the magic number
			if(!sc.next().equals("YUV3")){
				throw new UnsupportedFileFormatException("Wrong Magic Number");
			}
			width = Integer.parseInt(sc.next());
			if(width <= 0){
				throw new UnsupportedFileFormatException("Image Width smaller or equal to zero");
			}
			height = Integer.parseInt(sc.next());
			if(height <= 0){
				throw new UnsupportedFileFormatException("Image Height smaller or equal to zero");
			}

			pixelArray = new YUVPixel[height][width];
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					pixelArray[i][j] = new YUVPixel((short)16, (short)128, (short)128);
				}
			}

			//check if the YUV values needed to describe the image exist in the file
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of YUV Values");
					}
					pixelArray[i][j].setY((short)Integer.parseInt(sc.next()));
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of YUV Values");
					}
					pixelArray[i][j].setU((short)Integer.parseInt(sc.next()));
					if(!sc.hasNext()){
						throw new UnsupportedFileFormatException("Wrong Number of YUV Values");
					}
					pixelArray[i][j].setV((short)Integer.parseInt(sc.next()));
				}
			}
		}
		catch(java.io.FileNotFoundException ex) {
			System.out.println("A FileNotFoundException occured");
			System.out.println(ex.toString());
		}
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	//convert image to the equivalent grayscale image
	public void grayscale(){
		RGBImage RGBImg = new RGBImage(this);
		RGBImg.grayscale();

		this.width = RGBImg.getWidth();
		this.height = RGBImg.getHeight();
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
			}
		}
	}

	//doubles the size of the image
	public void doublesize(){
		RGBImage RGBImg = new RGBImage(this);
		RGBImg.doublesize();

		this.width = RGBImg.getWidth();
		this.height = RGBImg.getHeight();
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
			}
		}
	}

	//halves the size of the image
	public void halfsize(){
		RGBImage RGBImg = new RGBImage(this);
		RGBImg.halfsize();

		this.width = RGBImg.getWidth();
		this.height = RGBImg.getHeight();
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
			}
		}
	}

	//convert image to the equivalent image rotated by 90 degrees
	public void rotateClockwise(){
		RGBImage RGBImg = new RGBImage(this);
		RGBImg.rotateClockwise();

		this.width = RGBImg.getWidth();
		this.height = RGBImg.getHeight();
		this.pixelArray = new YUVPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new YUVPixel(RGBImg.pixelArray[i][j]);
			}
		}
	}

	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append("YUV3\n" + width + "\n" + height + "\n");
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				str.append(" " + pixelArray[i][j].getY());
				str.append(" " + pixelArray[i][j].getU());
				str.append(" " + pixelArray[i][j].getV());
			}
			str.append("\n");
		}
		return str.toString();
	}

	//save the YUVImage to a file
	public void toFile(java.io.File file){
		try{
			PrintWriter pfile = new PrintWriter(file);
			pfile.println("YUV3");
			pfile.println(width);
			pfile.println(height);
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
					pfile.print(" " + pixelArray[i][j].getY());
					pfile.print(" " + pixelArray[i][j].getU());
					pfile.print(" " + pixelArray[i][j].getV());
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

	//replace the YUVImage with the equivalent image with equalizes histogram
	public void equalize(){
		Histogram histogram = new Histogram(this);
		histogram.equalize();
		short yValue = 0;

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				yValue = pixelArray[i][j].getY();
				pixelArray[i][j].setY(histogram.getEqualizedLuminocity(yValue));
			}
		}
	}
}
