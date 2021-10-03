package com.documentsorganizer.view

import com.documentsorganizer.controller.MainController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.SelectionMode
import javafx.scene.text.FontWeight
import tornadofx.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

class MainView : View("Documents Organizer") {

    private val mainController: MainController by inject()

    var labelText = SimpleStringProperty()

    override val root = stackpane {
        borderpane {

            // Top section of the Main window
            top {
                menubar {
                    isVisible = true
                    menu("File") {
                        item("Exit", "Shortcut+x").action {
                            primaryStage.close()
                        }
                    }
                    menu("Help") {
                        item("Documentation", "Shortcut+d").action {
                            hostServices.showDocument("")
                        }
                        item("About", "Shortcut+a").action {
                            AboutView().openWindow()
                        }
                    }
                    menu("Options") {
                        item("Create/Update Model").action {
                            if ((File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")).exists()) {
                                mainController.createModel()
                            } else {
                                alert(Alert.AlertType.WARNING, "", "Training data file not found", ButtonType.OK)
                            }
                        }
                        item("Download Training data file").action {
                            val url = URL("https://www.dropbox.com/s/gjkk47gjvlnb94f/en-docs-category.train?dl=1")
                            val rbc: ReadableByteChannel = Channels.newChannel(url.openStream())
                            val fos = FileOutputStream(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")
                            fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                            if ((File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")).exists()) {
                                alert(Alert.AlertType.INFORMATION, "", "Training Data file downloaded successfully", ButtonType.OK)
                            }
                        }
                    }
                }
            }

            // Left section of the Main Window
            left {
                vbox {
                    this.setPrefSize(460.0, 0.0)
                    labelText.set("None")
                    label("Documents Organizer") {
                        style {
                            padding = box(10.px)
                            fontSize = 35.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    label("Choose the directory whose files you want to organize:") {
                        vboxConstraints {
                            marginLeft = 10.0
                            marginBottom = 10.0
                        }
                        style {
                            fontSize = 16.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    button("Select Directory") {
                        tooltip("Choose the directory you want to organize")
                        vboxConstraints {
                            marginLeft = 10.0
                        }
                        action {
                            mainController.filesList.removeAll(mainController.filesList)
                            mainController.finalFilesList.removeAll(mainController.finalFilesList)
                            val path = mainController.selectDirectory()
                            labelText.set(path)
                            mainController.listFiles()
                        }
                    }
                    hbox {
                        label("Selected Directory:") {
                            hboxConstraints {
                                marginLeft = 10.0
                                marginTop = 10.0
                            }
                            style {
                                fontSize = 16.px
                                fontWeight = FontWeight.BOLD
                            }
                        }
                        label {
                            hboxConstraints {
                                marginTop = 10.0
                                marginLeft = 5.0
                            }
                            style {
                                fontSize = 16.px
                            }
                            bind(labelText)
                        }
                    }
                    label("List of files which can be categorized: ") {
                        vboxConstraints {
                            marginTop = 10.0
                            marginLeft = 10.0
                        }
                        style {
                            fontSize = 16.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    listview(mainController.filesList) {
                        selectionModel.selectionMode = SelectionMode.MULTIPLE
                        mainController.selectedFilesList = selectionModel.selectedItems
                        vboxConstraints {
                            marginTop = 10.0
                            marginLeft = 10.0
                        }
                    }
                }
            }

            // Center section of the main window
            center {
                vbox {
                    alignment = Pos.TOP_CENTER
                    for (i in 1..19) {
                        label()
                    }
                    button(">") {
                        tooltip("Add file(s)")
                        vboxConstraints {
                            marginBottom = 15.0
                            marginLeft = 15.0
                            marginRight = 15.0
                        }
                        action {
                            mainController.addToFinal()
                        }
                    }
                    button("<") {
                        tooltip("Remove file(s)")
                        vboxConstraints {
                            marginBottom = 15.0
                            marginLeft = 15.0
                            marginRight = 15.0
                        }
                        action {
                            mainController.removeFromFinal()
                        }
                    }
                    button(">>") {
                        tooltip("Add all files to final list")
                        vboxConstraints {
                            marginBottom = 15.0
                            marginLeft = 15.0
                            marginRight = 15.0
                        }
                        action {
                            mainController.addAllToFinal()
                        }
                    }
                    button("<<") {
                        tooltip("Remove all files from final list")
                        vboxConstraints {
                            marginBottom = 15.0
                            marginLeft = 15.0
                            marginRight = 15.0
                        }
                        action {
                            mainController.removeAllFromFinal()
                        }
                    }
                }
            }

            // Right section of the main window
            right {
                vbox {
                    this.setPrefSize(460.0, 0.0)
                    for (i in 1..18) {
                        label {
                            style {
                                fontSize = (6.1).px
                            }
                        }
                    }
                    label("Final list of files to be categorized: ") {
                        vboxConstraints {
                            marginTop = 10.0
                            marginLeft = 10.0
                            marginBottom = 3.0
                        }
                        style {
                            fontSize = 16.px
                            fontWeight = FontWeight.BOLD
                        }
                    }
                    listview(mainController.finalFilesList) {
                        selectionModel.selectionMode = SelectionMode.MULTIPLE
                        mainController.selectedFinalFilesList = selectionModel.selectedItems
                        vboxConstraints {
                            marginTop = 10.0
                            marginRight = 10.0
                        }
                    }
                    button("Categorize") {
                        vboxConstraints {
                            marginTop = 15.0
                            marginBottom = 15.0
                            marginRight = 10.0
                            marginLeft = 350.0
                        }
                        action {
                            if ((File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")).exists()) {
                                if (mainController.finalFilesList.isEmpty()) {
                                    alert(Alert.AlertType.WARNING, "", "No files in final list, Please add files to final list to continue", ButtonType.OK)

                                } else {
                                    mainController.categorize()
                                    replaceWith<SceneTwo>()
                                }
                            } else {
                                alert(Alert.AlertType.ERROR, "", "Training data file not found", ButtonType.OK)
                            }
                        }
                        style {
                            fontSize = 16.px
                        }
                    }
                }
            }
        }
    }
}