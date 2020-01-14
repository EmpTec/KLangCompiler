.PHONY: clean

run: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang < code.k > code.s

pretty: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang --pretty --no-compile < code.k > pretty.k

eval: code.k target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang --evaluate --no-compile < code.k

build: clean target/klang-1.0-jar-with-dependencies.jar

target/klang-1.0-jar-with-dependencies.jar:
	mvn package

test: ./src/test/test
	./src/test/test
	
./src/test/test: ./src/test/test.s
	gcc -o ./src/test/test ./src/test/test.s ./src/test/**/*.c ./src/test/test.c

./src/test/test.s: target/klang-1.0-jar-with-dependencies.jar
	java -cp target/klang-1.0-jar-with-dependencies.jar de.hsrm.compiler.Klang.Klang < ./src/test/test.k > ./src/test/test.s

clean:
	rm -f ./src/test/test.s
	rm -f ./src/test/test
	rm -f code.s
	rm -f target/klang-1.0-jar-with-dependencies.jar
	rm -f target/klang-1.0.jar