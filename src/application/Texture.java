package application;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Texture extends Application {
	    public Map<String, Image> HashMap =  new HashMap<String, Image>();
	    Stage stage = new Stage();
	    
	    public Texture() {
	    	start(stage);
	    }
	    
	    public void start(Stage primaryStage){
	    	HashMap.put("name1", new Image(name));
	        HashMap.put("name2", new Image(name));
	        HashMap.put("name3", new Image(name));
	        HashMap.put("name4", new Image(name));
	    }
	    
	    public Image getTexture(String key) {
	    	return HashMap.get(key);
	    }
}

