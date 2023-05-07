#!/bin/bash
CFlags="-J-Dfile.encoding=UTF-8"
JFlags="-Dfile.encoding=UTF-8"
Classes="classes"
Test="TestExecutor"
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
  find -name '*.java' | xargs java "$JFlags" -jar "$Formatter" -i
}

clean () {
  rm -f "$Classes"/*.class
}

make () {
  find -name '*.java' | xargs javac "$CFlags" -d "$Classes"
}

test () {
  java "$JFlags" -cp "$Classes" -ea "$Test"
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
