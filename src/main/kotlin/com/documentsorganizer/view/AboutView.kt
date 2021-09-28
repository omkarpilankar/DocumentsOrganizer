package com.documentsorganizer.view

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import tornadofx.*

class AboutView : View("About") {
    override val root = borderpane {
        setPrefSize(480.0, 480.0)

        center {
            vbox {
                label("Documents Organizer") {
                    minWidth = 480.0
                    vboxConstraints {
                        marginTop = 10.0
                        marginBottom = 10.0
                    }
                    style {
                        wrapText = true
                        fontSize = 20.px
                        fontWeight = FontWeight.BOLD
                    }
                    alignment = Pos.CENTER
                }
                alignment = Pos.CENTER
                label("Document Organizer is a software which helps you to categorize documents using NLP (Natural Language Processing)") {
                    vboxConstraints {
                        marginTop = 10.0
                        marginBottom = 10.0
                    }
                    style {
                        wrapText = true
                        fontSize = 16.px
                        fontWeight = FontWeight.BOLD
                        textAlignment = TextAlignment.CENTER
                    }
                }
            }
        }

    }
}