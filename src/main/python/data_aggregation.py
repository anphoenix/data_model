# -*- coding: utf-8 -*-
'''
This scripts is used to generate person weibo from a global weibo.txt file, it will generate one file to each person in a specified folder, the user's weibo is all contains in the file.
You can specify three parameters:
	1. the input global weibo.txt
	2. the folder of person specified weibo
	3. how many user you need to generate (option)
'''

import sys, os, re

def generateData(inputData, outputDir, userLimit):
    print "Generate person weibo to folder: " + outputDir
    if not os.path.isdir(outputDir):
        os.mkdir(outputDir)
        print 'Directory created at: ' + outputDir
    currentID = ""
    userNum = 0
    outputFile = None
    l = inputData.readline()
    while l:
        line = l.strip()
        if line:
            fields = line.split("\t")
            if len(fields) < 6:
                print "Broken line found: " + line
                l = inputData.readline()
                continue
            if fields[1] != currentID:
                userNum += 1
                if userNum > userLimit:
                    break
                print "Find weibo for " + str(userNum) + " user: " + fields[1]
                currentID = fields[1]
                fileName = outputDir + "/" + currentID
                print "Create a new file: " + fileName
                outputFile = file(fileName, "w")
            text = cleanWeibo(fields[5])
            if len(text) > 0:
                outputFile.write(text + "\n")
        l = inputData.readline()
    print "Generate user weibo " + str(userNum - 1)

def cleanWeibo(text):
    #1. clean all the @username
    username = re.compile('((//)*(回复)*@.*:)')
    text = username.sub("", text)

    #2. clean all the [*] as a face
    face = re.compile('\[.*\]')
    text = face.sub("", text)

    #3. remove all http url as "http://t.cn/h98rBI"
    url = re.compile('((http:)*\/\/t.cn\/\w*)')
    text = url.sub("", text)

    #4. remove the hashtag topic
    topic = re.compile('#.*#')
    text = topic.sub("", text)

    #5. remove the common text
    text = text.replace("转发微博", "")

    return text

def usage():
    print "Two parameter is required to run the scripts: input file and output folder\n"
    print "One parameter is optional: the limited number of user need generate, default will generate all the user weibo data in the input file\n"


if __name__ == "__main__":

    if len(sys.argv) < 3:  # Expect more then two argument: the input data file and output folder
        usage()
        sys.exit(2)
    try:
        inputData = file(sys.argv[1], "r")
    except IOError:
        sys.stderr.write("ERROR: Cannot read inputfile %s.\n" % arg)
        sys.exit(1)
    userCount = sys.maxint
    if len(sys.argv) >= 4:
        userCount = int(sys.argv[3])
        print "Generate weibo user: " + str(userCount)
    generateData(inputData, sys.argv[2], userCount)
	
