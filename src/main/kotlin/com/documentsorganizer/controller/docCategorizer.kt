package com.documentsorganizer.controller

import java.io.File
import opennlp.tools.doccat.*

fun docCategorizer(file: String): String {

    val model = DoccatModel(File(File("").absolutePath + File.separator + "model" + File.separator +"en-docs-category.bin"))

    // Test the model by subjecting it to prediction
    val docCat: DocumentCategorizer = DocumentCategorizerME(model)
    val outcomes: DoubleArray? = docCat.categorize(extractText(file))

    // Print the probabilities of the categories to console
    println("\n##############################################################################################")
    print("Filename: $file")
    println("\n---------------------------------\nCategory : Probability\n---------------------------------")
    for (i in 0 until docCat.numberOfCategories) {
        println(docCat.getCategory(i).toString() + " : " + outcomes?.get(i))
    }
    println("---------------------------------")

    // Print the final result for the selected document
    println(docCat.getBestCategory(outcomes) + " : is the predicted category for the given document.")
    println("##############################################################################################")

    return docCat.getBestCategory(outcomes)

}

fun extractText(file: String): Array<String> {
    // Extract the content from the file
    return textExtract(File(file))
}