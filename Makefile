JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
       	RedBlackTree/RedBlackTree.java \
        RedBlackTree/jobscheduler.java \
       	RedBlackTree/heapsort_.java \
        

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
