#WSD_EL
This project has two main components. The first component is a restfull API, which gets a persian sentence as input and disambiguates words of this sentence.
The porpuse of the second component is achieving a mapping between Farsnet and Farsbase, which are persian wornet and knowledgebase respectively.
There are two ways using Maven and Docker in order to run these components. Follow the following two sections for more details.
##Persian Word Sence Disambiguator API
First of all you need to change the mainClass part of the pom.xml file to WSD.Application. Then, you can use either maven or docker commands for running this component (-p flag can be used for mapping an internal port to port 8080 of docker).
##Farsnet and Farsbase mapper
Like the previous component, you need to change the mainClass to WSD.textWWSDMain and use maven or docker commands for running this component. 
This component takes Entity linked corpuses in JSON format as input and if exicted, adds its corresponding Farsnet ID (representing the disambiguated meaning). Input and output examples are [here](https://github.com/banafshe76/WSD_EL/tree/master/corpus/data/raw/repository_new) and [here](https://github.com/banafshe76/WSD_EL/tree/master/corpus/data/raw). You can use -v flag in docker to share your lockal files
