package com.documentsorganizer.controller

import org.apache.commons.lang3.StringUtils
import java.io.File
import org.apache.tika.Tika

fun textExtract(file: File): Array<String> {

    // Stopwords list
    val stopWords: List<String> = listOf("hereafter", "most", "each", "name", "more", "hence", "whatever", "does", "fifteen", "done", "an", "us", "amongst", "herself", "moreover", "due", "full", "keep", "across", "had", "back", "about", "up", "seem", "himself", "rather", "a", "into", "two", "eleven", "and", "forty", "everywhere", "sometime", "whether", "put", "few", "our", "somehow", "per", "therefore", "thru", "them", "beside", "'re", "whom", "even", "'ve", "once", "least", "we", "were", "still", "unless", "'s", "that", "whereby", "i", "otherwise", "might", "some", "whose", "go", "every", "five", "fifty", "toward", "why", "here", "enough", "part", "me", "for", "among", "at", "nowhere", "several", "ours", "you", "'s", "her", "so", "those", "former", "whenever", "was", "against", "both", "could", "mine", "being", "itself", "onto", "becoming", "then", "move", "would", "as", "whereas", "neither", "he", "wherein", "behind", "since", "whither", "latter", "while", "not", "other", "somewhere", "sometimes", "the", "there", "although", "first", "did", "show", "him", "where", "further", "three", "ten", "above", "amount", "becomes", "to", "anything", "of", "doing", "should", "all", "used", "nobody", "third", "front", "are", "seems", "else", "next", "whole", "these", "others", "one", "myself", "throughout", "yours", "along", "ca", "make", "also", "how", "may", "thence", "what", "when", "take", "n't", "which", "has", "wherever", "yourself", "herein", "became", "give", "have", "using", "within", "my", "until", "without", "something", "through", "do", "ourselves", "often", "thereupon", "alone", "either", "its", "regarding", "almost", "too", "your", "can", "many", "been", "side", "before", "'d", "with", "seeming", "though", "yourselves", "because", "call", "well", "cannot", "never", "much", "another", "see", "'ve", "out", "whereafter", "empty", "elsewhere", "be", "twenty", "she", "mostly", "nine", "only", "bottom", "quite", "except", "none", "nor", "everything", "under", "is", "besides", "please", "same", "'m", "in", "anywhere", "six", "say", "made", "over", "various", "hundred", "themselves", "thus", "indeed", "thereby", "anyhow", "everyone", "together", "by", "must", "whoever", "hereby", "during", "no", "again", "nothing", "whereupon", "formerly", "seemed", "from", "'d", "already", "it", "afterwards", "this", "if", "around", "get", "who", "hereupon", "whence", "last", "on", "someone", "four", "thereafter", "via", "than", "off", "nevertheless", "meanwhile", "re", "therein", "anyway", "any", "am", "anyone", "or", "beforehand", "such", "but", "between", "less", "very", "twelve", "namely", "just", "none", "become", "eight", "always", "sixty", "hers", "upon", "below", "beyond", "however", "his", "will", "their", "really", "perhaps", "own", "towards", "'ll", "down", "after", "top", "yet", "latterly", "they", "now", "ever", "serious")

    // Create an instance of Tika with default configuration
    val tika = Tika()

    // Using parseToString method of Tika Class to extract the text from the file as string and store it in fileContent variable
    val fileContent: String = tika.parseToString(file)

    // Creating a new array list to first split the extracted text and then store it as elements in extractedTextList variable
    val extractedTextList: MutableList<String> = fileContent.split(" ") as MutableList<String>

    // Creating a new onlyWords array list to store words containing only English alphabets
    val onlyWords: MutableList<String> = mutableListOf()

    /*
    * Iterate over the list of extractedTextList and check if the element consists of words made of English alphabet
    * If the string consists of word made of English Alphabets then add it to onlyWords list
    */
    for (s in extractedTextList) {
        if (StringUtils.isAlpha(s)) onlyWords.add(s.lowercase())
    }

    // Remove all stopwords
    onlyWords.removeAll(stopWords)

    return onlyWords.toTypedArray()
}