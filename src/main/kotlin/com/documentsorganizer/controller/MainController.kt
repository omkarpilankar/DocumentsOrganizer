package com.documentsorganizer.controller

import com.documentsorganizer.view.MainView
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.DirectoryChooser
import tornadofx.*
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class MainController : Controller() {

    private val mainView: MainView by inject()

    private var path = "None"
    private var extendedFilePath: ObservableList<String> = FXCollections.observableArrayList()

    private var validExtensions: ObservableList<String> = FXCollections.observableArrayList("doc", "docx", "pdf")

    var filesList: ObservableList<String> = FXCollections.observableArrayList()
    var selectedFilesList: ObservableList<String> = FXCollections.observableArrayList()

    var finalFilesList: ObservableList<String> = FXCollections.observableArrayList()
    var selectedFinalFilesList: ObservableList<String> = FXCollections.observableArrayList()

    var filesWithCategory: ObservableList<Map<String, String>> = FXCollections.observableArrayList()

    fun selectDirectory(): String {
        val directoryChooser = DirectoryChooser()
        val selectedDirectory: File? = directoryChooser.showDialog(primaryStage.scene.window)
        return if (selectedDirectory != null) {
            path = selectedDirectory.absolutePath
            selectedDirectory.absolutePath
        } else {
            path = "None"
            "None"
        }
    }

    fun listFiles() {

        var numberOfFiles = 0
        var count = 0

        // Logic for no files with the specified included extensions found
        if (path != "None") {
            Files.walk(Paths.get(path), 1).use { paths ->
                paths.filter(Files::isRegularFile).forEach {
                    numberOfFiles += 1
                    val f = File(it.toString())
                    if (f.extension !in validExtensions) {
                        count += 1
                    }
                }
            }
            if (count == numberOfFiles) {
                alert(Alert.AlertType.ERROR, "No files found", "No files found in the selected directory. Supported file extensions are .pdf, .doc, .docx", ButtonType.OK)
            }
        }

        if (path != "None") {
            Files.walk(Paths.get(path), 1).use { paths ->
                paths.filter(Files::isRegularFile).forEach {
                    val f = File(it.toString())
                    if (f.extension in validExtensions) {
                        filesList.add(f.name.toString())
                        extendedFilePath.add(f.absolutePath)
                    }
                }
            }
        }
    }

    fun addToFinal() {
        if (finalFilesList.containsAll(selectedFilesList)) {
            alert(Alert.AlertType.WARNING, "Selected files already added", "File(s) already added to final list", ButtonType.OK)
        } else {
            finalFilesList.addAll(selectedFilesList)
        }
    }

    fun addAllToFinal() {
        if (finalFilesList.containsAll(filesList)) {
            alert(Alert.AlertType.WARNING, "All files already added", "All files already added to final list", ButtonType.OK)
        } else {
            finalFilesList.addAll(filesList)
        }
    }

    fun removeFromFinal() {
        if (finalFilesList.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Selected files already removed", "File(s) already removed from final list", ButtonType.OK)
        } else {
            finalFilesList.removeAll(selectedFinalFilesList)
        }
    }

    fun removeAllFromFinal() {
        if (finalFilesList.isEmpty()) {
            alert(Alert.AlertType.WARNING, "All files already removed", "All files already removed from final list", ButtonType.OK)
        } else {
            finalFilesList.removeAll(finalFilesList)
        }
    }

    fun commitChanges(categoryMap: List<Map<String, String>>) {
        for (i in categoryMap) {
            val path: Path =
                Paths.get(((mainView.labelText.value).toString() + File.separator + i["Category"].toString()))
            if (Files.exists(path)) {
                try {
                    val source =
                        Paths.get(((mainView.labelText.value).toString() + File.separator + i["Filename"].toString()))
                    val target =
                        Paths.get(((mainView.labelText.value).toString() + File.separator + i["Category"].toString() + File.separator + i["Filename"].toString()))
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
                } catch (e: IOException) {
                    alert(Alert.AlertType.WARNING, "Moving files failed", "Moving files failed", ButtonType.OK)
                    e.printStackTrace()
                }
            } else {
                val file = File((mainView.labelText.value).toString() + File.separator + i["Category"].toString())
                if (!file.exists()) {
                    file.mkdirs()
                }
                try {
                    val source =
                        Paths.get(((mainView.labelText.value).toString() + File.separator + i["Filename"].toString()))
                    val target =
                        Paths.get(((mainView.labelText.value).toString() + File.separator + i["Category"].toString() + File.separator + i["Filename"].toString()))
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
                } catch (e: IOException) {
                    alert(Alert.AlertType.WARNING, "Moving files failed", "Moving files failed", ButtonType.OK)
                    e.printStackTrace()
                }
            }
        }
    }

}