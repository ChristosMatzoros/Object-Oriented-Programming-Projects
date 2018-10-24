package ce325.hw2;

//describes a pixel that contains information about the brightness(Y) and the color(U, V)
public class YUVPixel{
	private int YUVValue;	//contains information about the brightness(Y) and the color(U, V)
							// the MSB is left blank, the second MSB represents Y, the third MSB represents U
							// and the LSB represents V
	private  static final int mask = 255;  //the 8-bit number which is equivalent to 11111111 (used in bit mask creation)

	//create a YUV pixel using Y, U and V values
	public YUVPixel(short Y, short U, short V){
		int temp = 0;

		temp = (int)Y << 8;
		temp =  (temp + (int)U) << 8;
		temp =  temp + (int)V;

		YUVValue = temp;
	}

	//create a YUV pixel using another YUV pixel
	public YUVPixel(YUVPixel pixel){
		this.YUVValue = pixel.YUVValue;
	}

	//create a YUV pixel using an RGB pixel
	public YUVPixel(RGBPixel pixel){
		short Y = (short)( ( (  66 * pixel.getRed() + 129 * pixel.getGreen() +  25 * pixel.getBlue() + 128) >> 8) +  16);
		short U = (short)( ( ( -38 * pixel.getRed() -  74 * pixel.getGreen() + 112 * pixel.getBlue() + 128) >> 8) + 128);
		short V = (short)( ( ( 112 * pixel.getRed() -  94 * pixel.getGreen() -  18 * pixel.getBlue() + 128) >> 8) + 128);

		int temp = 0;

		temp = (int)Y << 8;
		temp =  (temp + (int)U) << 8;
		temp =  temp + (int)V;

		YUVValue = temp;
	}

	public short getY(){
		int temp = 0;
		temp = (YUVValue >> 16) & mask;
		return (short)temp;
	}
	public short getU(){
		int temp = 0;
		temp = (short)(YUVValue >> 8) & mask;
		return (short)temp;
	}
	public short getV(){
		int temp = 0;
		temp = YUVValue & mask;
		return (short)temp;
	}

	public void setY(short Y){
		int temp = 0;

		temp = mask << 8;
		temp =  temp << 8;
		temp =  (temp + mask) << 8;
		temp =  temp + mask;

		YUVValue = YUVValue & temp;

		temp = 0;
		temp = (int)Y << 16;

		YUVValue = YUVValue | temp;
	}
	public void setU(short U){
		int temp = 0;

		temp = mask << 8;
		temp =  (temp + mask) << 8;
		temp =  temp << 8;
		temp =  temp + mask;

		YUVValue = YUVValue & temp;

		temp = 0;
		temp = (int)U << 8;

		YUVValue = YUVValue | temp;
	}
	public void setV(short V){
		int temp = 0;

		temp = mask << 8;
		temp =  (temp + mask) << 8;
		temp =  (temp + mask) << 8;

		YUVValue = YUVValue & temp;

		temp = 0;
		temp = (int)V;

		YUVValue = YUVValue | temp;
	}
}
