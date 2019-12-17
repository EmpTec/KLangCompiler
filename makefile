.PHONY: clean

run: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang < code.k > code.s

build: clean target/klang-1.0-jar-with-dependencies.jar

target/klang-1.0-jar-with-dependencies.jar:
	mvn package

runTest: ./src/test/test
	./src/test/test
	
./src/test/test: ./src/test/tests.s
	gcc -o ./src/test/test ./src/test/tests.s ./src/test/functionCall/functionCall.c ./src/test/testCode.c

./src/test/tests.s: target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang < ./src/test/tests.k > ./src/test/tests.s

clean:
	rm -f ./src/test/tests.s
	rm -f ./src/test/test
	rm -f code.s
	rm -f target/klang-1.0-jar-with-dependencies.jar
	rm -f target/klang-1.0.jar