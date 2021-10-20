package com.documentsorganizer.app

import com.documentsorganizer.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

// Entry point of the application. Requires MainView.kt file.
class MyApp : App(MainView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            //set height and width
            height = 775.0
            width = 1005.0
            isResizable = false
            addStageIcon(Image("logo.png"))
        }
        super.start(stage)
    }

}
