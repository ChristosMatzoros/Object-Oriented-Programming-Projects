package ce325.hw2;

public class UnsupportedFileFormatException extends java.lang.Exception{
	private String msg;

	public UnsupportedFileFormatException(){
		this.msg = super.getMessage();
	}

	public UnsupportedFileFormatException(String msg){
		this.msg = msg;
	}

	public String getMessage(){
		return msg;
	}

}
