# Documents Organizer
A Kotlin based application to organize Documents using NLP (Natural Language Processing)

The proposed project tries to eliminate the problem of document categorization by
automating the process of making categories. The software will scan through the complete
document finding keywords relevant to a specific category. After applying the machine
learning model, the software will decide whether to add it to a particular category or not. The
software will then create folders according to the categories and safely move those
documents in the specified folder categories. The software will make use of NLP (Natural
Language Processing) to make decide.

Why did I make this project?
As a student, I download many PDF documents, notes of different topics and save them in a
folder. Then when I want to find a particular file of a specific topic, I have to open all
documents to find the one I want. It takes time to manually make folders according to the
categories and place those documents in the folder. I have many examples where this might
be very uphill struggle to do. Consider a Law firm that has hundreds or even thousands of
unorganized documents. It would be a struggle to manually go through all the documents and
place them in a particular folder. That makes it difficult to retrieve data on time. Another
example is a Construction Company, where many documents need to be in order. The need
for document categorization is crucial since it makes document retrieval easy. The other
reason is usage of Machine Learning. Automation has taken over most of the manual tasks
and that in turn has made our lives easier. The only limitation was that automation required
some logic to be given by the programmer. But with the help of Machine Learning,
automation can now be done without any user interaction.

The motivation of the project is to see whether we can use Machine Learning for automating
some of the basic tasks. The idea of document categorization might seem a simple task given
that if we only have to work on a handful number of documents. But this simple looking task
might become a noticeably big problem once the number of documents increase. The model
might show different accuracy results since the accuracy depends on how large the dataset is.
The bigger the dataset the model learns more and hence the accuracy increases. The accuracy
of the model can also be increased by using different models, but the Naïve Bayes model is
tested to have accuracy greater than 90%. The Naïve Bayes model is also easy to implement
and less complex than SVM which makes it first choice.
