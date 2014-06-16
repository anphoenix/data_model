import sys, os

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
				outputFile = file(fileName,"w")
			outputFile.write(fields[5] + "\n")                      
        	l = inputData.readline()
	print "Generate user weibo " + str(userNum - 1)

def usage():
	print "Two parameter is required to run the scripts: input file and output folder\n"
	print "One parameter is optional: the limited number of user need generate, default will generate all the user weibo data in the input file\n"

if __name__ == "__main__":

    	if len(sys.argv) < 3: # Expect more then two argument: the input data file and output folder
		usage()
        	sys.exit(2)
    	try:
        	inputData = file(sys.argv[1],"r")
    	except IOError:
        	sys.stderr.write("ERROR: Cannot read inputfile %s.\n" % arg)
        	sys.exit(1)
	userCount = sys.maxint
	if len(sys.argv) >= 4:
		userCount = int(sys.argv[3])
		print "Generate weibo user: " + str(userCount)
	generateData(inputData, sys.argv[2], userCount)
	
