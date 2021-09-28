package com.documentsorganizer.controller

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Duration

object Toast {
    fun makeText(ownerStage: Stage?, toastMsg: String?, toastDelay: Int, fadeInDelay: Int, fadeOutDelay: Int) {

        val toastStage = Stage()
        toastStage.initOwner(ownerStage)
        toastStage.isResizable = false
        toastStage.initStyle(StageStyle.TRANSPARENT)

        val text = Text(toastMsg)
        text.font = Font.font("Sans Serif", 20.0)
        text.fill = Color.WHITE

        val root = StackPane(text)
        root.style = "-fx-background-radius: 10; -fx-background-color: black; -fx-padding: 10px;"
        root.opacity = 0.0

        val scene = Scene(root)
        scene.fill = Color.TRANSPARENT
        toastStage.scene = scene
        toastStage.show()

        val fadeInTimeline = Timeline()
        val fadeInKey1 =
            KeyFrame(Duration.millis(fadeInDelay.toDouble()), KeyValue(toastStage.scene.root.opacityProperty(), 1))

        fadeInTimeline.keyFrames.add(fadeInKey1)

        fadeInTimeline.onFinished = EventHandler {
            Thread {
                try {
                    Thread.sleep(toastDelay.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                val fadeOutTimeline = Timeline()
                val fadeOutKey1 = KeyFrame(
                    Duration.millis(fadeOutDelay.toDouble()),
                    KeyValue(toastStage.scene.root.opacityProperty(), 0)
                )
                fadeOutTimeline.keyFrames.add(fadeOutKey1)
                fadeOutTimeline.onFinished = EventHandler { toastStage.close() }
                fadeOutTimeline.play()
            }.start()
        }
        fadeInTimeline.play()
    }
}