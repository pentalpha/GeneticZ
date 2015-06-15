The goal of the Geneticz project is to produce a genetic samples analyzer capable of defining an appropriate genetic signature, draw a dendogram basead on the samples and apply the leave-one-out technic to evaluate if the sample groups (case/control) are stable.

![Main Window](screenshot.png?raw=true "Screenshot")

----------------------------------------------

It uses two input files, the "*.gSamples" file and the "*.groups" file.
The gSamples is a double floating point values matrix which describes the expression of each gene on each sample:

Gene1 0.5 0.75 1.22 4.03
Gene2 0.9 4.98 3.00 1.09
Gene2 0.5 0.75 1.22 4.03

And the groups is a file which describes the name and group of each column on the gSamples file, respectively:

ct1 controle
c2 caso
c3 caso

----------------------------------------------

The building process is very simple.
First, make sure you have JDK 1.8 in your machine.
Build with the script: ./build
You may need to give permission to the script with: chmod +x build

Always make sure the "lib/" directory is in the same directory as the .jar executable