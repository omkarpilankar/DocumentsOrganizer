package com.documentsorganizer.view

import com.documentsorganizer.controller.MainController
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.text.FontWeight
import tornadofx.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

class FinalScene : View("Documents Organizer") {

    private val mainController: MainController by inject()

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
                        val fos = FileOutputStream(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                        if ((File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train")).exists()) {
                            alert(Alert.AlertType.INFORMATION, "", "Training Data file downloaded successfully", ButtonType.OK)
                        }
                    }
                }
            }
        }

        center {
            label("Categorization Completed") {
                style {
                    padding = box(10.px)
                    fontSize = 35.px
                    fontWeight = FontWeight.BOLD
                }
            }
        }

        bottom {
            hbox {
                button("Close") {
                    hboxConstraints {
                        marginBottom = 15.0
                        marginLeft = 15.0
                        marginRight = 15.0
                    }
                    style {
                        fontSize = 16.px
                    }
                    action {
                        primaryStage.close()
                    }
                }
                alignment = Pos.BASELINE_CENTER
            }
        }

    }
}
