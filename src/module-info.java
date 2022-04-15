module Comp_Sci_Final {
	requires com.google.gson;
	requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to com.google.gson,javafx.graphics,javafx.fxml;
	opens mapObjects to com.google.gson;
	opens utility to com.google.gson;
}