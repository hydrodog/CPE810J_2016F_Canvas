f=open("dumpFile",'r')
array = []

#append the time delay value into array
temp = ""
stepNew = ""
stepOld = ""
while 1:
    line = f.readline()
    #last line, choose the value
    if not line:
        if temp.split()[len(temp.split()) - 1] == "ms":
            value = temp.split()[len(temp.split()) - 2]
            array.append(value)
        break
    if temp == "":
        temp = line
        continue
    else:
        stepNew = line.split()[0]
        # if the step = 1, or the line has only '*' values, select the last line and last value!
        if int(stepNew) == 1 or (line.split()[len(line.split())-1] == "*" and line.split()[len(line.split())-2] == "*" and line.split()[len(line.split())-3] == "*"):
            if temp.split()[len(temp.split())-1] == "ms":
                value = temp.split()[len(temp.split())-2]
                array.append(value)
    temp = line

#write to a new file
f2=open('DelayValues','w')
for num in array:
    f2.write(num+"\n")
f2.close()
f.close()