module Comp_Sci_Final {
	requires com.google.gson;
	requires javafx.controls;
	requires java.desktop;
	
	opens application to com.google.gson,javafx.graphics,javafx.fxml;
	opens tiles to com.google.gson;
	opens entities to com.google.gson;
	opens utility to com.google.gson;
}