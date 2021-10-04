package com.documentsorganizer.view

import com.documentsorganizer.controller.MainController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import tornadofx.*
import javafx.scene.control.TableColumn
import javafx.scene.text.FontWeight
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

typealias Row = Map<String, String>

class SceneTwo : View("Documents Organizer") {

    private val mainController: MainController by inject()
    private val mainView: MainView by inject()

    private val finalPath = mainView.labelText.value

    override val root = borderpane {

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
                        val fos =
                            FileOutputStream(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                        if ((File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")).exists()) {
                            alert(
                                Alert.AlertType.INFORMATION,
                                "",
                                "Training Data file downloaded successfully",
                                ButtonType.OK
                            )
                        }
                    }
                }
            }
        }

        center {
            vbox {
                label("List of files with categories: ") {
                    vboxConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                        marginBottom = 10.0
                    }
                    style {
                        fontSize = 16.px
                        fontWeight = FontWeight.BOLD
                    }
                }

                tableview(mainController.filesWithCategory) {
                    mainController.filesWithCategory.first().keys.forEach { columnName ->
                        column(columnName) { cell: TableColumn.CellDataFeatures<Row, String> ->
                            SimpleStringProperty(cell.value[columnName])
                        }.fixedWidth(483.0)
                    }
                    vboxConstraints {
                        marginTop = 10.0
                        marginBottom = 10.0
                        marginRight = 10.0
                        marginLeft = 10.0
                    }
                }
            }
        }

        bottom {
            hbox {
                button("Commit") {
                    hboxConstraints {
                        marginBottom = 15.0
                        marginLeft = 15.0
                        marginRight = 15.0
                    }
                    style {
                        fontSize = 16.px
                    }
                    action {
                        alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure you want to continue?",
                            "Changes will be made to the structure of the selected directory $finalPath",
                            ButtonType.YES,
                            ButtonType.NO,
                            actionFn = { btnType ->
                                if (btnType.buttonData == ButtonBar.ButtonData.YES) {
                                    mainController.commitChanges(mainController.filesWithCategory)
                                    replaceWith<FinalScene>()
                                }
                            })
                    }
                }
                alignment = Pos.BOTTOM_RIGHT
            }
        }

    }
}