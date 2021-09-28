package com.documentsorganizer.controller

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import opennlp.tools.doccat.*
import opennlp.tools.ml.AbstractTrainer
import opennlp.tools.ml.naivebayes.NaiveBayesTrainer
import opennlp.tools.util.*

fun docCategorizer(file: String): String {

    // Read training data
    val dataIn: InputStreamFactory =
        MarkableFileInputStreamFactory(File(File("").absolutePath + File.separator + "train" + File.separator + "en-docs-category.train"))
    val lineStream: ObjectStream<String> = PlainTextByLineStream(dataIn, "UTF-8")
    val sampleStream = DocumentSampleStream(lineStream)

    // Define the training parameters
    val params = TrainingParameters()
    params.put(TrainingParameters.ITERATIONS_PARAM, 10.toString())
    params.put(TrainingParameters.CUTOFF_PARAM, 0.toString())
    params.put(AbstractTrainer.ALGORITHM_PARAM, NaiveBayesTrainer.NAIVE_BAYES_VALUE)

    // Create a model from training data
    val model: DoccatModel = DocumentCategorizerME.train("en", sampleStream, params, DoccatFactory())
    println("Model successfully trained")

    // Save the model to local
    val modelOut =
        BufferedOutputStream(FileOutputStream((File("").absolutePath + File.separator + "model" + File.separator + "en-docs-category.bin")))
    model.serialize(modelOut)
    println("Trained Model is saved locally")

    // Test the model by subjecting it to prediction
    val docCat: DocumentCategorizer = DocumentCategorizerME(model)
    val outcomes: DoubleArray? = docCat.categorize(extractText(file))

    // Print the probabilities of the categories to console
    println("\n---------------------------------\nCategory : Probability\n---------------------------------")
    for (i in 0 until docCat.numberOfCategories) {
        println(docCat.getCategory(i).toString() + " : " + outcomes?.get(i))
    }
    println("---------------------------------")

    // Print the final result for the selected document
    println(docCat.getBestCategory(outcomes) + " : is the predicted category for the given document.")

    return docCat.getBestCategory(outcomes)

}

fun extractText(file: String): Array<String> {
    // Extract the content from the file
    return textExtract(File(file))
}