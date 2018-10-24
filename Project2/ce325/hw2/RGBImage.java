package ce325.hw2;

//an image that consists of RGB Pixels
public class RGBImage implements Image{
	private int width;	//indicates the width of the image
	private int height;	//indicates the height of the image
	private int colordepth;	//indicates the colordepth of the image
	protected RGBPixel [][] pixelArray; //array that contains the pixels of the image
	protected static final int MAX_COLOR = 255;  //the largest value of a pixel brightness

	//create an initially black RGB image using width, height and colordepth values
	public RGBImage(int width, int height, int colordepth){
		this.width = width;
		this.height = height;
		this.colordepth = colordepth;
		pixelArray = new RGBPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelArray[i][j] = new RGBPixel((short)0, (short)0, (short)0);
			}
		}
	}

	//create an RGB image out of another RGB image
	public RGBImage(RGBImage copyImg){
		this.width = copyImg.width;
		this.height = copyImg.height;
		this.colordepth = copyImg.colordepth;
		this.pixelArray = new RGBPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new RGBPixel(copyImg.pixelArray[i][j]);
			}
		}
	}

	//create an RGB image out of a YUV image
	public RGBImage(YUVImage YUVImg){
		this.width = YUVImg.getWidth();
		this.height = YUVImg.getHeight();
		this.colordepth = MAX_COLOR;
		this.pixelArray = new RGBPixel[height][width];
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				this.pixelArray[i][j] = new RGBPixel(YUVImg.pixelArray[i][j]);
			}
		}
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}

	public int getColorDepth(){
		return colordepth;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void setColorDepth(int colordepth){
		this.colordepth = colordepth;
	}

	//convert image to the equivalent grayscale image
	public void grayscale(){
		short gray;
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				gray = (short)(pixelArray[i][j].getRed() * 0.3 + pixelArray[i][j].getGreen() * 0.59 + pixelArray[i][j].getBlue() * 0.11);
				pixelArray[i][j].setRed(gray);
				pixelArray[i][j].setGreen(gray);
				pixelArray[i][j].setBlue(gray);
			}
		}
	}

	//doubles the size of the image
	public void doublesize(){
	 	RGBPixel [][] temp = new RGBPixel[height][width];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				temp[i][j] = new RGBPixel(pixelArray[i][j]);
			}
		}

		pixelArray = new RGBPixel[2*height][2*width];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelArray[2*i][2*j] = new RGBPixel(temp[i][j]);
				pixelArray[(2*i)+1][2*j] = new RGBPixel(temp[i][j]);
				pixelArray[2*i][(2*j)+1] = new RGBPixel(temp[i][j]);
				pixelArray[(2*i)+1][(2*j)+1] = new RGBPixel(temp[i][j]);
			}
		}
		height = 2*height;
		width = 2*width;
	}

	//halves the size of the image
	public void halfsize(){
	 	RGBPixel [][] temp = new RGBPixel[height][width];
		short redAverage, greenAverage, blueAverage;

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				temp[i][j] = new RGBPixel(pixelArray[i][j]);
			}
		}

		height = height/2;
		width = width/2;

		pixelArray = new RGBPixel[height][width];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				redAverage = (short)((temp[2*i][2*j].getRed() + temp[(2*i)+1][2*j].getRed() + temp[2*i][(2*j)+1].getRed() + temp[(2*i)+1][(2*j)+1].getRed())/4);
				greenAverage = (short)((temp[2*i][2*j].getGreen() + temp[(2*i)+1][2*j].getGreen() + temp[2*i][(2*j)+1].getGreen() + temp[(2*i)+1][(2*j)+1].getGreen())/4);
				blueAverage = (short)((temp[2*i][2*j].getBlue() + temp[(2*i)+1][2*j].getBlue() + temp[2*i][(2*j)+1].getBlue() + temp[(2*i)+1][(2*j)+1].getBlue())/4);

				pixelArray[i][j] = new RGBPixel(redAverage, greenAverage, blueAverage);
			}
		}
	}

	//convert image to the equivalent image rotated by 90 degrees
	public void rotateClockwise(){
	 	RGBPixel [][] temp = new RGBPixel[height][width];
		int swap;
		short redAverage, greenAverage, blueAverage;

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				temp[i][j] = new RGBPixel(pixelArray[i][j]);
			}
		}

		swap = height;
		height = width;
		width = swap;

		pixelArray = new RGBPixel[height][width];

		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelArray[i][j] = new RGBPixel(temp[width-1-j][i]);
			}
		}
	}
}
