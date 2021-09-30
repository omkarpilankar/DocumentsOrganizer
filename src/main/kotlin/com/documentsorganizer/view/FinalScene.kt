package com.documentsorganizer.view

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class FinalScene : View("Documents Organizer") {
    override val root = borderpane {

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
