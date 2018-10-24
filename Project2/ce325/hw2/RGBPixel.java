package ce325.hw2;

import java.nio.ByteBuffer;

//describes a pixel that contains information about the brightness of each basic color (red, green, blue)
public class RGBPixel{
	private int RGBValue;	//contains information about the brightness of each basic color (red, green, blue)
							// the MSB is left blank, the second MSB represents Red, the third MSB represents Green
							// and the LSB represents Blue
	private  static final int mask = 255;  //the 8-bit number which is equivalent to 11111111 (used in bit mask creation)
	private static final int MAX_COLOR = 255;  //the largest value of a pixel brightness

	//create an RGB pixel using red, green and blue values
	public RGBPixel(short red, short green, short blue){
		int temp = 0;

		temp = (int)red << 8;
		temp =  (temp + (int)green) << 8;
		temp =  temp + (int)blue;

		RGBValue = temp;
	}

	//create an RGB pixel using another RGB pixel
	public RGBPixel(RGBPixel pixel){
		this.RGBValue = pixel.RGBValue;
	}

	//create an RGB pixel using a YUV pixel
	public RGBPixel(YUVPixel pixel){
		short C = (short)(pixel.getY() - 16);
		short D = (short)(pixel.getU() - 128);
		short E = (short)(pixel.getV() - 128);

		short red = clip((short)(( 298 * C + 409 * E + 128) >> 8));
		short green = clip((short)(( 298 * C - 100 * D - 208 * E + 128) >> 8));
		short blue = clip((short)(( 298 * C + 516 * D + 128) >> 8));

		int temp = 0;

		temp = (int)red << 8;
		temp =  (temp + (int)green) << 8;
		temp =  temp + (int)blue;

		RGBValue = temp;
	}

	public int getRGBValue(){
		return RGBValue;
	}

	public short getRed(){
		int temp = 0;
		temp = (RGBValue >> 16) & mask;
		return (short)temp;
	}

	public short getGreen(){
		int temp = 0;
		temp = (short)(RGBValue >> 8) & mask;
		return (short)temp;
	}

	public short getBlue(){
		int temp = 0;
		temp = RGBValue & mask;
		return (short)temp;
	}

	public void setRed(short red){
		int temp = 0;

		temp = mask << 8;
		temp =  temp << 8;
		temp =  (temp + mask) << 8;
		temp =  temp + mask;

		RGBValue = RGBValue & temp;

		temp = 0;
		temp = (int)red << 16;

		RGBValue = RGBValue | temp;
	}

	public void setGreen(short green){
		int temp = 0;

		temp = mask << 8;
		temp =  (temp + mask) << 8;
		temp =  temp << 8;
		temp =  temp + mask;

		RGBValue = RGBValue & temp;

		temp = 0;
		temp = (int)green << 8;

		RGBValue = RGBValue | temp;
	}

	public void setBlue(short blue){
		int temp = 0;

		temp = mask << 8;
		temp =  (temp + mask) << 8;
		temp =  (temp + mask) << 8;

		RGBValue = RGBValue & temp;

		temp = 0;
		temp = (int)blue;

		RGBValue = RGBValue | temp;
	}

	//if input is negative it returns 0, if it is greater than MAX_COLOR(usually 255) it returns MAX_COLOR
	public short clip(short input){
		if(input < 0){
			return 0;
		}
		else if(input > MAX_COLOR){
			return MAX_COLOR;
		}
		return input;
	}
}
