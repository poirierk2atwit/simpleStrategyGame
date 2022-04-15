package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageHandler extends Parent {
		public static final int SCALE = 4;
		public static final int SIZE = 16 * SCALE;
		private static final String FILEPATH = "src/textures/";
	    @SuppressWarnings("serial")
		public static final Map<String, ArrayList<File>> IMAGES = new HashMap<String, ArrayList<File>>() {
	    	{
	    		File[] files = new File(FILEPATH).listFiles();
	    		ArrayList<String> keys = new ArrayList<String>();
	    		ArrayList<ArrayList<File>> images = new ArrayList<ArrayList<File>>();
	    		for (File f : files) {
	    			String[] data = f.getName().split("\\.")[0].split("_"); //could be problematic idk
	    			if (f.getName().split("\\.")[1].toLowerCase().equals("png")) {
	    				if (!keys.contains(data[0])) {
	    					keys.add(data[0]);
	    					images.add(new ArrayList<File>());
	    				}
	    				while (images.get(keys.indexOf(data[0])).size() <= Integer.parseInt(data[1])) {
	    					images.get(keys.indexOf(data[0])).add(null);
	    				}
	    				images.get(keys.indexOf(data[0])).set(Integer.parseInt(data[1]), f);
	    			}
	    		}
	    		for (int i = 0; i < keys.size(); i++) {
	    			put(keys.get(i), images.get(i));
	    		}
	    	}
	    };
	    
	    public static Image getImage(String key, int index) {
	    	return new Image(IMAGES.get(key).get(index).getAbsolutePath(), SIZE, SIZE, false, false);
	    }
	    
	    public static Image getImage(String key, int index, int width, int height) {
	    	return new Image(IMAGES.get(key).get(index).getAbsolutePath(), width, height, false, false);
	    }
	    
	    String key;
	    int index = 0;
	    private final ImageView view;
	    
	    public ImageHandler(String key) {
	    	if (IMAGES.containsKey(key)) {
	    		this.key = key;
	    	} else {
	    		System.out.println("Unknown key \"" + key + "\"");
	    		this.key = "Generic";
	    	}
	    	view = new ImageView(getImage(this.key, index));
	    	view.setSmooth(false);
	    	this.getChildren().add(this.view);
	    }
	    
	    public ImageHandler(String key, int width, int height) {
	    	if (IMAGES.containsKey(key)) {
	    		this.key = key;
	    	} else {
	    		System.out.println("Unknown key \"" + key + "\"");
	    		this.key = "Generic";
	    	}
	    	view = new ImageView(getImage(this.key, index, width, height));
	    	view.setSmooth(false);
	    	this.getChildren().add(this.view);
	    }
	    
	    public boolean setIndex(int i) {
	    	try {
	    		view.setImage(getImage(key, i));
	    		index = i;
	    		return true;
	    	} catch (Exception e) {
	    		return false;
	    	}
	    }
	    
	    public Image getImage() {
	    	return getImage(key, 0);
	    }
	    
	    public Image getImage(int index) {
	    	return getImage(key, index);
	    }
	    
	    public ArrayList<File> getAll() {
	    	return IMAGES.get(key);
	    }
}

