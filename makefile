.PHONY: clean

runTest: ./src/test/test
	./src/test/test
	
./src/test/test: ./src/test/tests.s
	gcc -o ./src/test/test ./src/test/tests.s ./src/test/functionCall/functionCall.c ./src/test/testCode.c

./src/test/tests.s:
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang < ./src/test/tests.k > ./src/test/tests.s

clean:
	rm -f ./src/test/tests.s
	rm -f ./src/test/test
