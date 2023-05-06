#!/bin/bash
Formatter="../Lib/google-java-format-1.15.0-all-deps.jar"

usage () {
  cat 1>&2 <<EOF
    Usage: $0 [options]

    Options:
      -h      display this help
      -f      format source code
      -c      remove all class files
      -m      run compile
      -t      run test
EOF
  exit
}

format () {
  java -jar "$Formatter" -i *.java
}

clean () {
  rm -f *.class
}

make () {
  javac -encoding UTF-8 *.java
}

test () {
  java -ea TestExecutor
}


if [ $# -eq 0 ]; then
  usage
fi

while getopts 'hfcmt' opt; do
  case "$opt" in
    h) usage ;;
    f) format ;;
    c) clean ;;
    m) make ;;
    t) format && make && test ;;
  esac
done
