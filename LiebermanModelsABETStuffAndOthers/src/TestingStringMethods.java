import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import edu.duke.FileResource;

public class TestingStringMethods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileResource fr = new FileResource("C:/Users/RolandoJose/workspace/LiebermanModels/Data/TestingFile.csv");
		CSVParser parser = fr.getCSVParser(true);
		for(CSVRecord record : parser) {
			
		}
	}
}
