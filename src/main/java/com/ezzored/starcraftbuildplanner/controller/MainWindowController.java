/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ezzored.starcraftbuildplanner.controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezzored.starcraftbuildplanner.model.Build;
import com.ezzored.starcraftbuildplanner.service.BuildService;
import com.ezzored.starcraftbuildplanner.service.ImportExportService;

/**
 * 
 * @author Ezzored
 */
public class MainWindowController implements Initializable {

	private static final Logger log = LoggerFactory
			.getLogger(MainWindowController.class);

	@FXML
	AnchorPane rootElement;

	@FXML
	TextField nameField;
	@FXML
	TextArea scriptArea;
	@FXML
	TextArea notesArea;
	@FXML
	TextField categoryField;
	@FXML
	ChoiceBox<String> macthupChoiceBox;
	@FXML
	Button btn_newBuild;
	@FXML
	Button btn_updateBuild;
	@FXML
	Button btn_deleteBuild;
	@FXML
	Button btn_Overlay;
	@FXML
	Button btn_Share;
	@FXML
	Button btn_Load;
	@FXML
	CheckBox notesCheckbox;
	@FXML
	TableView<Build> buildTable;
	@FXML
	TableColumn<Build, String> buildColumn;
	@FXML
	TableColumn<Build, String> matchupColumn;

	@FXML
	TextField filter_name;
	@FXML
	TextField filter_category;
	@FXML
	ChoiceBox<String> filter_matchup;
	@FXML
	Button btn_resetFilter;
	@FXML
	Button btn_filter;

	final ObservableList<Build> buildList = FXCollections.observableArrayList();

	final ObservableList<String> matchupList = FXCollections
			.observableArrayList("PvZ", "PvT", "PvP", "TvT", "TvZ", "ZvZ");
	private Build selectedBuild;
	BuildService buildService;
	ImportExportService importExportService;

	@FXML
	private void btn_resetFilterClick(ActionEvent event) {
		filter_name.clear();
		filter_category.clear();
		filter_matchup.getSelectionModel().clearSelection();
		resetTable();
	}
	
	private void resetTable() {
		buildList.clear();
		buildList.addAll(buildService.getAll());
		buildTable.setItems(buildList);
	}

	@FXML
	private void btn_filterClick(ActionEvent event) {
		resetTable();
		
		List<Build> filteredList = new ArrayList<Build>(buildList);
		
		String filterCategory = filter_category.getText();
		String filterName = filter_name.getText();
		String filterMatchup = filter_matchup.getSelectionModel().getSelectedItem();
		
		for (Build build : buildList) {
			
			if (!filterCategory.isEmpty() && !build.getCategory().contains(filterCategory)) {
				filteredList.remove(build);
			}
			
			if (!filterName.isEmpty() && !build.getName().contains(filterName)) {
				filteredList.remove(build);
			}
			
			if (filterMatchup!=null && !build.getMatchup().contains(filterMatchup)) {
				filteredList.remove(build);
			}
		}

		buildList.clear();
		buildList.addAll(filteredList);
		buildTable.setItems(buildList);
	}

	@FXML
	private void btn_newBuildClick(ActionEvent event) {
		Build build = new Build(nameField.getText(), macthupChoiceBox
				.getValue().toString(), scriptArea.getText(),
				notesArea.getText(), categoryField.getText());
		buildList.add(build);
		buildService.save(build);
	}

	@FXML
	private void btn_updateBuildClick(ActionEvent event) {
		Build updateBuild = new Build(nameField.getText(), macthupChoiceBox
				.getValue().toString(), scriptArea.getText(),
				notesArea.getText(), categoryField.getText());
		buildService.update(selectedBuild, updateBuild);
		buildList.remove(selectedBuild);
		buildList.add(updateBuild);
		buildTable.setItems(buildList);
	}

	@FXML
	private void btn_deleteBuildClick(ActionEvent event) {
		buildService.delete(selectedBuild);
		buildList.remove(selectedBuild);
		buildTable.setItems(buildList);
	}

	@FXML
	private void btn_OverlayClick(ActionEvent event) {
		/*
		 * final JDialog jDialog = new JDialog(); jDialog.setGlassPane(true);
		 * final JFXPanel fxPanel = new JFXPanel(); Platform.runLater(new
		 * Runnable() {
		 * 
		 * @Override public void run() { HBox hb = new HBox(); Scene sc= new
		 * Scene(hb); TextField textField = new TextField ();
		 * textField.setText("ASdasdasd"); hb.getChildren().add(textField);
		 * fxPanel.setScene(sc); } }); jDialog.add(fxPanel);
		 * jDialog.setAlwaysOnTop(true); jDialog.setVisible(true);
		 */
	}

	@FXML
	private void btn_ShareClick(ActionEvent event) {
		importExportService.saveBuilds(buildList);
	}

	@FXML
	private void btn_LoadClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open builds file");
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("sc2builds", "*.sc2builds"));
		File file = fileChooser.showOpenDialog(rootElement.getScene()
				.getWindow());

		if (file != null) {

			List<Build> builds = importExportService.loadBuilds(file);

			log.info("Loaded builds: {}", builds.toString());

			for (Build build : builds) {
				if (!buildList.contains(build)) {
					buildService.save(build);
					buildList.add(build);
					log.info("Build added: {}", build.toString());
				}
			}

			buildTable.setItems(buildList);
		}
	}

	private void fillFields(Build selectedBuild) {
		if (selectedBuild != null) {
			nameField.setText(selectedBuild.getName());
			scriptArea.setText(selectedBuild.getScript());
			notesArea.setText(selectedBuild.getNotes());
			macthupChoiceBox.setValue(selectedBuild.getMatchup());
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			buildService = new BuildService();
		} catch (SQLException ex) {
		}

		buildList.addAll(buildService.getAll());

		buildTable.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Object>() {
					// this method will be called whenever user selected row
					@Override
					public void changed(ObservableValue observale,
							Object oldValue, Object newValue) {
						selectedBuild = (Build) newValue;
						fillFields(selectedBuild);
					}
				});

		buildColumn
				.setCellValueFactory(new PropertyValueFactory<Build, String>(
						"name"));

		matchupColumn
				.setCellValueFactory(new PropertyValueFactory<Build, String>(
						"matchup"));

		macthupChoiceBox.setItems(matchupList);
		filter_matchup.setItems(matchupList);

		importExportService = new ImportExportService();

		buildTable.setItems(buildList);
	}

}
