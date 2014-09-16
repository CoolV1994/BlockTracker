	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.OutputStream;
	import java.util.Properties;

public class BlockTrackSQL {
	
	Properties prop = new Properties();
	OutputStream output = null;
	 
		public boolean createSQL(){
		try {
	 
			output = new FileOutputStream("db.config");
	 
			// set the properties value
			prop.setProperty("host", "localhost");
			prop.setProperty("database", "BlockTrack");
			prop.setProperty("dbuser", "username");
			prop.setProperty("dbpassword", "password");
			prop.setProperty("blocks","138,57,54,46,42,41");
	 
			// save properties to project root folder
			prop.store(output, null);
	 
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		} finally {
			if (output != null) {
				try {
					output.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
	 
		}
		return false;
	  }
	}

