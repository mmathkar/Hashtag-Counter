JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	FibonacciNode.java\
	SpanList.java\
	MaxFibHeap.java \
	FiboMain.java 
        
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class