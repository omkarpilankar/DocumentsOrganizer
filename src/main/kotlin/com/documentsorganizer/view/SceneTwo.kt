package com.documentsorganizer.view

import com.documentsorganizer.controller.MainController
import com.documentsorganizer.controller.docCategorizer
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import tornadofx.*
import javafx.scene.control.TableColumn
import javafx.scene.text.FontWeight
import java.io.File

typealias Row = Map<String, String>

class SceneTwo : View("Documents Organizer") {

    private val mainController: MainController by inject()
    private val mainView: MainView by inject()

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
                    item("Documentation").action {
                        hostServices.showDocument("")
                    }
                    item("About").action {
                        AboutView().openWindow()
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
                for (i in mainController.finalFilesList) {
                    val category = docCategorizer(mainView.labelText.value + File.separator + i)
                    mainController.filesWithCategory.add(mapOf("Filename" to i, "Category" to category))
                }

                tableview(mainController.filesWithCategory) {
                    mainController.filesWithCategory.first().keys.forEach { columnName ->
                        column(columnName) { cell: TableColumn.CellDataFeatures<Row, String> ->
                            SimpleStringProperty(cell.value[columnName])
                        }.fixedWidth(490.0)
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
                        mainController.commitChanges(mainController.filesWithCategory)
                        replaceWith<FinalScene>()
                    }
                }
                alignment = Pos.BASELINE_RIGHT
            }
        }

    }
}