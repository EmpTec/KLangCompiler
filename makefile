.PHONY: clean
.PHONY: cleanTests

run: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -o code.s code.k

pretty: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang --pretty -o pretty.k code.k

eval: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang --evaluate code.k

generateHeader: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -o code.h --generate-header code.k

build: clean target/klang-1.0-jar-with-dependencies.jar

target/klang-1.0-jar-with-dependencies.jar:
	mvn -Dmaven.test.skip=true package

test: ./src/test/test
	./src/test/test

testJava:
	mvn test
	
./src/test/test: ./src/test/test.s ./src/test/test.h
	gcc -o ./src/test/test ./src/test/test.s ./src/test/**/*.c ./src/test/test.c

./src/test/test.s: target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -o ./src/test/test.s --no-main ./src/test/test.k

./src/test/test.h: target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang -o ./src/test/test.h --generate-header ./src/test/test.k

clean:
	rm -f ./src/test/test.s
	rm -f ./src/test/test.h
	rm -f ./src/test/test
	rm -f code.s
	rm -f target/klang-1.0-jar-with-dependencies.jar
	rm -f target/klang-1.0.jar

cleanTests:
	rm -f ./src/test/test.s
	rm -f ./src/test/test.h
	rm -f ./src/test/test
