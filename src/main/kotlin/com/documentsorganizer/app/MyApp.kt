package com.documentsorganizer.app

import com.documentsorganizer.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MainView::class) {

    override fun start(stage: Stage) {
        with(stage) {
            height = 775.0
            width = 1005.0
            isResizable = false
            addStageIcon(Image("logo.png"))
        }
        super.start(stage)
    }

}