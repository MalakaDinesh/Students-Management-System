#!/bin/bash
echo "Compiling Student Information System..."
mkdir -p bin
javac -d bin -encoding UTF-8 src/model/*.java src/data/*.java src/auth/*.java src/service/*.java src/gui/*.java src/*.java
if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "To run the application, use: java -cp bin Main"
else
    echo "Compilation failed!"
fi

